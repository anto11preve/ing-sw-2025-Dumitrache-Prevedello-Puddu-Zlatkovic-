package Model;

import Model.Board.Flightboard;
import Model.Ship.Components.SpaceshipComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Player> players;
    private List<SpaceshipComponent> hiddenComponents;
    private List<SpaceshipComponent> visibleComponents;
    private Flightboard flightboard;

    public Game() {
        this.players = new ArrayList<>();
        this.hiddenComponents = new ArrayList<>();
        this.visibleComponents = new ArrayList<>();
        this.flightboard = new Flightboard();
    }

    public Player getPlayer(String name){
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }
    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void removePlayer(String name) {
        players.remove(getPlayer(name));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int rollDice() {
        return new Random().nextInt(6) + 1;
    }

    public SpaceshipComponent pickHiddenComponent() {
        SpaceshipComponent choosenComponent = hiddenComponents.get(0);
        hiddenComponents.remove(0);
        return choosenComponent;
    }

    public void addVisibleComponent(SpaceshipComponent component) {
        visibleComponents.add(component);
    }

    public List<SpaceshipComponent> viewVisibleComponents() {
        return visibleComponents;
    }

    public void pickVisibleComponent(SpaceshipComponent component) {
        visibleComponents.remove(component);
    }

    public Flightboard getFlightboard() {
        return flightboard;
    }
}
