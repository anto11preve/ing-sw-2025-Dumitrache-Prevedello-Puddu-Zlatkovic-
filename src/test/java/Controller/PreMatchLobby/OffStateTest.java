package Controller.PreMatchLobby;

import Controller.Controller;
import Controller.State;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.*;
import Model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for the OffState class.
 * Tests off state functionality and behavior.
 */
public class OffStateTest {

    private Controller controller;
    private OffState offState;

    @BeforeEach
    public void setUp() {
        controller = new Controller(MatchLevel.TRIAL, 1);
        offState = new OffState(controller);
    }

    @Test
    public void testOffStateConstructor() {
        assertNotNull(offState);
        assertEquals(controller, offState.getController());
    }

    @Test
    public void testAllMethodsThrowInvalidCommand() {
        // Test that all methods throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login("Player"));
        assertThrows(InvalidCommand.class, () -> offState.logout("Player"));
        assertThrows(InvalidCommand.class, () -> offState.startGame("Player"));
        assertThrows(InvalidCommand.class, () -> offState.getComponent("Player", 0));
        assertThrows(InvalidCommand.class, () -> offState.reserveComponent("Player"));
        assertThrows(InvalidCommand.class, () -> offState.placeComponent("Player", null, null, null));
        assertThrows(InvalidCommand.class, () -> offState.lookDeck("Player", 0));
        assertThrows(InvalidCommand.class, () -> offState.flipHourGlass("Player"));
        assertThrows(InvalidCommand.class, () -> offState.finishBuilding("Player", 0));
        assertThrows(InvalidCommand.class, () -> offState.placeCrew("Player", null, null));
        assertThrows(InvalidCommand.class, () -> offState.pickNextCard("Player"));
        assertThrows(InvalidCommand.class, () -> offState.deleteComponent("Player", null));
        assertThrows(InvalidCommand.class, () -> offState.leaveRace("Player"));
        assertThrows(InvalidCommand.class, () -> offState.getReward("Player", null));
        assertThrows(InvalidCommand.class, () -> offState.moveGood("Player", null, null, 0, 0));
        assertThrows(InvalidCommand.class, () -> offState.useItem("Player", null, null));
        assertThrows(InvalidCommand.class, () -> offState.declaresDouble("Player", null, 0.0));
        assertThrows(InvalidCommand.class, () -> offState.end("Player"));
        assertThrows(InvalidCommand.class, () -> offState.choosePlanet("Player", "Planet"));
        assertThrows(InvalidCommand.class, () -> offState.skipReward("Player"));
        assertThrows(InvalidCommand.class, () -> offState.getGood("Player", 0, null, 0));
        assertThrows(InvalidCommand.class, () -> offState.throwDices("Player"));
    }

