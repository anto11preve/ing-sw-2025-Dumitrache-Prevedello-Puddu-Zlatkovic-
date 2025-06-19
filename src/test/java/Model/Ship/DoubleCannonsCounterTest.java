package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleCannonsCounterTest {

    @Test
    public void testDefaultConstructor() {
        DoubleCannonsCounter counter = new DoubleCannonsCounter();
        assertEquals(0, counter.getFrontCannons());
        assertEquals(0, counter.getOtherCannons());
    }
    
    @Test
    public void testParameterizedConstructor() {
        DoubleCannonsCounter counter = new DoubleCannonsCounter(2, 3);
        assertEquals(2, counter.getFrontCannons());
        assertEquals(3, counter.getOtherCannons());
    }
    
    @Test
    public void testGetters() {
        DoubleCannonsCounter counter = new DoubleCannonsCounter(1, 2);
        assertEquals(1, counter.getFrontCannons());
        assertEquals(2, counter.getOtherCannons());
    }
}