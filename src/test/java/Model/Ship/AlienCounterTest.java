package Model.Ship;

import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for AlienCounter class.
 * This test verifies that the alien crew is counted correctly based on the cabins added.
 */
public class AlienCounterTest {

    @Test
    public void testAlienCount() {
        List<SpaceshipComponent> components = new ArrayList<>();

        // Create a cabin with an alien (purple)
        Cabin cabinWithAlien = new Cabin(true, "purple");
        components.add(cabinWithAlien);

        // Create a regular cabin without alien
        Cabin regularCabin = new Cabin(false, null);
        components.add(regularCabin);

        // Count alien crew
        AlienCounter counter = new AlienCounter();
        int result = counter.count(components);

        // Only one alien cabin should count as 2 alien crew
        assertEquals(2, result);
    }
}

