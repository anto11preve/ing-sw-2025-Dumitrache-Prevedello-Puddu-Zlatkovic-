package Model.Ship;

import Model.Enums.Good;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GoodCounter class which tracks different types of goods on the ship.
 * Tests adding and removing goods of different colors.
 */
public class GoodCounterTest {

    /**
     * Tests the constructor to ensure a new GoodCounter is properly initialized:
     * - All good counts should start at zero
     */
    @Test
    public void testConstructor() {
        GoodCounter counter = new GoodCounter();
        assertEquals(0, counter.getRed());
        assertEquals(0, counter.getBlue());
        assertEquals(0, counter.getGreen());
        assertEquals(0, counter.getYellow());
    }
    
    /**
     * Tests adding different types of goods:
     * - Each good type should be tracked independently
     * - Adding one type shouldn't affect other types
     * - The count should increase by one for each addition
     */
    @Test
    public void testAddGood() {
        GoodCounter counter = new GoodCounter();
        
        counter.addGood(Good.RED);
        assertEquals(1, counter.getRed());
        assertEquals(0, counter.getBlue());
        
        counter.addGood(Good.BLUE);
        assertEquals(1, counter.getRed());
        assertEquals(1, counter.getBlue());
        
        counter.addGood(Good.GREEN);
        assertEquals(1, counter.getGreen());
        
        counter.addGood(Good.YELLOW);
        assertEquals(1, counter.getYellow());
    }
    
    /**
     * Tests adding multiple goods of the same type:
     * - The counter should accumulate multiple goods of the same type
     * - Each addition should increase the count by one
     */
    @Test
    public void testAddMultipleGoods() {
        GoodCounter counter = new GoodCounter();
        
        counter.addGood(Good.RED);
        counter.addGood(Good.RED);
        assertEquals(2, counter.getRed());
        
        counter.addGood(Good.BLUE);
        counter.addGood(Good.BLUE);
        counter.addGood(Good.BLUE);
        assertEquals(3, counter.getBlue());
    }
    
    /**
     * Tests removing goods:
     * - Removing a good should decrease its count by one
     * - The count should not go below zero
     */
    @Test
    public void testRemoveGood() {
        GoodCounter counter = new GoodCounter();
        
        counter.addGood(Good.RED);
        counter.addGood(Good.RED);
        counter.removeGood(Good.RED);
        assertEquals(1, counter.getRed());
        
        counter.removeGood(Good.RED);
        assertEquals(0, counter.getRed());
    }
    
    /**
     * Tests removing goods when none exist:
     * - Removing a good that doesn't exist should not cause errors
     * - The count should remain at zero
     */
    @Test
    public void testRemoveNonExistentGood() {
        GoodCounter counter = new GoodCounter();
        
        // Should not throw exception
        counter.removeGood(Good.RED);
        assertEquals(0, counter.getRed());
    }
}