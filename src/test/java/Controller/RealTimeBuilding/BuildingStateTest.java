package Controller.RealTimeBuilding;

import Controller.StateTest;
import Controller.Controller;
import Controller.Enums.ComponentOrigin;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Game;
import TestUtils.TestStateManager;
import TestUtils.GameSnapshot;
import Model.Enums.Direction;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BuildingState functionality.
 * Tests component placement, timer management, and edge cases.
 */
public class BuildingStateTest extends StateTest {
//
//    private GameSnapshot gameSnapshotTrial;
//    private GameSnapshot gameSnapshotLevel2;
//
    public BuildingStateTest(MatchLevel matchLevel) {
        super(matchLevel);
    }

//    @BeforeAll
//    static void setUpTestStates() {
//        // Initialize common states once for all tests
//        TestStateManager.initializeCommonStates();
//
//    }
//
//    @BeforeEach
//    void setUp() {
//        // Reuse the "building_started" state instead of recreating from scratch
//        GameSnapshot savedState = TestStateManager.getGameSnapshot("building_started");
//        controller = savedState.controller;
//        model = savedState.getController().getModel();
//        buildingState = (BuildingState) model.getState();
//    }
//
//    // Component Drawing Tests
//
//    @Test
//    @DisplayName("Should successfully draw component from tiles")
//    void testDrawComponent() throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        // Find first available component
//        int componentIndex = findAvailableComponent();
//
//        buildingState.getComponent(player.getName(), componentIndex);
//
//        assertNotNull(player.getShipBoard().getActiveComponent());
//        assertNull(model.getTiles()[componentIndex]);
//    }
//
//    @Test
//    @DisplayName("Should reject drawing from empty tile position")
//    void testDrawFromEmptyTile() throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        // Draw a component first
//        int componentIndex = findAvailableComponent();
//        buildingState.getComponent(player.getName(), componentIndex);
//
//        // Try to draw from same position again
//        InvalidParameters exception = assertThrows(InvalidParameters.class,
//                () -> buildingState.getComponent(player.getName(), componentIndex));
//        assertTrue(exception.getMessage().contains("No component"));
//    }
//
//    @Test
//    @DisplayName("Should reject drawing with active component in hand")
//    void testDrawWithActiveComponent() throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        // Draw first component
//        int index1 = findAvailableComponent();
//        buildingState.getComponent(player.getName(), index1);
//
//        // Try to draw another without placing first
//        int index2 = findAvailableComponent();
//        InvalidParameters exception = assertThrows(InvalidParameters.class,
//                () -> buildingState.getComponent(player.getName(), index2));
//        assertTrue(exception.getMessage().contains("already has"));
//    }
//
//    // Component Placement Tests
//
//    @ParameterizedTest
//    @CsvSource({
//            "7, 5, true",   // Valid starting position
//            "6, 6, true",   // Valid position
//            "5, 5, false",  // Invalid position
//            "10, 10, false" // Out of bounds
//    })
//    @DisplayName("Should validate component placement coordinates")
//    void testComponentPlacementValidation(int row, int col, boolean shouldSucceed)
//            throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        // Draw a component
//        int componentIndex = findAvailableComponent();
//        buildingState.getComponent(player.getName(), componentIndex);
//
//        Coordinates coords = new Coordinates(row, col);
//
//        if (shouldSucceed) {
//            assertDoesNotThrow(() ->
//                    buildingState.placeComponent(player.getName(),
//                            ComponentOrigin.HAND, coords, Direction.UP));
//        } else {
//            assertThrows(InvalidParameters.class, () ->
//                    buildingState.placeComponent(player.getName(),
//                            ComponentOrigin.HAND, coords, Direction.UP));
//        }
//    }
//
//    @Test
//    @DisplayName("Should handle edge case: placing at boundary")
//    void testPlacementAtBoundary() throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        // For TRIAL level, test boundary coordinates
//        int componentIndex = findAvailableComponent();
//        buildingState.getComponent(player.getName(), componentIndex);
//
//        // Test extreme valid coordinate (9,9) for TRIAL
//        Coordinates boundary = new Coordinates(9, 9);
//        assertDoesNotThrow(() ->
//                buildingState.placeComponent(player.getName(),
//                        ComponentOrigin.HAND, boundary, Direction.LEFT));
//    }
//
//    // Finish Assembly Tests
//
//    @Test
//    @DisplayName("Should handle player finishing assembly")
//    void testFinishAssembly() throws Exception {
//        // Use a more complete state
//        GameSnapshot inProgress = TestStateManager.getGameSnapshot("building_in_progress");
//        controller = inProgress.controller;
//        model = inProgress.model;
//        buildingState = (BuildingState) model.getState();
//
//        Player player = model.getPlayers().get(0);
//
//        buildingState.finishBuilding(player.getName(), 1);
//
//        assertTrue(buildingState.finishedPlayers.containsValue(player));
//        assertEquals(1, buildingState.finishedPlayers.size());
//    }
//
//    @Test
//    @DisplayName("Should reject duplicate finish assembly")
//    void testDuplicateFinishAssembly() throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        buildingState.finishBuilding(player.getName(), 1);
//
//        InvalidParameters exception = assertThrows(InvalidParameters.class,
//                () -> buildingState.finishBuilding(player.getName(), 2));
//        assertTrue(exception.getMessage().contains("already finished"));
//    }
//
//    @Test
//    @DisplayName("Should transition when all players finish")
//    void testTransitionAllPlayersFinish() throws Exception {
//        // Simulate all players finishing
//        for (int i = 0; i < model.getPlayers().size(); i++) {
//            Player player = model.getPlayers().get(i);
//            buildingState.finishBuilding(player.getName(), i + 1);
//        }
//
//        // Should transition to next state
//        assertFalse(model.getState() instanceof BuildingState);
//    }
//
//    // Edge Cases and Stress Tests
//
//    @Test
//    @DisplayName("Should handle rapid successive operations")
//    void testRapidOperations() throws Exception {
//        Player player = model.getPlayers().get(0);
//
//        // Rapid draw and place operations
//        for (int i = 0; i < 5; i++) {
//            int index = findAvailableComponent();
//            if (index != -1) {
//                buildingState.getComponent(player.getName(), index);
//
//                // Place at different valid positions
//                Coordinates coords = new Coordinates(7, 5 + i);
//                buildingState.placeComponent(player.getName(),
//                        ComponentOrigin.HAND, coords, Direction.UP);
//            }
//        }
//
//        // Verify ship has components
//        assertTrue(player.getShipBoard().getAllComponents().size() >= 5);
//    }
//
//    @Test
//    @DisplayName("Should preserve state consistency under edge conditions")
//    void testStateConsistency() throws Exception {
//        // Test operations with null/invalid parameters
//        assertThrows(InvalidParameters.class,
//                () -> buildingState.getComponent(null, 0));
//
//        assertThrows(InvalidParameters.class,
//                () -> buildingState.getComponent("NonExistentPlayer", 0));
//
//        // Verify state remains consistent
//        assertNotNull(model.getState());
//        assertTrue(model.getState() instanceof BuildingState);
//    }
//
//    // Utility Methods
//
//    private int findAvailableComponent() {
//        SpaceshipComponent[] tiles = model.getTiles();
//        for (int i = 0; i < tiles.length; i++) {
//            if (tiles[i] != null) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * Example of creating a complex test scenario
//     * that can be saved for use in subsequent state tests
//     */
//    @Test
//    @DisplayName("Should create complex state for FixShipState tests")
//    void testCreateComplexStateForNextPhase() throws Exception {
//        // Build an invalid ship configuration for Level 2
//        if (controller.getMatchLevel() == MatchLevel.LEVEL2) {
//            // Create intentionally invalid ship
//            // ... complex setup ...
//
//            // Save this state for FixShipState tests
//            TestStateManager.saveGameSnapshot("invalid_ships_ready",
//                    new GameSnapshot(controller,
//                            "Ships ready for fixing phase"));
//        }
//    }
}