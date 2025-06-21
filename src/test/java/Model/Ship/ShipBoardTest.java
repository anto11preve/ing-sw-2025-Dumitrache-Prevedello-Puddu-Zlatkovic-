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

public class ShipBoardTest {

    @Test
    public void testConstructor() {
        ShipBoard board = new ShipBoard();
        assertTrue(board.isEmpty());
        assertNull(board.getActiveComponent());
    }
    
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
//    TODO: test inutili, non esitono più thrust e firepower, activate. Inooltre abbiamo riscirtto calulate firepower e calculate thrust. Vanno rifatti
//    @Test
//    public void testCalculateFirepower() throws InvalidMethodParameters {
//        ShipBoard board = new ShipBoard();
//
//        // Add a cannon facing UP
//        Cannon cannon = new Cannon(Card.CANNON,
//                ConnectorType.NONE, ConnectorType.UNIVERSAL,
//                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
//                false);
//        Coordinates cannonCoords = new Coordinates(6, 7);
//        board.addComponent(cannon, cannonCoords);
//
//        // Firepower should be 1 when ship is facing UP
//        assertEquals(1, board.calculateFirepower(Direction.UP));
//        assertEquals(0, board.calculateFirepower(Direction.DOWN));
//
//        // Add a double cannon facing RIGHT
//        Cannon doubleCannon = new Cannon(Card.DOUBLE_CANNON,
//                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
//                ConnectorType.UNIVERSAL, ConnectorType.NONE,
//                true);
//        doubleCannon.activate(); // Activate the double cannon
//        doubleCannon.rotate(); // Now facing RIGHT
//        Coordinates doubleCannonCoords = new Coordinates(6, 8);
//        board.addComponent(doubleCannon, doubleCannonCoords);
//
//        // Total firepower should be 1 + 0 = 1 when ship is facing UP
//        assertEquals(1, board.calculateFirepower(Direction.UP));
//        // Total firepower should be 0 + 2 = 2 when ship is facing RIGHT
//        assertEquals(2, board.calculateFirepower(Direction.RIGHT));
//    }
//
//    @Test
//    public void testCalculateThrust() throws InvalidMethodParameters {
//        ShipBoard board = new ShipBoard();
//
//        // Add an engine facing UP (thrust in DOWN direction)
//        Engine engine = new Engine(Card.ENGINE,
//                ConnectorType.UNIVERSAL, ConnectorType.NONE,
//                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
//                false);
//        Coordinates engineCoords = new Coordinates(6, 7);
//        board.addComponent(engine, engineCoords);
//
//        // Thrust should be 1 when ship rear is DOWN
//        assertEquals(1, board.calculateThrust(Direction.DOWN));
//        assertEquals(0, board.calculateThrust(Direction.UP));
//
//        // Add a double engine facing LEFT (thrust in RIGHT direction)
//        Engine doubleEngine = new Engine(Card.DOUBLE_ENGINE,
//                ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL,
//                ConnectorType.NONE, ConnectorType.UNIVERSAL,
//                true);
//        doubleEngine.activate(); // Activate the double engine
//        doubleEngine.rotate();
//        doubleEngine.rotate();
//        doubleEngine.rotate(); // Now facing LEFT
//        Coordinates doubleEngineCoords = new Coordinates(6, 8);
//        board.addComponent(doubleEngine, doubleEngineCoords);
//
//        // Total thrust should be 1 + 0 = 1 when ship rear is DOWN
//        assertEquals(1, board.calculateThrust(Direction.DOWN));
//        // Total thrust should be 0 + 2 = 2 when ship rear is RIGHT
//        assertEquals(2, board.calculateThrust(Direction.RIGHT));
//    }
}