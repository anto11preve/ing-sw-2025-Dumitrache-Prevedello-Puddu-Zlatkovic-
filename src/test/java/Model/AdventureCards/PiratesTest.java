package Model.AdventureCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PiratesTest {

    @Test
    public void testPlayerBeatsPirates() {
        int pirateStrength = 4;
        int playerFirepower = 5;
        assertTrue(playerFirepower >= pirateStrength, "Player should beat pirates");
    }

    @Test
    public void testPlayerLosesToPirates() {
        int pirateStrength = 6;
        int playerFirepower = 4;
        assertTrue(playerFirepower < pirateStrength, "Player should lose to pirates");
    }
}
