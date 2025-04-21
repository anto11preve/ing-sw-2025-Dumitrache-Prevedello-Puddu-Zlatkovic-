package Controller;

// Import del modello e delle librerie per RMI
import Model.Game;
import Model.Ship.Components.ComponentsLoader;
import Model.Ship.Components.SpaceshipComponent;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class Controller extends UnicastRemoteObject implements ControllerInterface {

    // Modello del gioco (non inizializzato subito)
    private Game model;

    // Stato corrente del controller (es. LobbyPhase, ConstructionPhase, ecc.)
    private State state;

    // Lista dei componenti disponibili, caricati da JSON all'avvio
    private List<SpaceshipComponent> availableComponents;

    // MAIN - Avvio del server RMI
    public static void main(String[] args)
            throws RemoteException, AlreadyBoundException {

        System.out.println("Constructing server implementation...");
        Controller controller = new Controller();  // istanzia il controller

        System.out.println("Binding server implementation to registry...");
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("Game_Controller", controller);  // pubblica su RMI
        System.out.println("Waiting for invocations from clients...");
    }

    // Costruttore del Controller
    public Controller() throws RemoteException {
        // Imposta lo stato iniziale in attesa (es. attesa dei giocatori)
        this.state = new LobbyPhase();

        // ✅ Carica i componenti della nave da file JSON (vedi spaceship_components.json)
        this.availableComponents = ComponentsLoader.load("src/main/resources/spaceship_components.json");

        // Stampa a console quanti componenti sono stati caricati (debug)
        System.out.println("Componenti caricati: " + availableComponents.size());
    }

    // Getter per il modello del gioco
    public Game getModel() {
        return model;
    }

    // Imposta il nuovo stato del controller (es. cambio fase)
    public void setState(State phase) {
        this.state = phase;
    }

    // Riceve un comando (es. da un client) e lo passa allo stato attivo
    public void send(Map<String, Object> command) {
        state.execute(command, this);
    }

    // ✅ Getter utile se vuoi accedere ai componenti da altre parti del programma
    public List<SpaceshipComponent> getAvailableComponents() {
        return availableComponents;
    }
}