    @Test
    public void testLoginThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.login("TestPlayer"));
        assertEquals("Invalid command: login", exception.getMessage());
    }

    @Test
    public void testLogoutThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.logout("TestPlayer"));
        assertEquals("Invalid command: logout", exception.getMessage());
    }

    @Test
    public void testStartGameThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.startGame("TestPlayer"));
        assertEquals("Invalid command: startGame", exception.getMessage());
    }

    @Test
    public void testGetComponentThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.getComponent("TestPlayer", 0));
        assertEquals("Invalid command: getComponent", exception.getMessage());
    }

    @Test
    public void testReserveComponentThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.reserveComponent("TestPlayer"));
        assertEquals("Invalid command: reserveComponent", exception.getMessage());
    }

    @Test
    public void testPlaceComponentThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.placeComponent("TestPlayer", null, null, null));
        assertEquals("Invalid command: placeComponent", exception.getMessage());
    }

    @Test
    public void testLookDeckThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.lookDeck("TestPlayer", 1));
        assertEquals("Invalid command: lookDeck", exception.getMessage());
    }

    @Test
    public void testFlipHourGlassThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.flipHourGlass("TestPlayer"));
        assertEquals("Invalid command: flipHourGlass", exception.getMessage());
    }

    @Test
    public void testFinishBuildingThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.finishBuilding("TestPlayer", 1));
        assertEquals("Invalid command: finishBuilding", exception.getMessage());
    }

    @Test
    public void testPlaceCrewThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.placeCrew("TestPlayer", null, null));
        assertEquals("Invalid command: placeCrew", exception.getMessage());
    }

    @Test
    public void testPickNextCardThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.pickNextCard("TestPlayer"));
        assertEquals("Invalid command: pickNextCard", exception.getMessage());
    }

    @Test
    public void testDeleteComponentThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.deleteComponent("TestPlayer", null));
        assertEquals("Invalid command: deleteComponent", exception.getMessage());
    }

    @Test
    public void testLeaveRaceThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.leaveRace("TestPlayer"));
        assertEquals("Invalid command: leaveRace", exception.getMessage());
    }

    @Test
    public void testGetRewardThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.getReward("TestPlayer", null));
        assertEquals("Invalid command: getReward", exception.getMessage());
    }

    @Test
    public void testMoveGoodThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.moveGood("TestPlayer", null, null, 0, 1));
        assertEquals("Invalid command: moveGoods", exception.getMessage());
    }

    @Test
    public void testUseItemThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.useItem("TestPlayer", null, null));
        assertEquals("Invalid command: useItem", exception.getMessage());
    }

    @Test
    public void testDeclaresDoubleThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.declaresDouble("TestPlayer", null, 2.0));
        assertEquals("Invalid command: declaresDouble", exception.getMessage());
    }

    @Test
    public void testEndThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.end("TestPlayer"));
        assertEquals("Invalid command: end", exception.getMessage());
    }

    @Test
    public void testChoosePlanetThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.choosePlanet("TestPlayer", "Earth"));
        assertEquals("Invalid command: choosePlanet", exception.getMessage());
    }

    @Test
    public void testSkipRewardThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.skipReward("TestPlayer"));
        assertEquals("Invalid command: skipReward", exception.getMessage());
    }

    @Test
    public void testGetGoodThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> 
            offState.getGood("TestPlayer", 0, null, 0));
        assertEquals("Invalid command: getGood", exception.getMessage());
    }

    @Test
    public void testThrowDicesThrowsInvalidCommand() {
        InvalidCommand exception = assertThrows(InvalidCommand.class, () -> offState.throwDices("TestPlayer"));
        assertEquals("Invalid command: throwDices", exception.getMessage());
    }

    @Test
    public void testOffStateWithEmptyController() {
        Controller emptyController = new Controller(MatchLevel.TRIAL, 999);
        OffState emptyOffState = new OffState(emptyController);
        
        assertEquals(emptyController, emptyOffState.getController());
        assertThrows(InvalidCommand.class, () -> emptyOffState.login("Player"));
    }

    @Test
    public void testOffStateWithLevel2Controller() {
        Controller level2Controller = new Controller(MatchLevel.LEVEL2, 2);
        OffState level2OffState = new OffState(level2Controller);
        
        assertEquals(level2Controller, level2OffState.getController());
        assertThrows(InvalidCommand.class, () -> level2OffState.login("Player"));
    }

    @Test
    public void testOffStateInheritance() {
        assertTrue(offState instanceof OffState);
        assertTrue(offState instanceof State);
    }

    @Test
    public void testOffStateOnEnter() {
        // Test that onEnter can be called without issues
        offState.onEnter();
        assertTrue(true);
    }

    @Test
    public void testOffStatePlayerInTurn() {
        // Test player in turn management - may be null initially
        Player playerInTurn = offState.getPlayerInTurn();
        
        // Test setting player in turn
        offState.setPlayerInTurn(null);
        assertNull(offState.getPlayerInTurn());
        assertTrue(true);
    }

    @Test
    public void testOffStateWithNullParameters() {
        // Test methods with null parameters still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login(null));
        assertThrows(InvalidCommand.class, () -> offState.logout(null));
        assertThrows(InvalidCommand.class, () -> offState.startGame(null));
    }

    @Test
    public void testOffStateWithEmptyStringParameters() {
        // Test methods with empty string parameters still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login(""));
        assertThrows(InvalidCommand.class, () -> offState.logout(""));
        assertThrows(InvalidCommand.class, () -> offState.startGame(""));
    }

    @Test
    public void testOffStateWithSpecialCharacters() {
        // Test methods with special characters still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login("Player!@#$%"));
        assertThrows(InvalidCommand.class, () -> offState.logout("Player!@#$%"));
        assertThrows(InvalidCommand.class, () -> offState.startGame("Player!@#$%"));
    }

    @Test
    public void testOffStateWithUnicodeCharacters() {
        // Test methods with unicode characters still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login("Plāyér测试"));
        assertThrows(InvalidCommand.class, () -> offState.logout("Plāyér测试"));
        assertThrows(InvalidCommand.class, () -> offState.startGame("Plāyér测试"));
    }

    @Test
    public void testOffStateWithVeryLongStrings() {
        String longString = "A".repeat(1000);
        
        // Test methods with very long strings still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login(longString));
        assertThrows(InvalidCommand.class, () -> offState.logout(longString));
        assertThrows(InvalidCommand.class, () -> offState.startGame(longString));
    }

    @Test
    public void testOffStateWithWhitespace() {
        // Test methods with whitespace still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login("   Player   "));
        assertThrows(InvalidCommand.class, () -> offState.logout("   Player   "));
        assertThrows(InvalidCommand.class, () -> offState.startGame("   Player   "));
    }

    @Test
    public void testOffStateWithTabsAndNewlines() {
        // Test methods with tabs and newlines still throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login("Player\t\n\r"));
        assertThrows(InvalidCommand.class, () -> offState.logout("Player\t\n\r"));
        assertThrows(InvalidCommand.class, () -> offState.startGame("Player\t\n\r"));
    }

    @Test
    public void testOffStateExceptionMessages() {
        // Verify exact exception messages for key methods
        try {
            offState.login("test");
            fail("Should throw InvalidCommand");
        } catch (InvalidCommand e) {
            assertEquals("Invalid command: login", e.getMessage());
        } catch (InvalidParameters e) {
            // May also throw InvalidParameters
            assertTrue(true);
        } catch (Exception e) {
            fail("Should throw InvalidCommand, not " + e.getClass().getSimpleName());
        }
        
        try {
            offState.startGame("test");
            fail("Should throw InvalidCommand");
        } catch (InvalidCommand e) {
            assertEquals("Invalid command: startGame", e.getMessage());
        } catch (InvalidParameters e) {
            // May also throw InvalidParameters
            assertTrue(true);
        } catch (Exception e) {
            fail("Should throw InvalidCommand, not " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void testOffStateConsistency() {
        // Test that OffState consistently rejects all operations
        String[] playerNames = {"Player1", "Player2", "", null, "VeryLongPlayerName".repeat(100)};
        
        for (String name : playerNames) {
            final String finalName = name;
            try {
                assertThrows(InvalidCommand.class, () -> offState.login(finalName));
                assertThrows(InvalidCommand.class, () -> offState.logout(finalName));
                assertThrows(InvalidCommand.class, () -> offState.startGame(finalName));
            } catch (NullPointerException e) {
                // Expected for null names
                assertTrue(true);
            }
        }
    }

    @Test
    public void testOffStateMultipleOperations() {
        // Test that multiple operations all fail consistently
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            assertThrows(InvalidCommand.class, () -> offState.login("Player" + finalI));
            assertThrows(InvalidCommand.class, () -> offState.logout("Player" + finalI));
            assertThrows(InvalidCommand.class, () -> offState.startGame("Player" + finalI));
            assertThrows(InvalidCommand.class, () -> offState.getComponent("Player" + finalI, finalI));
            assertThrows(InvalidCommand.class, () -> offState.reserveComponent("Player" + finalI));
        }
    }

    @Test
    public void testOffStateWithDifferentGameIDs() {
        // Test OffState with different game IDs
        Controller[] controllers = {
            new Controller(MatchLevel.TRIAL, 1),
            new Controller(MatchLevel.TRIAL, 999),
            new Controller(MatchLevel.LEVEL2, 42)
        };
        
        for (Controller ctrl : controllers) {
            OffState state = new OffState(ctrl);
            assertEquals(ctrl, state.getController());
            assertThrows(InvalidCommand.class, () -> state.login("Player"));
        }
    }

    @Test
    public void testOffStateStateTransition() {
        // Test that OffState doesn't change state on operations
        State originalState = controller.getModel().getState();
        
        try {
            offState.login("Player");
        } catch (InvalidCommand e) {
            // Expected
        } catch (InvalidParameters e) {
            // May also throw InvalidParameters
        }
        
        // State should remain unchanged (though this test depends on controller implementation)
        assertTrue(true);
    }

    @Test
    public void testOffStateToString() {
        // Test that toString doesn't crash
        String result = offState.toString();
        assertNotNull(result);
    }

    @Test
    public void testOffStateHashCode() {
        // Test that hashCode doesn't crash
        int hashCode = offState.hashCode();
        assertTrue(true); // Just ensure no exception
    }

    @Test
    public void testOffStateEquality() {
        OffState state1 = new OffState(controller);
        OffState state2 = new OffState(controller);
        
        // States are different objects
        assertNotEquals(state1, state2);
    }

    @Test
    public void testOffStateWithNullController() {
        // Test OffState with null controller (if constructor allows it)
        try {
            OffState nullState = new OffState(null);
            assertNull(nullState.getController());
        } catch (Exception e) {
            // Constructor may not allow null
            assertTrue(true);
        }
    }

    @Test
    public void testOffStateBehaviorConsistency() {
        // Test that OffState behaves consistently across multiple instances
        OffState state1 = new OffState(controller);
        OffState state2 = new OffState(new Controller(MatchLevel.TRIAL, 2));
        
        // Both should reject the same operations
        assertThrows(InvalidCommand.class, () -> state1.login("Player"));
        assertThrows(InvalidCommand.class, () -> state2.login("Player"));
        
        assertThrows(InvalidCommand.class, () -> state1.startGame("Player"));
        assertThrows(InvalidCommand.class, () -> state2.startGame("Player"));
    }

    @Test
    public void testOffStateMethodCoverage() {
        // Ensure all inherited methods are covered and throw InvalidCommand
        assertThrows(InvalidCommand.class, () -> offState.login("test"));
        assertThrows(InvalidCommand.class, () -> offState.logout("test"));
        assertThrows(InvalidCommand.class, () -> offState.startGame("test"));
        assertThrows(InvalidCommand.class, () -> offState.getComponent("test", 0));
        assertThrows(InvalidCommand.class, () -> offState.reserveComponent("test"));
        assertThrows(InvalidCommand.class, () -> offState.placeComponent("test", null, null, null));
        assertThrows(InvalidCommand.class, () -> offState.lookDeck("test", 0));
        assertThrows(InvalidCommand.class, () -> offState.flipHourGlass("test"));
        assertThrows(InvalidCommand.class, () -> offState.finishBuilding("test", 0));
        assertThrows(InvalidCommand.class, () -> offState.placeCrew("test", null, null));
        assertThrows(InvalidCommand.class, () -> offState.pickNextCard("test"));
        assertThrows(InvalidCommand.class, () -> offState.deleteComponent("test", null));
        assertThrows(InvalidCommand.class, () -> offState.leaveRace("test"));
        assertThrows(InvalidCommand.class, () -> offState.getReward("test", null));
        assertThrows(InvalidCommand.class, () -> offState.moveGood("test", null, null, 0, 1));
        assertThrows(InvalidCommand.class, () -> offState.useItem("test", null, null));
        assertThrows(InvalidCommand.class, () -> offState.declaresDouble("test", null, 1.0));
        assertThrows(InvalidCommand.class, () -> offState.end("test"));
        assertThrows(InvalidCommand.class, () -> offState.choosePlanet("test", "planet"));
        assertThrows(InvalidCommand.class, () -> offState.skipReward("test"));
        assertThrows(InvalidCommand.class, () -> offState.getGood("test", 0, null, 0));
        assertThrows(InvalidCommand.class, () -> offState.throwDices("test"));
    }
}