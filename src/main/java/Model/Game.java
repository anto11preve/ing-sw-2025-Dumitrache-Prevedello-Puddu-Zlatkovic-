package Model;

import Model.Board.FlightBoard;
import Model.Ship.Components.SpaceshipComponent;
import Controller.Enums.MatchLevel;

import java.util.List;

/**
 * Main Game class that contains the state of a Galaxy Trucker match.
 * It holds the players, the flight board, the difficulty level,
 * and the pool of spaceship components used during the building phase.
 */
public class Game {

    private final List<Player> players; // All players in the current game
    private final FlightBoard flightBoard; // The central game board (flight phase)
    private final MatchLevel level; // Difficulty level (I, II, or III)
    private final List<SpaceshipComponent> componentsPool; // Pool of tiles to use during the building phase

    /**
     * Constructor to initialize the game with all required elements.
     *
     * @param players list of players
     * @param flightBoard shared flight board
     * @param level match difficulty
     * @param componentsPool shuffled pool of ship components
     */
    public Game(List<Player> players, FlightBoard flightBoard, MatchLevel level, List<SpaceshipComponent> componentsPool) {
        this.players = players;
        this.flightBoard = flightBoard;
        this.level = level;
        this.componentsPool = componentsPool;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public FlightBoard getFlightBoard() {
        return flightBoard;
    }

    public MatchLevel getLevel() {
        return level;
    }

    public List<SpaceshipComponent> getComponentsPool() {
        return componentsPool;
    }
}
