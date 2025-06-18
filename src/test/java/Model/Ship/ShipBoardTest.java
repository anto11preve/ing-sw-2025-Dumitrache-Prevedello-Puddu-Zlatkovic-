package Model.Ship;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import Model.Exceptions.InvalidMethodParameters;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.Engine;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Components.StructuralModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ShipBoard class which manages the spaceship's grid, component placement,
 * connections between components, and ship capabilities like firepower and thrust.
 */
public class ShipBoardTest {

    /**
     * Tests the constructor to ensure a new ShipBoard is properly initialized:
     * - The board should be empty
     * - No active component should be set
     */
    @Test
    public void testConstructor() {
        ShipBoard board = new ShipBoard();
        assertTrue(board.isEmpty());
        assertNull(board.getActiveComponent());
    }
    
    /**
     * Tests adding a component to the board:
     * - The first component can be placed anywhere on the board
     * - After adding, the board should not be empty
     * - The component should be retrievable at the specified coordinates
     */
    @Test
    public void testAddComponent() throws InvalidMethodParameters {
        ShipBoard board = new ShipBoard();
        SpaceshipComponent component = new StructuralModule(Card.STRUCTURAL_MODULE,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL);
        
        // First component can be placed anywhere
        Coordinates coords = new Coordinates(6, 7); // Center of the board
        board.addComponent(component, coords);
        
        assertFalse(board.isEmpty());
        assertEquals(component, board.getComponent(coords));
    }
    
    /**
     * Tests that adding a component at invalid coordinates throws an exception:
     * - Coordinates outside the board boundaries should be rejected
     * - The method should throw InvalidMethodParameters
     */
    @Test
    public void testAddComponentInvalidPosition() {
        ShipBoard board = new ShipBoard();
        SpaceshipComponent component = new StructuralModule(Card.STRUCTURAL_MODULE,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL);
        
        // Out of bounds
        Coordinates coords = new Coordinates(20, 20);
        assertThrows(InvalidMethodParameters.class, () -> board.addComponent(component, coords));
    }
    
    /**
     * Tests removing a component from the board:
     * - After adding a component, the board should not be empty
     * - After removing the component, the board should be empty again
     * - The removeComponent method should properly clear the position
     */
    @Test
    public void testRemoveComponent() throws InvalidMethodParameters {
        ShipBoard board = new ShipBoard();
        SpaceshipComponent component = new StructuralModule(Card.STRUCTURAL_MODULE,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL);
        
        Coordinates coords = new Coordinates(6, 7);
        board.addComponent(component, coords);
        assertFalse(board.isEmpty());
        
        board.removeComponent(coords);
        assertTrue(board.isEmpty());
    }
    
    /**
     * Tests setting and getting the active component:
     * - Initially, no active component should be set
     * - After setting an active component, getActiveComponent should return it
     * - This tests the component selection functionality used during ship building
     */
    @Test
    public void testSetActiveComponent() {
        ShipBoard board = new ShipBoard();
        SpaceshipComponent component = new StructuralModule(Card.STRUCTURAL_MODULE,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL);
        
        assertNull(board.getActiveComponent());
        
        board.setActiveComponent(component);
        assertEquals(component, board.getActiveComponent());
    }
    
    /**
     * Tests calculating the ship's firepower based on cannon components:
     * - A regular cannon facing UP should provide 1 firepower when ship faces UP
     * - The same cannon should provide 0 firepower when ship faces DOWN
     * - A double cannon facing RIGHT should provide 2 firepower when ship faces RIGHT
     * - The total firepower should be the sum of all active cannons facing the ship's direction
     */
    @Test
    public void testCalculateFirepower() throws InvalidMethodParameters {
        ShipBoard board = new ShipBoard();
        
        // Add a cannon facing UP
        Cannon cannon = new Cannon(Card.CANNON,
                ConnectorType.NONE, ConnectorType.UNIVERSAL,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                false);
        Coordinates cannonCoords = new Coordinates(6, 7);
        board.addComponent(cannon, cannonCoords);
        
        // Firepower should be 1 when ship is facing UP
        assertEquals(1, board.calculateFirepower(Direction.UP));
        assertEquals(0, board.calculateFirepower(Direction.DOWN));
        
        // Add a double cannon facing RIGHT
        Cannon doubleCannon = new Cannon(Card.DOUBLE_CANNON,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                ConnectorType.UNIVERSAL, ConnectorType.NONE,
                true);
        doubleCannon.activate(); // Activate the double cannon
        doubleCannon.rotate(); // Now facing RIGHT
        Coordinates doubleCannonCoords = new Coordinates(6, 8);
        board.addComponent(doubleCannon, doubleCannonCoords);
        
        // Total firepower should be 1 + 0 = 1 when ship is facing UP
        assertEquals(1, board.calculateFirepower(Direction.UP));
        // Total firepower should be 0 + 2 = 2 when ship is facing RIGHT
        assertEquals(2, board.calculateFirepower(Direction.RIGHT));
    }
    
    /**
     * Tests calculating the ship's thrust based on engine components:
     * - An engine facing UP provides thrust in the DOWN direction
     * - A double engine provides twice the thrust of a regular engine
     * - The total thrust should be the sum of all engines facing the ship's rear direction
     * - Engines not facing the ship's rear direction should not contribute thrust
     */
    @Test
    public void testCalculateThrust() throws InvalidMethodParameters {
        ShipBoard board = new ShipBoard();
        
        // Add an engine facing UP (thrust in DOWN direction)
        Engine engine = new Engine(Card.ENGINE,
                ConnectorType.UNIVERSAL, ConnectorType.NONE,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                false);
        Coordinates engineCoords = new Coordinates(6, 7);
        board.addComponent(engine, engineCoords);
        
        // Thrust should be 1 when ship rear is DOWN
        assertEquals(1, board.calculateThrust(Direction.DOWN));
        assertEquals(0, board.calculateThrust(Direction.UP));
        
        // Add a double engine facing LEFT (thrust in RIGHT direction)
        Engine doubleEngine = new Engine(Card.DOUBLE_ENGINE,
                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
                ConnectorType.NONE, ConnectorType.UNIVERSAL,
                true);
        doubleEngine.activate(); // Activate the double engine
        doubleEngine.rotate();
        doubleEngine.rotate();
        doubleEngine.rotate(); // Now facing LEFT
        Coordinates doubleEngineCoords = new Coordinates(6, 8);
        board.addComponent(doubleEngine, doubleEngineCoords);
        
        // Total thrust should be 1 + 0 = 1 when ship rear is DOWN
        assertEquals(1, board.calculateThrust(Direction.DOWN));
        // Total thrust should be 0 + 2 = 2 when ship rear is RIGHT
        assertEquals(2, board.calculateThrust(Direction.RIGHT));
    }
}