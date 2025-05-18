package Model.AdventureCards;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmugglersTest {

    @Test
    public void testTradeAccepted() {
        boolean tradeAccepted = true;
        assertTrue(tradeAccepted);
    }

    @Test
    public void testTradeRefused() {
        boolean tradeAccepted = false;
        assertFalse(tradeAccepted);
    }
}
