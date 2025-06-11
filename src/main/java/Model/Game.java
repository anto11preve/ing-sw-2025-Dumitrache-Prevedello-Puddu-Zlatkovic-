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

    private final List<Player> players; // All players in the current game
    private final FlightBoard flightBoard; // The central game board (flight phase)
    private final List<SpaceshipComponent> componentsPool; // Pool of tiles to use during the building phase
    private final MatchLevel level; // Match difficulty level
    private State state;
    private boolean error = false;

    /**
     * Constructor to initialize the game with all required elements.
     *
     * @param players list of players
     * @param level match difficulty
     * @param componentsPool list of spaceship components
     * @param adventureCards the deck of adventure cards for the flight phase
     */
    public Game(List<Player> players, MatchLevel level,
                List<SpaceshipComponent> componentsPool,
                List<AdventureCardFilip> adventureCards) {
        if (players == null || players.isEmpty())
            throw new IllegalArgumentException("Player list must not be empty");
        if (level == null)
            throw new IllegalArgumentException("Match level must not be null");
        if (componentsPool == null || componentsPool.isEmpty())
            throw new IllegalArgumentException("Component pool must not be empty");
        if (componentsPool.size() != 156)
            throw new IllegalArgumentException("There must be exactly 156 spaceship components.");

        this.players = players;
        this.level = level;
        this.componentsPool = componentsPool;
        this.flightBoard = new FlightBoard(adventureCards.toArray(new AdventureCardFilip[0]));
    }

    /**
     * Adds a new player to the game.
     *
     * @param name the name of the new player
     */
    public void addPlayer(String name) {
        this.players.add(new Player(name));
    }

    /**
     * Removes the player with the given name from the game.
     *
     * @param name the name of the player to remove
     */
    public void removePlayer(String name) {
        players.removeIf(p -> p.getName().equals(name));
    }

    /**
     * Retrieves a player by name.
     *
     * @param name the name of the player
     * @return the Player object, or null if not found
     */
    public Player getPlayer(String name) {
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a copy of the list of players.
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Simulates rolling a dice (1 to 6).
     *
     * @return a number between 1 and 6
     */
    public int rollDice() {
        return (int)(Math.random() * 6 + 1);
    }

    /**
     * Picks a component from the pool by index and removes it.
     *
     * @param index the index of the component
     * @return the component removed
     */
    public SpaceshipComponent pickComponent(int index) {
        if (index < 0 || index >= componentsPool.size())
            throw new IndexOutOfBoundsException("Invalid component index");
        return componentsPool.remove(index);
    }

    /**
     * Adds a component back into the pool.
     *
     * @param component the component to add
     */
    public void addComponent(SpaceshipComponent component) {
        if (component == null)
            throw new IllegalArgumentException("Component cannot be null");
        this.componentsPool.add(component);
    }

    /**
     * Returns a list of currently visible components.
     *
     * @return list of visible spaceship components
     */
    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent> visible = new ArrayList<>();
        for (SpaceshipComponent c : componentsPool) {
            if (c.isVisible()) visible.add(c);
        }
        return visible;
    }

    /**
     * Returns the flight board of the game.
     *
     * @return the FlightBoard instance
     */
    public FlightBoard getFlightBoard() {
        return this.flightBoard;
    }

    /**
     * Returns the match difficulty level.
     *
     * @return match level
     */
    public MatchLevel getLevel() {
        return this.level;
    }

    public void setState(State phase) {
        this.state = phase;
    }

    public State getState() {
        return state;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

