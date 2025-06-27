package Controller_Ale.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.MatchLevel;
import Controller.Enums.RewardType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.GamePhases.FlightPhase;
import Controller.Pirates.PiratesBatteryRemovalState;
import Controller.Pirates.PiratesPowerDeclarationState;
import Controller.Pirates.PiratesRewardState;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiratesBatteryRemovalStateTest {

    private Controller controller;
    private Context context;
    private PiratesBatteryRemovalState state;
    private Player player1;
    private Player player2;
    private BatteryCompartment battery;

    @BeforeEach
    void setUp() {
        controller = new Controller(MatchLevel.TRIAL, 1);
        controller.getModel().addPlayer("Player1");
        controller.getModel().addPlayer("Player2");
        
        player1 = controller.getModel().getPlayer("Player1");
        player2 = controller.getModel().getPlayer("Player2");
        
        context = new Context(controller);
        context.getPlayers().add(player1);
        context.getPlayers().add(player2);
        
        // Set power using reflection
        try {
            java.lang.reflect.Field powerField = Context.class.getDeclaredField("power");
            powerField.setAccessible(true);
            powerField.set(context, 5);
        } catch (Exception e) {
            fail("Failed to set power: " + e.getMessage());
        }
        
        battery = new BatteryCompartment(Card.BATTERY_COMPARTMENT, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, 
                                        ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, 5);
        
        try {
            player1.getShipBoard().addComponent(battery, new Coordinates(6, 7));
            player1.getShipBoard().getCondensedShip().addBatteryCompartment(battery);
        } catch (Exception e) {
            fail("Failed to add components: " + e.getMessage());
        }
        
        state = new PiratesBatteryRemovalState(context, 6.0, 1);
    }

    @Test
    void testConstructor() {
        assertNotNull(state);
        assertEquals(player1, state.getPlayerInTurn());
    }

    @Test
    void testUseItem_Success() throws InvalidContextualAction, InvalidParameters {
        state.useItem("Player1", ItemType.BATTERIES, new Coordinates(6, 7));
        
        assertFalse(controller.getModel().isError());
        assertInstanceOf(PiratesRewardState.class, controller.getModel().getState());
    }

    @Test
    void testUseItem_InvalidItemType() {
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> state.useItem("Player1", ItemType.CREW, new Coordinates(6, 7)));
        
        assertEquals("Invalid item type, expected BATTERIES", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_NegativeDeclaredPower() {
        PiratesBatteryRemovalState negativeState = new PiratesBatteryRemovalState(context, -1.0, 1);
        
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> negativeState.useItem("Player1", ItemType.BATTERIES, new Coordinates(6, 7)));
        
        assertEquals("Declared power cannot be negative", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_NullCoordinates() {
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> state.useItem("Player1", ItemType.BATTERIES, null));
        
        assertEquals("Coordinates cannot be null", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_NoBatteriesToRemove() {
        PiratesBatteryRemovalState noBatteriesState = new PiratesBatteryRemovalState(context, 6.0, 0);
        
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> noBatteriesState.useItem("Player1", ItemType.BATTERIES, new Coordinates(6, 7)));
        
        assertEquals("No batteries to remove", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_WrongPlayer() {
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> state.useItem("Player2", ItemType.BATTERIES, new Coordinates(6, 7)));
        
        assertEquals("It's not the player's turn", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testUseItem_InvalidComponent() {
        InvalidContextualAction exception = assertThrows(InvalidContextualAction.class,
            () -> state.useItem("Player1", ItemType.BATTERIES, new Coordinates(1, 1)));
        
        assertEquals("Invalid component type, expected BatteryCompartment", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testGetReward_Success() throws InvalidParameters {
        PiratesBatteryRemovalState rewardState = new PiratesBatteryRemovalState(context, 6.0, 0);
        
        rewardState.getReward("Player1", RewardType.CREDITS);
        
        assertFalse(controller.getModel().isError());
        assertInstanceOf(PiratesRewardState.class, controller.getModel().getState());
    }

    @Test
    void testGetReward_InvalidRewardType() {
        PiratesBatteryRemovalState rewardState = new PiratesBatteryRemovalState(context, 6.0, 0);
        
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> rewardState.getReward("Player1", RewardType.GOODS));
        
        assertEquals("Invalid reward type, expected CREDITS", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testGetAvailableCommands() {
        List<String> commands = state.getAvailableCommands();
        assertEquals(2, commands.size());
        assertTrue(commands.contains("UseBattery"));
        assertTrue(commands.contains("GetCreditsReward"));
    }

    @Test
    void testUseItem_EqualPower_RemovePlayer_SwitchState() throws InvalidContextualAction, InvalidParameters {
        PiratesBatteryRemovalState equalPowerState = new PiratesBatteryRemovalState(context, 5.0, 1);
        
        equalPowerState.useItem("Player1", ItemType.BATTERIES, new Coordinates(6, 7));
        
        assertFalse(controller.getModel().isError());
        assertInstanceOf(PiratesPowerDeclarationState.class, controller.getModel().getState());
    }

    @Test
    void testUseItem_LessPower_AddToSpecial_SwitchState() throws InvalidContextualAction, InvalidParameters {
        PiratesBatteryRemovalState lessPowerState = new PiratesBatteryRemovalState(context, 4.0, 1);
        
        lessPowerState.useItem("Player1", ItemType.BATTERIES, new Coordinates(6, 7));
        
        assertFalse(controller.getModel().isError());
        assertTrue(context.getSpecialPlayers().contains(player1));
        assertInstanceOf(PiratesPowerDeclarationState.class, controller.getModel().getState());
    }

    @Test
    void testUseItem_MultipleBatteries_StaysInState() throws InvalidContextualAction, InvalidParameters {
        PiratesBatteryRemovalState multipleBatteriesState = new PiratesBatteryRemovalState(context, 6.0, 2);
        
        multipleBatteriesState.useItem("Player1", ItemType.BATTERIES, new Coordinates(6, 7));
        
        assertFalse(controller.getModel().isError());
        assertInstanceOf(PiratesBatteryRemovalState.class, controller.getModel().getState());
    }

    @Test
    void testGetReward_WrongPlayer() {
        PiratesBatteryRemovalState rewardState = new PiratesBatteryRemovalState(context, 6.0, 0);
        
        InvalidParameters exception = assertThrows(InvalidParameters.class,
            () -> rewardState.getReward("Player2", RewardType.CREDITS));
        
        assertEquals("It's not the player's turn", exception.getMessage());
        assertTrue(controller.getModel().isError());
    }

    @Test
    void testGetReward_DeclaredPowerTooLow() throws InvalidParameters {
        PiratesBatteryRemovalState lowPowerState = new PiratesBatteryRemovalState(context, 4.0, 0);
        
        lowPowerState.getReward("Player1", RewardType.CREDITS);
        
        // No state change should occur since declaredPower <= context.getPower()
        assertInstanceOf(PiratesBatteryRemovalState.class, controller.getModel().getState());
    }
}