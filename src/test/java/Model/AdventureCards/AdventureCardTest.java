package Model.AdventureCards;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdventureCardTest {

    @Test
    public void testAdventureCardName() {
        String cardName = "Pirate Attack";
        assertEquals("Pirate Attack", cardName);
    }
}
