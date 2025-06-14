package Model;

import Controller.Enums.MatchLevel;
import Controller.State;
import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.FlightBoard;
import Model.Ship.Components.SpaceshipComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main Game class, managing players, the flight board, the component pool,
 * and overall match state.
 * This class unifies responsibilities previously split across Game and GameBuilder.
 */
public class Game {

    private final MatchLevel level; // Match difficulty level (only attribute allowed)

    private final List<Player> players;
    private final FlightBoard flightBoard;
    private final List<SpaceshipComponent> componentsPool;
    private State state;
    private boolean error = false;

    /**
     * Constructor to initialize the game with all required elements.
     *
     * @param level match difficulty
     * @param players list of players
     * @param componentsPool list of spaceship components
     * @param adventureCards the deck of adventure cards for the flight phase
     */
    public Game(MatchLevel level,
                List<Player> players,
                List<SpaceshipComponent> componentsPool,
                List<AdventureCardFilip> adventureCards) {

        if (level == null)
            throw new IllegalArgumentException("Match level must not be null");
        if (players == null || players.isEmpty())
            throw new IllegalArgumentException("Player list must not be empty");
        if (componentsPool == null || componentsPool.size() != 156)
            throw new IllegalArgumentException("Component pool must contain exactly 156 components");
        if (adventureCards == null || adventureCards.isEmpty())
            throw new IllegalArgumentException("Adventure cards must not be null or empty");

        this.level = level;
        this.players = new ArrayList<>(players);
        this.componentsPool = new ArrayList<>(componentsPool);
        this.flightBoard = new FlightBoard(adventureCards.toArray(new AdventureCardFilip[0]));
    }

    public MatchLevel getLevel() {
        return level;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public List<SpaceshipComponent> getComponentsPool() {
        return new ArrayList<>(componentsPool);
    }

    public FlightBoard getFlightBoard() {
        return flightBoard;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Player getPlayer(String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    public void addPlayer(String name) {
        this.players.add(new Player(name));
    }

    public void removePlayer(String name) {
        players.removeIf(p -> p.getName().equals(name));
    }

    public int rollDice() {
        return (int)(Math.random() * 6 + 1);
    }

    public SpaceshipComponent pickComponent(int index) {
        if (index < 0 || index >= componentsPool.size())
            throw new IndexOutOfBoundsException("Invalid component index");
        return componentsPool.remove(index);
    }

    public void addComponent(SpaceshipComponent component) {
        if (component == null)
            throw new IllegalArgumentException("Component cannot be null");
        this.componentsPool.add(component);
    }

    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent> visible = new ArrayList<>();
        for (SpaceshipComponent c : componentsPool) {
            if (c.isVisible()) visible.add(c);
        }
        return visible;
    }
}
