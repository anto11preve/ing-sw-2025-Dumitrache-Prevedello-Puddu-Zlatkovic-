package Model;

import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Board.FlightBoard;
import Model.Exceptions.InvalidMethodParameters;
import Model.Ship.Components.SpaceshipComponent;
import Controller.Enums.MatchLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main Game class that contains the state of a Galaxy Trucker match.
 * It holds the players, the flight board, the difficulty level,
 * and the pool of spaceship components used during the building phase.
 */
public class Game {
    private final List<Player> players; // All players participating in the current match
    private final SpaceshipComponent[] Tiles; // Fixed-size array used to manage tile components (legacy system)
    private final FlightBoard flightBoard; // Shared flight board used during the flight phase
    private final MatchLevel level; // Current match difficulty (Level I, II, or III)
    private final List<SpaceshipComponent> componentsPool; // Shuffled pool of available components during the building phase

    /**
     * Constructor for fully-initialized Game object, used when loading or starting a predefined game.
     *
     * @param players list of participating players
     * @param flightBoard shared game flight board
     * @param level difficulty level
     * @param componentsPool shuffled list of components available to players
     */
    public Game(List<Player> players, FlightBoard flightBoard, MatchLevel level, List<SpaceshipComponent> componentsPool) {
        this.players = players;
        this.Tiles = new SpaceshipComponent[156];
        this.flightBoard = flightBoard;
        this.level = level;
        this.componentsPool = componentsPool;
    }

    /**
     * Alternate constructor used when initializing an empty game with a specific difficulty level.
     * Creates empty player list, empty tile array, empty component pool and a default flight board.
     *
     * @param matchLevel difficulty level
     */
    public Game(MatchLevel matchLevel) {
        this.players = new ArrayList<>();
        this.Tiles = new SpaceshipComponent[156];
        this.componentsPool = new ArrayList<>();
        this.level = matchLevel;
        this.flightBoard = new FlightBoard();
    }

    /**
     * Retrieves a player by name.
     *
     * @param name name of the player
     * @return matching Player object, or null if not found
     */
    public Player getPlayer(String name){
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Adds a new player to the game.
     *
     * @param name name of the player to add
     */
    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    /**
     * Removes a player from the game by name.
     *
     * @param name name of the player to remove
     */
    public void removePlayer(String name) {
        players.remove(getPlayer(name));
    }

    /**
     * Returns the list of all players.
     *
     * @return list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Simulates a dice roll (1 to 6).
     *
     * @return random integer between 1 and 6
     */
    public int rollDice() {
        return new Random().nextInt(6) + 1;
    }

    /**
     * Picks and reveals a component from the Tiles array at the specified index.
     *
     * @param index index in the Tiles array
     * @return revealed SpaceshipComponent
     * @throws InvalidMethodParameters if index is out of bounds
     */
    public SpaceshipComponent pickComponent(int index) throws InvalidMethodParameters{
        if (index < 0 || index >= Tiles.length) {
            throw new InvalidMethodParameters("Invalid index");
        }
        SpaceshipComponent choosenComponent = Tiles[index];
        Tiles[index]=null;
        choosenComponent.setVisible();
        return choosenComponent;
    }

    /**
     * Adds a component to the first available slot in the Tiles array.
     *
     * @param component the component to be added
     */
    public void addComponent(SpaceshipComponent component) {
        if(component!=null){
            int i=0;
            while(i < Tiles.length && Tiles[i]!=null){
                i++;
            }
            if(i < Tiles.length){
                Tiles[i]=component;
            }
        }
    }

    /**
     * Returns a list of components that have been revealed (visible).
     *
     * @return list of visible components from Tiles
     */
    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent> visibleComponentsList = new ArrayList<>();
        for (SpaceshipComponent tile : Tiles) {
            if (tile != null && tile.isVisible()) {
                visibleComponentsList.add(tile);
            }
        }
        return visibleComponentsList;
    }

    /**
     * Returns the internal Tiles array.
     *
     * @return array of SpaceshipComponent
     */
    public SpaceshipComponent[] getTiles(){
        return Tiles;
    }

    /**
     * Returns the shared flight board.
     *
     * @return FlightBoard instance
     */
    public FlightBoard getFlightBoard() {
        return flightBoard;
    }

    /**
     * Returns the current match level.
     *
     * @return MatchLevel enum value
     */
    public MatchLevel getLevel() {
        return level;
    }

    /**
     * Returns the pool of components used for ship building.
     *
     * @return list of components
     */
    public List<SpaceshipComponent> getComponentsPool() {
        return componentsPool;
    }
}
