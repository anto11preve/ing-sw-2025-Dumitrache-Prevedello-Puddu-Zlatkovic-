package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StardustTest {

    @Test
    public void testConstructor() {
        Stardust card = new Stardust(1, CardLevel.LEVEL_TWO);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        assertEquals("Polvere Stellare", card.getName());
        assertEquals("", card.getDescription());
    }

    @Test
    public void testJsonConstructor() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL2");
        
        Stardust card = new Stardust(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
    }
}