package Model.Ship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for DoubleCannonsCounter.
 * Verifies getters and manual state.
 */
public class DoubleCannonsCounterTest {

    @Test
    public void testConstructorAndGetters() {
        DoubleCannonsCounter counter = new DoubleCannonsCounter(3, 5);
        assertEquals(3, counter.getFrontCannons());
        assertEquals(5, counter.getOtherCannons());
    }

    @Test
    public void testDefaultConstructor() {
        DoubleCannonsCounter counter = new DoubleCannonsCounter();
        assertEquals(0, counter.getFrontCannons());
        assertEquals(0, counter.getOtherCannons());
    }
}
