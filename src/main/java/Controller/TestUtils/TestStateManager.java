package Controller.TestUtils;

import Controller.Controller;
import Controller.Enums.MatchLevel;
import Model.Game;
import Model.Ship.Coordinates;
import Model.Enums.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages game states for testing purposes.
 * Provides predefined game states to avoid repetitive setup.
 */
public class TestStateManager {

    private static final Map<String, GameSnapshot> savedGameSnapshots = new HashMap<>();

    /**
     * Represents a saved game state
     */
    public static class GameSnapshot {
        public final Controller controller;
        public final Game model;
        public final String description;

        public GameSnapshot(Controller controller, String description) {
            this.controller = controller;
            this.model = controller.getModel();
            this.description = description;
        }
    }

    /**
     * Creates and saves common test states
     */
    public static void initializeCommonStates() {
        // Empty lobby
        saveGameSnapshot("empty_lobby_trial", createEmptyLobby());

        // Lobby with 2 players
        saveGameSnapshot("lobby_2_players_trial", createLobbyWith2Players());

        // Full lobby (4 players)
        saveGameSnapshot("lobby_4_players_trial", createFullLobby());

        // Building phase just started
        saveGameSnapshot("building_started", createBuildingPhaseStarted());

        // Building phase with components placed
        saveGameSnapshot("building_in_progress", createBuildingInProgress());
    }

    /**
     * Saves a game state with a key
     */
    public static void saveGameSnapshot(String key, GameSnapshot state) {
        savedGameSnapshots.put(key, state);
    }

    /**
     * Retrieves a saved game state
     */
    public static GameSnapshot getGameSnapshot(String key) {
        return savedGameSnapshots.get(key);
    }

    // State creation methods

    private static GameSnapshot createEmptyLobby() {
        Controller controller = new Controller(MatchLevel.TRIAL, 1);
        return new GameSnapshot(controller, "Empty lobby - no players");
    }

    private static GameSnapshot createLobbyWith2Players() {
        Controller controller = new Controller(MatchLevel.TRIAL, 2);
        try {
            controller.login("Player1");
            controller.login("Player2");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        return new GameSnapshot(controller, "Lobby with 2 players");
    }

    private static GameSnapshot createFullLobby() {
        Controller controller = new Controller(MatchLevel.TRIAL, 3);
        try {
            controller.login("Player1");
            controller.login("Player2");
            controller.login("Player3");
            controller.login("Player4");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        return new GameSnapshot(controller, "Full lobby with 4 players");
    }

    private static GameSnapshot createBuildingPhaseStarted() {
        Controller controller = new Controller(MatchLevel.TRIAL, 4);
        try {
            controller.login("Player1");
            controller.login("Player2");
            controller.startGame("Player1");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        return new GameSnapshot(controller, "Building phase just started");
    }

    private static GameSnapshot createBuildingInProgress() {
        Controller controller = new Controller(MatchLevel.TRIAL, 5);
        try {
            // Setup players and start game
            controller.login("Player1");
            controller.login("Player2");
            controller.startGame("Player1");

            // Simulate some building actions
            controller.getComponent("Player1", 0);
            controller.placeComponent("Player1",
                    Controller.Enums.ComponentOrigin.HAND,
                    new Coordinates(7, 5),
                    Direction.UP);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        return new GameSnapshot(controller, "Building phase with components placed");
    }

    /**
     * Creates a custom game state based on specific requirements
     */
    public static class GameSnapshotBuilder {
        private Controller controller;
        private MatchLevel matchLevel = MatchLevel.TRIAL;
        private int gameId = 100;

        public GameSnapshotBuilder withMatchLevel(MatchLevel level) {
            this.matchLevel = level;
            return this;
        }

        public GameSnapshotBuilder withGameId(int id) {
            this.gameId = id;
            return this;
        }

        public GameSnapshotBuilder withPlayers(String... playerNames) {
            if (controller == null) {
                controller = new Controller(matchLevel, gameId);
            }
            try {
                for (String name : playerNames) {
                    controller.login(name);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to add players", e);
            }
            return this;
        }

        public GameSnapshotBuilder startGame() {
            try {
                String admin = controller.getModel().getPlayers().get(0).getName();
                controller.startGame(admin);
            } catch (Exception e) {
                throw new RuntimeException("Failed to start game", e);
            }
            return this;
        }

        public GameSnapshot build(String description) {
            if (controller == null) {
                controller = new Controller(matchLevel, gameId);
            }
            return new GameSnapshot(controller, description);
        }
    }
}