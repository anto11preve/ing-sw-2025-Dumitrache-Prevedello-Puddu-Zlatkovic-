package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.FlightBoard;
import Model.Ship.Components.SpaceshipComponent;
import Controller.Enums.MatchLevel;
import Controller.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game session of Galaxy Trucker.
 * Manages players, the tile pool, the flight board, and overall game state.
 */
public class Game {
    // List of players in the game
    private final List<Player> players;
    // Difficulty level of the match (Trial, Level 2, etc.)
    private final MatchLevel level;
    // Fixed-size array of spaceship components (exactly 156 tiles)
    private final SpaceshipComponent[] tiles;
    // FlightBoard containing the adventure cards deck
    private final FlightBoard flightBoard;
    // Current game state (e.g., BUILDING, FLIGHT, etc.)
    private State state;
    // Error flag used to track game invalid state
    private boolean error;

    /**
     * Constructs a new Game instance.
     *
     * @param players        list of players
     * @param level          match difficulty level
     * @param tiles          array of spaceship components (must be length 156)
     * @param adventureCards list of adventure cards
     */
    public Game(List<Player> players, MatchLevel level,
                SpaceshipComponent[] tiles,
                List<AdventureCardFilip> adventureCards) {
        if (players == null || players.isEmpty())
            throw new IllegalArgumentException("Player list must not be empty");
        if (level == null)
            throw new IllegalArgumentException("Match level must not be null");
        if (tiles == null || tiles.length != 156)
            throw new IllegalArgumentException("There must be exactly 156 spaceship components.");

        this.players = players;
        this.level = level;
        this.tiles = tiles;
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
     * Picks a component from the array by index and removes it.
     *
     * @param index the index of the component
     * @return the component removed
     */
    public SpaceshipComponent pickComponent(int index) {
        if (index < 0 || index >= tiles.length || tiles[index] == null)
            throw new IndexOutOfBoundsException("Invalid component index");
        SpaceshipComponent picked = tiles[index];
        tiles[index] = null;
        return picked;
    }

    /**
     * Adds a component back into the first available slot.
     *
     * @param component the component to add
     */
    public void addComponent(SpaceshipComponent component) {
        if (component == null)
            throw new IllegalArgumentException("Component cannot be null");
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                tiles[i] = component;
                return;
            }
        }
        throw new IllegalStateException("No space left to add the component");
    }

    /**
     * Returns a list of currently visible components.
     *
     * @return list of visible spaceship components
     */
    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent> visible = new ArrayList<>();
        for (SpaceshipComponent c : tiles) {
            if (c != null && c.isVisible()) visible.add(c);
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

    /**
     * Sets the current game state.
     *
     * @param phase new game state
     */
    public void setState(State phase) {
        this.state = phase;
    }

    /**
     * Returns the current game state.
     *
     * @return game state
     */
    public State getState() {
        return state;
    }

    /**
     * Returns whether the game is in an error state.
     *
     * @return true if an error occurred
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets the error state.
     *
     * @param error true if an error occurred
     */
    public void setError(boolean error) {
        this.error = error;
    }
}
