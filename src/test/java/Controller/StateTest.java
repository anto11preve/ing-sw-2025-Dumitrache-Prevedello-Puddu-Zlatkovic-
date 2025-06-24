package Controller;

import Controller.Enums.MatchLevel;
import Model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import TestUtils.TestStateManager;
import TestUtils.GameSnapshot;


import static org.junit.jupiter.api.Assertions.*;

public class StateTest {


    protected Controller controller;
    protected Game model;
    protected State state;
    //protected Game model;

    public StateTest(MatchLevel matchLevel) {
        this.controller = createController(matchLevel);
        this.model= controller.getModel();
        this.state=model.getState();
    }

    /**
     * Creates a controller with specified match level.
     * Override this method to customize controller creation.
     *
     * @param matchLevel the match level for the game
     * @return a new controller instance
     */
    protected Controller createController(MatchLevel matchLevel) {
        return new Controller(matchLevel, 1);
    }

    /**
     * Adds players to the game model.
     *
     * @param playerNames names of players to add
     */
    protected void addPlayers(String... playerNames) {
        Game model = controller.getModel();
        for (String name : playerNames) {
            model.addPlayer(name);
        }
    }

    /**
     * Creates a game snapshot for state preservation.
     * This can be used to save game state between tests.
     *
     * @return a snapshot of the current game state
     */
    protected GameSnapshot createSnapshot(String description) {
        return new GameSnapshot(controller, description);
    }

    /**
     * Restores game state from a snapshot.
     *
     * @param snapshot the snapshot to restore from
     */
    protected void restoreFromSnapshot(GameSnapshot snapshot) {
        this.controller = snapshot.getController();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


}