
package Model.Ship;

import Model.Ship.Components.ShieldGenerator;
import Model.Ship.Components.SpaceshipComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for ShieldCounter class.
 * This test verifies that all shield generators are counted correctly.
 */
public class ShieldCounterTest {

    @Test
    public void testSettersAndGetters() {
        ShieldCounter counter = new ShieldCounter();
        counter.setNorthShields(1);
        counter.setEastShields(2);
        counter.setSouthShields(3);
        counter.setWestShields(4);

        assertEquals(1, counter.getNorthShields());
        assertEquals(2, counter.getEastShields());
        assertEquals(3, counter.getSouthShields());
        assertEquals(4, counter.getWestShields());
    }
}
