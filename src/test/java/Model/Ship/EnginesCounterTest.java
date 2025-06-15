
package Model.Ship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for EnginesCounter.
 * Verifies default values and setter/getter logic.
 */
public class EnginesCounterTest {

    @Test
    public void testDefaultValues() {
        EnginesCounter counter = new EnginesCounter();
        assertEquals(0, counter.getSingleEngines());
        assertEquals(0, counter.getDoubleEngines());
    }

    @Test
    public void testSettersAndGetters() {
        EnginesCounter counter = new EnginesCounter();
        counter.setSingleEngines(2);
        counter.setDoubleEngines(1);

        assertEquals(2, counter.getSingleEngines());
        assertEquals(1, counter.getDoubleEngines());
    }
}
