package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ShieldGenerator component which protects the ship from damage.
 * Tests shield orientation and blocking functionality.
 */
public class ShieldGeneratorTest {

    /**
     * Tests the constructor to ensure a new ShieldGenerator is properly initialized:
     * - The component type should be SHIELD_GENERATOR
     * - The default orientation should be UP
     * - The shield direction should match the specified direction
     */
    @Test
    public void testConstructor() {
        ShieldGenerator shield = new ShieldGenerator(Card.SHIELD_GENERATOR, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  Direction.UP);
        
        assertEquals(Card.SHIELD_GENERATOR, shield.getType());
        assertEquals(Direction.UP, shield.getOrientation());
        assertEquals(Direction.UP, shield.getDirection());
    }
    
    /**
     * Tests the shield's blocking functionality:
     * - A shield should block damage from its facing direction
     * - A shield should not block damage from other directions
     * - After changing direction, the shield should block from the new direction
     */
    @Test
    public void testBlocks() {
        ShieldGenerator shield = new ShieldGenerator(Card.SHIELD_GENERATOR, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  Direction.UP);
        
        // Shield should block in its facing direction
        assertTrue(shield.blocks(Direction.UP));
        assertFalse(shield.blocks(Direction.DOWN));
        assertFalse(shield.blocks(Direction.LEFT));
        assertFalse(shield.blocks(Direction.RIGHT));
        
        // Test after changing direction
        shield.setDirection(Direction.RIGHT);
        assertFalse(shield.blocks(Direction.UP));
        assertFalse(shield.blocks(Direction.DOWN));
        assertFalse(shield.blocks(Direction.LEFT));
        assertTrue(shield.blocks(Direction.RIGHT));
    }
}