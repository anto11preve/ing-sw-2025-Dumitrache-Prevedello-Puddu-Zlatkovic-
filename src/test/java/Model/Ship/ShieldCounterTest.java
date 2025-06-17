package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShieldCounterTest {

    @Test
    public void testConstructor() {
        ShieldCounter counter = new ShieldCounter();
        assertEquals(0, counter.getNorthShields());
        assertEquals(0, counter.getEastShields());
        assertEquals(0, counter.getSouthShields());
        assertEquals(0, counter.getWestShields());
    }
    
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