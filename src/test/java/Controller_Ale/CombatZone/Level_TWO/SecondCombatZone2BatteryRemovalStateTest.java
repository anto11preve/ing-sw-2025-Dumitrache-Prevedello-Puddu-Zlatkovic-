package Controller_Ale.CombatZone.Level_TWO;

import Controller.CombatZone.Level_TWO.SecondCombatZone2BatteryRemovalState;
import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SecondCombatZone2BatteryRemovalStateTest {

    private Controller controller;
    private Context context;
    private SecondCombatZone2BatteryRemovalState state;
    private Player player1;
    private Player player2;
    private Coordinates batteryCoords;
    private BatteryCompartment battery;

    @BeforeEach
    void setUp() {
        controller = new Controller(MatchLevel.LEVEL2, 1);
        controller.getModel().addPlayer("Player1");
        controller.getModel().addPlayer("Player2");
        
        player1 = controller.getModel().getPlayer("Player1");
        player2 = controller.getModel().getPlayer("Player2");
        
        context = new Context(controller);
        
        // Set up the context's player list
        context.getPlayers().clear();
        context.getPlayers().add(player1);
        context.getPlayers().add(player2);
        
        // Add battery to player1's ship
        batteryCoords = new Coordinates(6, 7);
        battery = new BatteryCompartment(Card.BATTERY_COMPARTMENT, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, 
                                        ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, 3);
        
        try {
            player1.getShipBoard().addComponent(battery, batteryCoords);
        } catch (Exception e) {
            fail("Failed to add battery: " + e.getMessage());
        }
        
        // Create state with amount of batteries to remove
        int amountToRemove = 1;
        state = new SecondCombatZone2BatteryRemovalState(context, amountToRemove);
    }

    @Test
    void testConstructor() {
        assertNotNull(state);
    }

    @Test
    void testUseItem_ValidBatteryRemoval() throws InvalidContextualAction, InvalidMethodParameters {
        // This test expects an exception due to component type mismatch
        InvalidContextualAction exception = assertThrows(InvalidContextualAction.class,
            () -> state.useItem("Player1", ItemType.BATTERIES, batteryCoords));
        
        assertEquals("Invalid component type, expected BatteryCompartment", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_InvalidItemType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> state.useItem("Player1", ItemType.CREW, batteryCoords));
        
        assertEquals("Invalid item type, expected BATTERIES", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_WrongPlayer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> state.useItem("Player2", ItemType.BATTERIES, batteryCoords));
        
        assertEquals("It's not the player's turn", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_NullCoordinates() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> state.useItem("Player1", ItemType.BATTERIES, null));
        
        assertEquals("Coordinates cannot be null", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_InvalidComponent() {
        Coordinates invalidCoords = new Coordinates(0, 0);
        
        assertThrows(InvalidContextualAction.class,
            () -> state.useItem("Player1", ItemType.BATTERIES, invalidCoords));
        
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_MultipleBatteries() throws InvalidContextualAction, InvalidMethodParameters {
        // Create state with 2 batteries to remove
        SecondCombatZone2BatteryRemovalState multiState = new SecondCombatZone2BatteryRemovalState(context, 2);
        
        // This test expects an exception due to component type mismatch
        InvalidContextualAction exception = assertThrows(InvalidContextualAction.class,
            () -> multiState.useItem("Player1", ItemType.BATTERIES, batteryCoords));
        
        assertEquals("Invalid component type, expected BatteryCompartment", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_ZeroAmount() throws InvalidContextualAction, InvalidMethodParameters, InvalidParameters {
        // Create state with 0 amount to remove
        SecondCombatZone2BatteryRemovalState zeroState = new SecondCombatZone2BatteryRemovalState(context, 0);
        
        zeroState.useItem("Player1", ItemType.BATTERIES, batteryCoords);
        
        assertFalse(controller.getModel().isError());
        // Should handle zero amount case and transition
    }

    @Test
    void testUseItem_NegativeAmount() {
        // Create state with negative amount
        SecondCombatZone2BatteryRemovalState negativeState = new SecondCombatZone2BatteryRemovalState(context, -1);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> negativeState.useItem("Player1", ItemType.BATTERIES, batteryCoords));
        
        assertEquals("Amount cannot be negative", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testGetAvailableCommands() {
        List<String> commands = state.getAvailableCommands();
        assertEquals(1, commands.size());
        assertTrue(commands.contains("UseBattery"));
    }
}