package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import Model.Enums.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the SpaceshipComponent abstract class which is the base class for all
 * ship components. Tests focus on common functionality like rotation, orientation,
 * and connector management that all components share.
 */
public class SpaceshipComponentTest {

    /**
     * Test implementation of SpaceshipComponent for testing purposes.
     * Since SpaceshipComponent is abstract, we need a concrete subclass to test it.
     */
    private static class TestComponent extends SpaceshipComponent {
        public TestComponent() {
            super(Card.STRUCTURAL_MODULE, 
                  ConnectorType.UNIVERSAL, 
                  ConnectorType.UNIVERSAL, 
                  ConnectorType.UNIVERSAL, 
                  ConnectorType.UNIVERSAL);
        }
    }

    /**
     * Tests the constructor to ensure a new SpaceshipComponent is properly initialized:
     * - The component type should be set correctly
     * - The default orientation should be UP
     * - The component should not be visible by default
     */
    @Test
    public void testConstructor() {
        SpaceshipComponent component = new TestComponent();
        assertEquals(Card.STRUCTURAL_MODULE, component.getType());
        assertEquals(Direction.UP, component.getOrientation());
        assertFalse(component.isVisible());
    }
    
    /**
     * Tests setting a component to visible:
     * - Initially, the component should not be visible
     * - After calling setVisible(), the component should be visible
     */
    @Test
    public void testSetVisible() {
        SpaceshipComponent component = new TestComponent();
        assertFalse(component.isVisible());
        
        component.setVisible();
        assertTrue(component.isVisible());
    }
    
    /**
     * Tests the rotation functionality:
     * - The component should start with UP orientation
     * - Each rotate() call should rotate the component 90 degrees clockwise
     * - After 4 rotations, the component should return to its original orientation
     */
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
    
    /**
     * Tests setting the orientation directly:
     * - setOrientation() should rotate the component to the specified direction
     * - The component should take the shortest path to reach the target orientation
     */
    @Test
    public void testSetOrientation() {
        SpaceshipComponent component = new TestComponent();
        assertEquals(Direction.UP, component.getOrientation());
        
        component.setOrientation(Direction.DOWN);
        assertEquals(Direction.DOWN, component.getOrientation());
        
        component.setOrientation(Direction.LEFT);
        assertEquals(Direction.LEFT, component.getOrientation());
    }
    
    /**
     * Tests getting connector types at different sides:
     * - getConnectorAt() should return the correct connector type for each side
     * - When rotated, the connectors should maintain their relative positions
     */
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
    
    /**
     * Tests the default implementations of component-specific methods:
     * - Default firepower should be 0 (only cannons provide firepower)
     * - Default thrust should be 0 (only engines provide thrust)
     * - Default blocks should be false (only shields block incoming fire)
     */
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