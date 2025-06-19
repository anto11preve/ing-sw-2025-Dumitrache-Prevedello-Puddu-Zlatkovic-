package Model.Board.AdventureCards.Rewards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditsTest {

    @Test
    public void testConstructor() {
        Credits credits = new Credits(5);
        assertEquals(5, credits.getAmount());
    }
    
    @Test
    public void testZeroCredits() {
        Credits credits = new Credits(0);
        assertEquals(0, credits.getAmount());
    }
    
    @Test
    public void testNegativeCredits() {
        Credits credits = new Credits(-1);
        // Implementation might handle negative values differently
        // This test assumes it stores the value as provided
        assertEquals(-1, credits.getAmount());
    }
}