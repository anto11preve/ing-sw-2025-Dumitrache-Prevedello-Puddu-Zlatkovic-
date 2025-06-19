package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EpidemicTest {

    @Test
    public void testConstructor() {
        Epidemic card = new Epidemic(1, CardLevel.LEVEL_THREE);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals("Epidemia", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(0, card.getCrewLost());
    }

    @Test
    public void testJsonConstructorWithCrewLoss() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL3");
        
        JsonObject penalty = new JsonObject();
        penalty.addProperty("crewLoss", 3);
        json.add("penalty", penalty);
        
        Epidemic card = new Epidemic(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals(3, card.getCrewLost());
    }
    
    @Test
    public void testJsonConstructorWithoutCrewLoss() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL3");
        
        Epidemic card = new Epidemic(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals(0, card.getCrewLost());
    }
}