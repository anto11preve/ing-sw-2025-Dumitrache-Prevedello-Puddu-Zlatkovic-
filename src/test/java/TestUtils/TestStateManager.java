package TestUtils;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Ship.Coordinates;
import Model.Enums.Direction;
import Controller.Enums.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages game states for testing purposes.
 * Provides predefined game states to avoid repetitive setup.
 */
public class TestStateManager {

    private static final Map<String, GameSnapshot> savedGameSnapshots = new HashMap<>();



    /**
     * Creates and saves common test states
     */
    public static void initializeCommonStates() {
        // Empty lobby
        saveGameSnapshot("empty_lobby_trial", createEmptyLobbyTrial());

        // Lobby with 2 players trial
        saveGameSnapshot("lobby_2_players_trial", createLobbyWith2PlayersTrial());

        // Full lobby (4 players) trial
        saveGameSnapshot("lobby_4_players_trial", createLobbyWith4PlayersTrial());

        // Building phase just started with 2 players trial
        saveGameSnapshot("building_2_players_trial", createBuildingWith2PlayersTrial());

        // Building phase just started with 2 players level 2
        saveGameSnapshot("building_2_players_level2", createBuildingWith2PlayersLevel2());

        // Building phase with components placed


        // Building phase with 1 wrong ship in Level 2
        saveGameSnapshot("build_end_2_good_1_bad_L2", finishedBuilding1wrongL2(MatchLevel.LEVEL2));

        // Building phase with 1 wrong ship in Trial
        saveGameSnapshot("build_end_2_good_1_bad_Trial", finishedBuilding1wrongL2(MatchLevel.TRIAL));

        // Building phase with all valid ships in Level 2
        saveGameSnapshot("build_end_2_good_L2", finishedBuildingAllValid(MatchLevel.LEVEL2));

        // Building phase with all valid ships in Trial
        saveGameSnapshot("build_end_2_good_L2", finishedBuildingAllValid(MatchLevel.LEVEL2));

        // Building phase with all valid ships in Level 2 with NO alien support ship
        saveGameSnapshot("build_end_2_good_NO_alien_L2", finishedBuildingAllValid());

        // Flight phase started with 2 players in Trial
        saveGameSnapshot("flight_phase_2_players_trial", flightPhase2Players(MatchLevel.TRIAL));

        // Flight phase started with 2 players in Level 2
        saveGameSnapshot("flight_phase_2_players_L2", flightPhase2Players(MatchLevel.LEVEL2));

    }



    /**
     * Saves a game state with a key, the saving is NOT permanent
     *
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
    private static GameSnapshot createEmptyLobbyTrial() {
        Controller controller = new Controller(MatchLevel.TRIAL, 1);
        return new GameSnapshot(controller, "Empty lobby - no players");
    }

    private static GameSnapshot createLobbyWith2PlayersTrial() {
        Controller controller = new Controller(MatchLevel.TRIAL, 2);
        try {
            controller.login("Player1");
            controller.login("Player2");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        return new GameSnapshot(controller, "Lobby with 2 players");
    }

    private static GameSnapshot createLobbyWith4PlayersTrial() {
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

    private static GameSnapshot createBuildingWith2PlayersTrial() {
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

    private static GameSnapshot createBuildingWith2PlayersLevel2() {
        Controller controller = new Controller(MatchLevel.LEVEL2, 4);
        try {
            controller.login("Player1");
            controller.login("Player2");
            controller.startGame("Player1");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        return new GameSnapshot(controller, "Building phase just started");
    }

    private static GameSnapshot finishedBuilding1wrongL2(MatchLevel level) {
        Controller controller = new Controller(level, 1);
        try {
            controller.login("Anna");
            controller.login("Bob");
            controller.startGame("Carl");
            controller.startGame("Anna");
            controller.preBuiltShip("Anna", 0);
            controller.preBuiltShip("Bob", 1);
            controller.preBuiltShip("Carl", 2);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        if( level == MatchLevel.LEVEL2) {
            return new GameSnapshot(controller, "Finished building phase with 1 wrong ship in Level 2");
        }else{
            return new GameSnapshot(controller, "Finished building phase with 1 wrong ship in Trial");
        }


    }

    private static GameSnapshot finishedBuildingAllValid(MatchLevel level) {
        Controller controller = new Controller(level, 1);
        try {
            controller.login("Anna");
            controller.login("Bob");
            controller.startGame("Anna");
            controller.preBuiltShip("Anna", 0);
            controller.preBuiltShip("Bob", 1);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }
        if( level == MatchLevel.LEVEL2) {
            return new GameSnapshot(controller, "Finished building phase with all valid ship in Level 2");
        }else{
            return new GameSnapshot(controller, "Finished building phase with all valid ship in Trial");
        }

    }

    private static GameSnapshot finishedBuildingAllValid() {
        Controller controller = new Controller(MatchLevel.LEVEL2, 1);
        try {
            controller.login("Anna");
            controller.login("Bob");
            controller.startGame("Anna");
            controller.preBuiltShip("Anna", 1);
            controller.preBuiltShip("Bob", 1);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create test state", e);
        }

        return new GameSnapshot(controller, "Finished building phase with alien support ship in Level 2");

    }

    public static GameSnapshot flightPhase2Players(MatchLevel level) {
        GameSnapshot snapshot = finishedBuildingAllValid(level);
        Controller controller = snapshot.getController();
        try {
            controller.finishBuilding("Anna", 0);
            controller.finishBuilding("Bob", 1);
        } catch (InvalidCommand | InvalidParameters e) {
            throw new RuntimeException("Failed to start flight phase", e);
        }

        if(level == MatchLevel.LEVEL2) {

            try {
                controller.placeCrew("Anna", new Coordinates(7, 6), CrewType.PURPLE_ALIEN);
            }catch (InvalidCommand | InvalidParameters e) {
                throw new RuntimeException("Failed to start flight phase", e);
            }
        }

        if( level == MatchLevel.LEVEL2) {
            return new GameSnapshot(controller, "Flight phase started with 2 players in Level 2");
        } else {
            return new GameSnapshot(controller, "Flight phase started with 2 players in Trial");
        }


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