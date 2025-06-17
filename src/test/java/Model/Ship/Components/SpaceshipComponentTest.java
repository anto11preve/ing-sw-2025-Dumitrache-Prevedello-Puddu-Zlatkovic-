package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import Model.Enums.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceshipComponentTest {

    // Test implementation of SpaceshipComponent for testing
    private static class TestComponent extends SpaceshipComponent {
        public TestComponent() {
            super(Card.STRUCTURAL_MODULE, 
                  ConnectorType.UNIVERSAL, 
                  ConnectorType.UNIVERSAL, 
                  ConnectorType.UNIVERSAL, 
                  ConnectorType.UNIVERSAL);
        }
    }

    @Test
    public void testConstructor() {
        SpaceshipComponent component = new TestComponent();
        assertEquals(Card.STRUCTURAL_MODULE, component.getType());
        assertEquals(Direction.UP, component.getOrientation());
        assertFalse(component.isVisible());
    }
    
    @Test
    public void testSetVisible() {
        SpaceshipComponent component = new TestComponent();
        assertFalse(component.isVisible());
        
        component.setVisible();
        assertTrue(component.isVisible());
    }
    
    @Test
    public void testRotate() {
        SpaceshipComponent component = new TestComponent();
        assertEquals(Direction.UP, component.getOrientation());
        
        component.rotate();
        assertEquals(Direction.RIGHT, component.getOrientation());
        
        component.rotate();
        assertEquals(Direction.DOWN, component.getOrientation());
        
        component.rotate();
        assertEquals(Direction.LEFT, component.getOrientation());
        
        component.rotate();
        assertEquals(Direction.UP, component.getOrientation());
    }
    
    @Test
    public void testSetOrientation() {
        SpaceshipComponent component = new TestComponent();
        assertEquals(Direction.UP, component.getOrientation());
        
        component.setOrientation(Direction.DOWN);
        assertEquals(Direction.DOWN, component.getOrientation());
        
        component.setOrientation(Direction.LEFT);
        assertEquals(Direction.LEFT, component.getOrientation());
    }
    
    @Test
    public void testGetConnectorAt() {
        SpaceshipComponent component = new TestComponent();
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.FRONT));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.REAR));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.LEFT));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.RIGHT));
        
        // Test after rotation
        component.rotate();
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.FRONT));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.REAR));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.LEFT));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorAt(Side.RIGHT));
    }
    
    @Test
    public void testDefaultMethods() {
        SpaceshipComponent component = new TestComponent();
        
        // Default firepower should be 0
        assertEquals(0, component.getFirepower(Direction.UP));
        
        // Default thrust should be 0
        assertEquals(0, component.getThrust(Direction.DOWN));
        
        // Default blocks should be false
        assertFalse(component.blocks(Direction.LEFT));
    }
}