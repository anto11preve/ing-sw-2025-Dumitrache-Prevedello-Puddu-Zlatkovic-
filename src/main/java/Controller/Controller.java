package Controller;

import Controller.Commands.Command;
import Controller.Enums.*;
import Controller.Exceptions.*;
import Controller.PreMatchLobby.LogInState;
// Import del modello e delle librerie per RMI
import Model.Enums.Direction;
import Model.Exceptions.InvalidMethodParameters;
import Model.Game;

import Model.Ship.Coordinates;

import java.rmi.*;
//import java.rmi.registry.*;
//import java.rmi.server.*;
import java.util.*;

public class Controller /*extends UnicastRemoteObject implements ControllerInterface*/ {
    private final Game model;
    private final Queue<Command> commandQueue= new LinkedList<>();
    private final MatchLevel matchLevel;
    private final int gameID;
    //placeholder per lo smistatore di comandi per eliminarsi da lista game attivi quando la partita finisce


    /*
    public static void main(String[] args)
            throws RemoteException, AlreadyBoundException {
        System.out.println("Constructing server implementation...");
        Controller controller = new Controller();

        System.out.println("Binding server implementation to registry...");
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("Game_Controller", controller);
        System.out.println("Waiting for invocations from clients...");

   }
   */


    public Controller(MatchLevel matchLevel, int GameID/*, Smistatore smistatore*/) throws RemoteException {
        this.model = new Game(matchLevel);
        model.setState(new LogInState(this));
        this.matchLevel = matchLevel;
        this.gameID = GameID;
        //this.smistatatore=smistatatore;

    }

    // Getter for game model
    public Game getModel() {
        return model;
    }

    public int getGameID() {
        return gameID;
    }

    public void enqueueCommand(Command command) {
        commandQueue.add(command);
    }

    public Command dequeueCommand() {
        return commandQueue.poll();
    }

    public MatchLevel getMatchLevel() {return matchLevel;}


    /*
    public void send(Map<String, Object> command) {model.getState().execute(this);}
    */


    public void login(String name) throws InvalidCommand, InvalidParameters {
        try {
            model.getState().login(name);
            model.setError(false);
        } catch (InvalidCommand | InvalidParameters e) {
            model.setError(true);
        }
        //TODO: vedere se mettere gestione exception più a monte, nello specifico nel thread che mangia i comandi
    }
    public void logout(String name) throws InvalidCommand, InvalidParameters {
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

    // Adventure Card resolution
    public void pickNextCard(String name) throws InvalidCommand, InvalidParameters {
        model.getState().pickNextCard(name);
    }
    public void deleteComponent(String name, Coordinates coordinates) throws InvalidCommand, InvalidParameters {
        model.getState().deleteComponent(name, coordinates);
    }
    public void leaveRace(String name) throws InvalidCommand, InvalidParameters {
        model.getState().leaveRace(name);
    }
    public void getReward(String name, RewardType rewardType) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().getReward(name, rewardType);
    }
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex) throws InvalidCommand, InvalidParameters {
        model.getState().moveGood(name, oldCoordinates, newCoordinates, oldIndex, newIndex);
    }
    public void useItem(String name, ItemType itemType, Coordinates coordinates) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().useItem(name, itemType, coordinates);
    }
    public void declaresDouble(String name, DoubleType doubleType, int amount) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().declaresDouble(name, doubleType, amount);
    }
    public void defend(String name) throws InvalidCommand, InvalidParameters {
        model.getState().defend(name);
    }
    public void end(String name) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().end(name);
    }
    public void choosePlanet(String name, String planetName) throws InvalidCommand, InvalidParameters {
        model.getState().choosePlanet(name, planetName);
    }
    public void skipReward(String name) throws InvalidCommand, InvalidParameters {
        model.getState().skipReward(name);
    }
    public void getGood(String name, int goodIndex, Coordinates coordinates, int CargoHoldIndex) throws InvalidCommand, InvalidParameters {
        model.getState().getGood(name, goodIndex, coordinates, CargoHoldIndex);
    }
    public void throwDices(String playerName) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        model.getState().throwDices(playerName);
    }



}
