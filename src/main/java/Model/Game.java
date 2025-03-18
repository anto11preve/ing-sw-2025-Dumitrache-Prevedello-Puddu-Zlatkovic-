package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game {
    private Player[] players;
    private List<SpaceshipComponent> hiddenComponents;
    private List<SpaceshipComponent> visibleComponents;
    private Flightboard flightboard;

    public Game() {
        this.players = new ArrayList<>();
        this.hiddenComponents = new ArrayList<>();
        this.visibleComponents = new ArrayList<>();
        this.flightboard = new Flightboard();
    }

    public void addPlayer(String name) {
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[0]);
    }

    public int rollDice() {
        return new Random().nextInt(6) + 1;
    }

    public SpaceshipComponent pickHiddenComponent() {
        return null;
    }

    public void addVisibleComponent(SpaceshipComponent component) {
        visibleComponents.add(component);
    }

    public SpaceshipComponent viewVisibleComponents() {
        return null;
    } //to do

    public void pickVisibleComponent(SpaceshipComponent component) {
        //to do
    }

    public Flightboard getFlightboard() {
        return flightboard;
    }
}
