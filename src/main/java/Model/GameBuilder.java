package Model;

import Model.Board.FlightBoard;
import Model.Board.FlightBoardBuilder;

import Model.Ship.ShipBoard;
import Model.Ship.Components.SpaceshipComponent;
import Model.Utils.ComponentLoader;
import Controller.Enums.MatchLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Controller.Enums.MatchLevel.LEVEL2;
import static Controller.Enums.MatchLevel.TRIAL;

/**
 * Builder class for initializing a new Galaxy Trucker game.
 * Handles player setup, ship initialization, and level configuration.
 */
public class GameBuilder {
    private List<String> playerNames = new ArrayList<>();
    private MatchLevel level;

    /**
     * Adds a player to the game setup.
     */
    public GameBuilder addPlayer(String name) {
        this.playerNames.add(name);
        return this;
    }

    /**
     * Sets the match level (I, II, or III).
     */
    public GameBuilder setLevel(MatchLevel level) {
        this.level = level;
        return this;
    }

    /**
     * Builds and returns a fully initialized Game instance.
     */
    public Game build() {
        if (playerNames.isEmpty() || level == null) {
            throw new IllegalStateException("Must define at least one player and a game level.");
        }

        // Load all spaceship components from JSON (tiles for building phase)
        List<SpaceshipComponent> componentsPool = ComponentLoader.loadAllComponents();
        Collections.shuffle(componentsPool); // Shuffle the components randomly

        // Initialize players and assign an empty ShipBoard to each
        List<Player> players = new ArrayList<>();
        for (String name : playerNames) {
            Player p = new Player(name);
            int rows, cols;
            switch (level) {
                case TRIAL:
                    rows = 5; cols = 7;
                    break;
                case LEVEL2:
                    rows = 6; cols = 8;
                    break;
                default:
                    throw new IllegalStateException("Unsupported game level: " + level);
            }

            ShipBoard shipBoard = new ShipBoard(rows, cols); // Ship size based on game level
            p.setShipBoard(shipBoard);
            players.add(p);
        }

        // Build the flight board using its builder
        FlightBoard flightBoard = new FlightBoardBuilder()
                .setPlayers(players)
                .setLevel(level)
                .build();

        // Return the complete game instance
        return new Game(players, flightBoard, level, componentsPool);
    }
}