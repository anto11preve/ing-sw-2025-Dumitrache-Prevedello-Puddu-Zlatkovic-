package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnginesCounterTest {

    @Test
    public void testConstructor() {
        EnginesCounter counter = new EnginesCounter();
        assertEquals(0, counter.getSingleEngines());
        assertEquals(0, counter.getDoubleEngines());
    }
    
    @Test
    public void testSetEngines() {
        EnginesCounter counter = new EnginesCounter();
        
        counter.setSingleEngines(2);
        assertEquals(2, counter.getSingleEngines());
        
        counter.setDoubleEngines(1);
        assertEquals(1, counter.getDoubleEngines());
    }
}