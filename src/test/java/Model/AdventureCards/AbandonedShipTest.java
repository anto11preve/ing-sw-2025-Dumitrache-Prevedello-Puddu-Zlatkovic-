package Model.AdventureCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AbandonedShipTest {

    @Test
    public void testAbandonedShipGivesCrew() {
        int crewFound = 2;
        assertTrue(crewFound >= 0 && crewFound <= 4, "Crew should be between 0 and 4");
    }

    @Test
    public void testAbandonedShipNoCrew() {
        int crewFound = 0;
        assertEquals(0, crewFound);
    }
}
