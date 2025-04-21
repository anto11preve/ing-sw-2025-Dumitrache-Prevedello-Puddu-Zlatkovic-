package Controller;

import Model.Game;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class Controller extends UnicastRemoteObject implements ControllerInterface {
    private Game model;
    private State state;


    public static void main(String[] args)
            throws RemoteException, AlreadyBoundException {
        System.out.println("Constructing server implementation...");
        Controller controller = new Controller();

        System.out.println("Binding server implementation to registry...");
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("Game_Controller", controller);
        System.out.println("Waiting for invocations from clients...");

    }


    public Controller() throws RemoteException {
        this.state = new LobbyPhase();
    }

    public Game getModel() {
        return model;
    }

    public void setState(State phase) {
        this.state = phase;
    }

    public void send(Map<String, Object> command) {state.execute(command, this);}


}
