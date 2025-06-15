package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.FlightBoard;
import Model.Ship.Components.SpaceshipComponent;
import Controller.Enums.MatchLevel;
import Controller.State;
import Model.AdventureCardLoader;
import Model.Utils.ComponentLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game session of Galaxy Trucker.
 * Manages players, the tile pool, the flight board, and overall game state.
 */
public class Game {
    private final List<Player> players;
    private final MatchLevel level;
    private final SpaceshipComponent[] tiles;
    private final FlightBoard flightBoard;
    private State state;
    private boolean error;

    /**
     * Constructs a new Game instance based only on the match level.
     * Automatically loads components and adventure cards.
     *
     * @param level match difficulty level
     */
    public Game(MatchLevel level) {
        if (level == null)
            throw new IllegalArgumentException("Match level must not be null");

        this.players = new ArrayList<>();
        this.level = level;
        this.tiles = ComponentLoader.loadComponents();
        List<AdventureCardFilip> cards = AdventureCardLoader.loadAdventureCards(level);
        this.flightBoard = new FlightBoard(cards.toArray(new AdventureCardFilip[0]));
    }

    public SpaceshipComponent[] getTiles() {
        return this.tiles;
    }

    public void addPlayer(String name) {
        this.players.add(new Player(name));
    }

    public void removePlayer(String name) {
        players.removeIf(p -> p.getName().equals(name));
    }

    public Player getPlayer(String name) {
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public int rollDice() {
        return (int)(Math.random() * 6 + 1);
    }

    public SpaceshipComponent pickComponent(int index) {
        if (index < 0 || index >= tiles.length || tiles[index] == null)
            throw new IndexOutOfBoundsException("Invalid component index");
        SpaceshipComponent picked = tiles[index];
        tiles[index] = null;
        return picked;
    }

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

    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent> visible = new ArrayList<>();
        for (SpaceshipComponent c : tiles) {
            if (c != null && c.isVisible()) visible.add(c);
        }
        return visible;
    }

    public FlightBoard getFlightBoard() {
        return this.flightBoard;
    }

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
