package Controller;

import Controller.Commands.Command;
import Controller.Enums.*;
import Controller.Exceptions.*;
import Controller.PreMatchLobby.LogInState;
import Model.Game;
import Model.Ship.Coordinates;

import java.rmi.*;
//import java.rmi.registry.*;
//import java.rmi.server.*;
import java.util.*;

public class Controller /*extends UnicastRemoteObject implements ControllerInterface*/ {
    private final Game model;
    private State state;
    private final Queue<Command> commandQueue= new LinkedList<>();
    private final MatchLevel matchLevel;
    private final int gameID;
    //placeholder per lo smistatore di comandi per eliminarsi da lista game attivi quando la partita finisce




//    public static void main(String[] args)
//            throws RemoteException, AlreadyBoundException {
//        System.out.println("Constructing server implementation...");
//        Controller controller = new Controller();
//
//        System.out.println("Binding server implementation to registry...");
//        Registry registry = LocateRegistry.getRegistry();
//        registry.bind("Game_Controller", controller);
//        System.out.println("Waiting for invocations from clients...");
//
//    }


    public Controller(MatchLevel matchLevel, int GameID/*, Smistatore smistatore*/) throws RemoteException {
        this.model = new Game(matchLevel);
        this.state = new LogInState(this);
        this.matchLevel = matchLevel;
        this.gameID = GameID;
        //this.smistatatore=smistatatore;

    }

    public Game getModel() {
        return model;
    }

    public void setState(State phase) {
        this.state = phase;
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
    public void send(Map<String, Object> command) {state.execute(this);}
    */


    public void login(String name) throws InvalidCommand, InvalidParameters {
        state.login(name);
    }
    public void logout(String name) throws InvalidCommand, InvalidParameters {
        state.logout(name);
    }
    public void startGame(String name) throws InvalidCommand, InvalidParameters {
        state.startGame(name);
    }
    public void getComponent(String name, int index) throws InvalidCommand, InvalidParameters {
        state.getComponent(name, index);
    }
    public void reserveComponent(String name) throws InvalidCommand, InvalidParameters {
        state.reserveComponent(name);
    }
    public void placeComponent(String name, ComponentOrigin origin, Coordinates coordinates, int orientation) throws InvalidCommand, InvalidParameters {
        state.placeComponent(name, origin, coordinates, orientation);
    }
    public void lookDeck(String name, int index) throws InvalidCommand, InvalidParameters {
        state.lookDeck(name, index);
    }
    public void flipHourGlass(String name) throws InvalidCommand, InvalidParameters {
        state.flipHourGlass(name);
    }
    public void finishBuilding(String name, int position) throws InvalidCommand, InvalidParameters {
        state.finishBuilding(name, position);
    }
    public void placeCrew(String name, Coordinates coordinates, CrewType type) throws InvalidCommand, InvalidParameters {
        state.placeCrew(name, coordinates, type);
    }

    // Adventure Card resolution
    public void pickNextCard(String name) throws InvalidCommand, InvalidParameters {
        state.pickNextCard(name);
    }
    public void deleteComponent(String name, Coordinates coordinates) throws InvalidCommand, InvalidParameters {
        state.deleteComponent(name, coordinates);
    }
    public void leaveRace(String name) throws InvalidCommand, InvalidParameters {
        state.leaveRace(name);
    }
    public void getReward(String name, RewardType rewardType) throws InvalidCommand, InvalidParameters {
        state.getReward(name, rewardType);
    }
    public void moveGoods(String name) throws InvalidCommand, InvalidParameters {
        state.moveGoods(name);
    }
    public void useItem(String name, ItemType itemType, Coordinates coordinates, int amount) throws InvalidCommand, InvalidParameters {
        state.useItem(name, itemType, coordinates, amount);
    }
    public void declaresDouble(String name, DoubleType doubleType, int amount) throws InvalidCommand, InvalidParameters {
        state.declaresDouble(name, doubleType, amount);
    }
    public void defend(String name) throws InvalidCommand, InvalidParameters {
        state.defend(name);
    }




}
