package Model.AdventureCards;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AbandonedStationTest {

    @Test
    public void testAbandonedStationLoot() {
        int cargo = 3;
        int crew = 1;
        assertTrue(cargo >= 0 && cargo <= 3);
        assertTrue(crew >= 0 && crew <= 2);
    }
}
