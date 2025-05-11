package Controller;

import Controller.Commands.Command;
import Controller.Enums.MatchLevel;
import Model.Game;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class Controller /*extends UnicastRemoteObject implements ControllerInterface*/ {
    private Game model;
    private State state;
    private Queue<Command> commandQueue= new LinkedList<>();
    private MatchLevel matchLevel;
    private int gameID;
    //placeholder per lo smistatore di comandi per eliminarsi da lista game attivi quando la partita finisce




    public static void main(String[] args)
            throws RemoteException, AlreadyBoundException {
//        System.out.println("Constructing server implementation...");
//        Controller controller = new Controller();
//
//        System.out.println("Binding server implementation to registry...");
//        Registry registry = LocateRegistry.getRegistry();
//        registry.bind("Game_Controller", controller);
//        System.out.println("Waiting for invocations from clients...");

    }


    public Controller(MatchLevel matchLevel, int GameID/*, Smistatore smistatore*/) throws RemoteException {
        this.model = new Game();
        this.state = new LobbyPhase();
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


    /*
    public void send(Map<String, Object> command) {state.execute(this);}
    */


}
