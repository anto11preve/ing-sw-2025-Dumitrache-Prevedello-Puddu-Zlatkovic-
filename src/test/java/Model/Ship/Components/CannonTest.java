package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Cannon component which provides firepower to the ship.
 * Tests both regular and double cannons, their orientation, and activation states.
 */
public class CannonTest {

    /**
     * Tests the constructor to ensure a new Cannon is properly initialized:
     * - The component type should be CANNON
     * - The default orientation should be UP
     * - The double flag should be set correctly
     */
    @Test
    public void testConstructor() {
        Cannon cannon = new Cannon(Card.CANNON, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  false);
        
        assertEquals(Card.CANNON, cannon.getType());
        assertEquals(Direction.UP, cannon.getOrientation());
        assertFalse(cannon.isDouble());
    }
    
    /**
     * Tests the firepower calculation for regular cannons:
     * - A cannon should provide 1 firepower in its facing direction
     * - No firepower should be provided in other directions
     * - After rotation, firepower should be provided in the new direction
     */
    @Test
    public void testGetFirepower() {
        Cannon cannon = new Cannon(Card.CANNON, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  false);
        
        // Cannon should have firepower in its facing direction
        assertEquals(1, cannon.getFirepower(Direction.UP));
        assertEquals(0, cannon.getFirepower(Direction.DOWN));
        assertEquals(0, cannon.getFirepower(Direction.LEFT));
        assertEquals(0, cannon.getFirepower(Direction.RIGHT));
        
        // Test after rotation
        cannon.rotate(); // Now facing RIGHT
        assertEquals(0, cannon.getFirepower(Direction.UP));
        assertEquals(0, cannon.getFirepower(Direction.DOWN));
        assertEquals(0, cannon.getFirepower(Direction.LEFT));
        assertEquals(1, cannon.getFirepower(Direction.RIGHT));
    }
    
    /**
     * Tests double cannons which provide double firepower when activated:
     * - A double cannon should provide 2 firepower when activated
     * - After rotation, double firepower should be provided in the new direction
     * - When deactivated, no firepower should be provided
     */
    @Test
    public void testDoubleCannon() {
        Cannon cannon = new Cannon(Card.DOUBLE_CANNON, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  true);
        
        // Double cannon should have double firepower when activated
        cannon.activate();
        assertEquals(2, cannon.getFirepower(Direction.UP));
        assertEquals(0, cannon.getFirepower(Direction.DOWN));
        
        // Test after rotation
        cannon.rotate(); // Now facing RIGHT
        assertEquals(0, cannon.getFirepower(Direction.UP));
        assertEquals(2, cannon.getFirepower(Direction.RIGHT));
        
        // Test deactivation
        cannon.deactivate();
        assertEquals(0, cannon.getFirepower(Direction.RIGHT));
    }
}