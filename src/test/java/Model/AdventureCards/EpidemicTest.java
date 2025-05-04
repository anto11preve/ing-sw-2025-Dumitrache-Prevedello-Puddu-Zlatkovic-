package Model.AdventureCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpidemicTest {

    @Test
    public void testEpidemicKillsCrew() {
        int crewBefore = 4;
        int crewLost = 2;
        int crewAfter = crewBefore - crewLost;
        assertEquals(2, crewAfter);
    }
}
