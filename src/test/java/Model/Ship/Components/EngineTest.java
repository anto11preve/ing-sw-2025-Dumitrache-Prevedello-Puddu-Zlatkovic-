package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

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