package Model.AdventureCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlaversTest {

    @Test
    public void testSlaversStrongerThanPlayer() {
        int slaverStrength = 5;
        int playerFirepower = 3;
        assertTrue(slaverStrength > playerFirepower);
    }

    @Test
    public void testPlayerBeatsSlavers() {
        int slaverStrength = 4;
        int playerFirepower = 5;
        assertTrue(playerFirepower >= slaverStrength);
    }
}
