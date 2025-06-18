package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Engine component which provides thrust to the ship.
 * Tests both regular and double engines, their orientation, and activation states.
 */
public class EngineTest {

    /**
     * Tests the constructor to ensure a new Engine is properly initialized:
     * - The component type should be ENGINE
     * - The default orientation should be UP
     * - The double engine flag should be set correctly
     */
    @Test
    public void testConstructor() {
        Engine engine = new Engine(Card.ENGINE, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.NONE, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  false);
        
        assertEquals(Card.ENGINE, engine.getType());
        assertEquals(Direction.UP, engine.getOrientation());
        assertFalse(engine.isDoubleEngine());
    }
    
    /**
     * Tests the thrust calculation for regular engines:
     * - An engine provides thrust in the opposite direction of its orientation
     * - An engine facing UP provides thrust in the DOWN direction
     * - After rotation, thrust should be provided in the new opposite direction
     */
    @Test
    public void testGetThrust() {
        Engine engine = new Engine(Card.ENGINE, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.NONE, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  false);
        
        // Engine should provide thrust when facing the ship's rear
        assertEquals(0, engine.getThrust(Direction.UP));
        assertEquals(1, engine.getThrust(Direction.DOWN));
        assertEquals(0, engine.getThrust(Direction.LEFT));
        assertEquals(0, engine.getThrust(Direction.RIGHT));
        
        // Test after rotation
        engine.rotate(); // Now facing RIGHT
        assertEquals(0, engine.getThrust(Direction.UP));
        assertEquals(0, engine.getThrust(Direction.DOWN));
        assertEquals(1, engine.getThrust(Direction.LEFT));
        assertEquals(0, engine.getThrust(Direction.RIGHT));
    }
    
    /**
     * Tests double engines which provide double thrust when activated:
     * - A double engine should provide 2 thrust when activated
     * - After rotation, double thrust should be provided in the new opposite direction
     * - When deactivated, no thrust should be provided
     */
    @Test
    public void testDoubleEngine() {
        Engine engine = new Engine(Card.DOUBLE_ENGINE, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.NONE, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  true);
        
        // Double engine should provide double thrust when activated
        engine.activate();
        assertEquals(0, engine.getThrust(Direction.UP));
        assertEquals(2, engine.getThrust(Direction.DOWN));
        
        // Test after rotation
        engine.rotate(); // Now facing RIGHT
        assertEquals(0, engine.getThrust(Direction.UP));
        assertEquals(0, engine.getThrust(Direction.DOWN));
        assertEquals(2, engine.getThrust(Direction.LEFT));
        assertEquals(0, engine.getThrust(Direction.RIGHT));
        
        // Test deactivation
        engine.deactivate();
        assertEquals(0, engine.getThrust(Direction.LEFT));
    }
}