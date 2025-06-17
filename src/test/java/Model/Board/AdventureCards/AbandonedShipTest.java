package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbandonedShipTest {

    @Test
    public void testConstructor() {
        AbandonedShip card = new AbandonedShip(1, CardLevel.LEVEL_THREE, 2, 3, 1);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals("Nave Abbandonata", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(2, card.getCrew());
        assertEquals(3, card.getLandingReward().getAmount());
        assertEquals(1, card.getLandingPenalty().getAmount());
    }

    @Test
    public void testJsonConstructorWithFullData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL3");
        json.addProperty("crewRequired", 2);
        json.addProperty("days", 3);
        
        JsonObject reward = new JsonObject();
        reward.addProperty("crew", 4);
        json.add("reward", reward);
        
        AbandonedShip card = new AbandonedShip(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals(4, card.getCrew());
        assertEquals(3, card.getLandingPenalty().getAmount());
    }
    
    @Test
    public void testJsonConstructorWithMinimalData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL3");
        
        AbandonedShip card = new AbandonedShip(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        // Should use default values
        assertEquals(1, card.getCrew());
        assertEquals(0, card.getLandingPenalty().getAmount());
    }
}