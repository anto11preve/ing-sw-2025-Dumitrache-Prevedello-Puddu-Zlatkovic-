package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpenSpaceTest {

    @Test
    public void testConstructor() {
        OpenSpace card = new OpenSpace(1, CardLevel.LEARNER);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
        assertEquals("Spazio Aperto", card.getName());
        assertEquals("", card.getDescription());
    }

    @Test
    public void testJsonConstructor() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "TRIAL");
        OpenSpace card = new OpenSpace(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
    }
}