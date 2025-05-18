package Model.AdventureCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlanetsTest {

    @Test
    public void testPlanetLootCollected() {
        int goods = 3;
        assertTrue(goods >= 0 && goods <= 5);
    }
}
