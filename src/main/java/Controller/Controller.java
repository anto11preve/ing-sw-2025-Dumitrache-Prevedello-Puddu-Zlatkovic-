package Controller;

import Controller.Commands.Command;
import Controller.Enums.*;
import Controller.Exceptions.*;
import Controller.PreMatchLobby.LogInState;
// Import del modello
import Controller.Server.Server;
import Model.Enums.Direction;
import Model.Exceptions.InvalidMethodParameters;
import Model.Game;

import Model.Player;
import Model.Ship.Coordinates;
import Networking.Agent;
import Networking.Messages.ClientMessage;
import Networking.Network;
import View.Client.Actions.Action;
import View.Client.Actions.UpdateGameAction;

import java.util.*;

public class Controller implements Agent {
    private final Game model;
    private final Queue<Command> commandQueue= new LinkedList<>();
    private final MatchLevel matchLevel;
    private final int gameID;
    private Action queuedAction;

    public Controller(MatchLevel matchLevel, int GameID) {
        this.model = new Game(matchLevel);
        this.model.setState(new LogInState(this));
        this.matchLevel = matchLevel;
        this.gameID = GameID;

        //create the thread that reads and executes commands
        new Thread(this, "Controller#" + this.gameID).start();
    }

    // Getter for game model
    public Game getModel() {
        return model;
    }

    public int getGameID() {
        return gameID;
    }

    public void enqueueCommand(Command command) {
        synchronized (this.commandQueue) {
            this.commandQueue.add(command);
            this.commandQueue.notifyAll();
        }
    }

    public Command dequeueCommand() {
        synchronized (this.commandQueue) {
            if(this.commandQueue.isEmpty()){
                try{
                    this.commandQueue.wait();
                } catch (InterruptedException e){
                    /*TODO: decidere se si vuole gestire o ignorare*/
                }
            }
            return this.commandQueue.poll();
        }
    }

    public MatchLevel getMatchLevel() {return matchLevel;}


    /*
    public void send(Map<String, Object> command) {model.getState().execute(this);}
    */


    public void login(String name) throws InvalidCommand, InvalidParameters {
        model.getState().login(name);
    }
    public void logout(String name) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().logout(name);
    }
    public void startGame(String name) throws InvalidCommand, InvalidParameters {
        model.getState().startGame(name);
    }
    public void getComponent(String name, int index) throws InvalidCommand, InvalidParameters {
        model.getState().getComponent(name, index);
    }
    public void reserveComponent(String name) throws InvalidCommand, InvalidParameters {
        model.getState().reserveComponent(name);
    }
    public void placeComponent(String name, ComponentOrigin origin, Coordinates coordinates, Direction orientation) throws InvalidCommand, InvalidParameters {
        model.getState().placeComponent(name, origin, coordinates, orientation);
    }
    public void lookDeck(String name, int index) throws InvalidCommand, InvalidParameters {
        model.getState().lookDeck(name, index);
    }
    public void flipHourGlass(String name) throws InvalidCommand, InvalidParameters {
        model.getState().flipHourGlass(name);
    }
    public void finishBuilding(String name, int position) throws InvalidCommand, InvalidParameters {
        model.getState().finishBuilding(name, position);
    }
    public void placeCrew(String name, Coordinates coordinates, CrewType type) throws InvalidCommand, InvalidParameters {
        model.getState().placeCrew(name, coordinates, type);
    }
    public void preBuiltShip(String name, int index) throws InvalidCommand, InvalidParameters {
        model.getState().preBuiltShip(name, index);
    }
    // Adventure Card resolution
    public void pickNextCard(String name) throws InvalidCommand, InvalidParameters, InvalidContextualAction, InvalidMethodParameters {
        model.getState().pickNextCard(name);
    }
    public void deleteComponent(String name, Coordinates coordinates) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().deleteComponent(name, coordinates);
    }
    public void leaveRace(String name) throws InvalidCommand, InvalidParameters {
        model.getState().leaveRace(name);
    }
    public void getReward(String name, RewardType rewardType) throws InvalidCommand, InvalidParameters, InvalidMethodParameters, InvalidContextualAction {
        model.getState().getReward(name, rewardType);
    }
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex) throws InvalidCommand, InvalidParameters, InvalidContextualAction {
        model.getState().moveGood(name, oldCoordinates, newCoordinates, oldIndex, newIndex);
    }
    public void useItem(String name, ItemType itemType, Coordinates coordinates) throws InvalidCommand, InvalidParameters, InvalidMethodParameters, InvalidContextualAction {
        model.getState().useItem(name, itemType, coordinates);
    }
    public void declaresDouble(String name, DoubleType doubleType, double amount) throws InvalidCommand, InvalidParameters, InvalidMethodParameters, InvalidContextualAction {
        model.getState().declaresDouble(name, doubleType, amount);
    }
    public void end(String name) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().end(name);
    }
    public void choosePlanet(String name, String planetName) throws InvalidCommand, InvalidParameters, InvalidContextualAction {
        model.getState().choosePlanet(name, planetName);
    }
    public void skipReward(String name) throws InvalidCommand, InvalidParameters {
        model.getState().skipReward(name);
    }
    public void getGood(String name, int goodIndex, Coordinates coordinates, int CargoHoldIndex) throws InvalidCommand, InvalidParameters, InvalidContextualAction {
        model.getState().getGood(name, goodIndex, coordinates, CargoHoldIndex);
    }
    public void throwDices(String playerName) throws InvalidCommand, InvalidParameters, InvalidMethodParameters, InvalidContextualAction {
        model.getState().throwDices(playerName);
    }

    public synchronized void setQueuedAction(Action action){
        this.queuedAction = action;
    }

    public synchronized void sendAll() {
        final Game modelClone = this.model.clone();
        for(Player player : this.getModel().getPlayers()){
            Network network = Server.server.getNetwork(player.getName());
            if(network != null && !network.isDone()) {

                network.send(new ClientMessage(
                        new UpdateGameAction(modelClone)
                ));
                if(this.queuedAction != null) {
                    network.send(new ClientMessage(this.queuedAction));
                }
            }
        }

        this.queuedAction = null;
    }

    public void run(){
        while(!this.model.getState().isDone()){
            final Command command = this.dequeueCommand();

            if(command != null){
                try {
                    command.execute(this);
                    this.model.setError(false);
                } catch (InvalidCommand | InvalidParameters | InvalidMethodParameters | InvalidContextualAction e) {
                    this.model.setError(true);
                }

                this.sendAll();
            }
        }

        Server.server.destroyGame(this.gameID);
    }
}
