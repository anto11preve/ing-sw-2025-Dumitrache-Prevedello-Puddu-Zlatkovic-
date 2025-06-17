package Model.Ship;

import Model.Enums.Good;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoodCounterTest {

    @Test
    public void testConstructor() {
        GoodCounter counter = new GoodCounter();
        assertEquals(0, counter.getRed());
        assertEquals(0, counter.getBlue());
        assertEquals(0, counter.getGreen());
        assertEquals(0, counter.getYellow());
    }
    
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
    
    @Test
    public void testRemoveNonExistentGood() {
        GoodCounter counter = new GoodCounter();
        
        // Should not throw exception
        counter.removeGood(Good.RED);
        assertEquals(0, counter.getRed());
    }
}