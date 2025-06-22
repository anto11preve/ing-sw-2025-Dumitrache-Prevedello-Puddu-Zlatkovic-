package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the EnginesCounter class which tracks the number of engines on the ship.
 * Tests both single and double engine counting functionality.
 */
public class EnginesCounterTest {

    /**
     * Tests the constructor to ensure a new EnginesCounter is properly initialized:
     * - Both single and double engine counts should start at zero
     */
    @Test
    public void testConstructor() {
        EnginesCounter counter = new EnginesCounter();
        assertEquals(0, counter.getSingleEngines());
        assertEquals(0, counter.getDoubleEngines());
    }
    
    /**
     * Tests setting engine counts:
     * - Single engine count can be set to a specific value
     * - Double engine count can be set to a specific value
     * - Each count is tracked independently
     */
    @Test
    public void testSetEngines() {
        EnginesCounter counter = new EnginesCounter();
        
        counter.setSingleEngines(2);
        assertEquals(2, counter.getSingleEngines());
        
        counter.setDoubleEngines(1);
        assertEquals(1, counter.getDoubleEngines());
    }
}