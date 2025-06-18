package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ShieldCounter class which tracks shields in different directions.
 * Tests shield counting functionality for all four cardinal directions.
 */
public class ShieldCounterTest {

    /**
     * Tests the constructor to ensure a new ShieldCounter is properly initialized:
     * - Shield counts in all directions should start at zero
     */
    @Test
    public void testConstructor() {
        ShieldCounter counter = new ShieldCounter();
        assertEquals(0, counter.getNorthShields());
        assertEquals(0, counter.getEastShields());
        assertEquals(0, counter.getSouthShields());
        assertEquals(0, counter.getWestShields());
    }
    
    /**
     * Tests setting shield counts in different directions:
     * - Shield counts can be set independently for each direction
     * - Each direction's count is tracked separately
     * - Setting one direction doesn't affect other directions
     */
    @Test
    public void testSetShields() {
        ShieldCounter counter = new ShieldCounter();
        
        counter.setNorthShields(2);
        assertEquals(2, counter.getNorthShields());
        
        counter.setEastShields(1);
        assertEquals(1, counter.getEastShields());
        
        counter.setSouthShields(3);
        assertEquals(3, counter.getSouthShields());
        
        counter.setWestShields(2);
        assertEquals(2, counter.getWestShields());
    }
}