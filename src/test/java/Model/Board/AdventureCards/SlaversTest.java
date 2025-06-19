package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SlaversTest {

    @Test
    public void testConstructor() {
        Slavers card = new Slavers(1, CardLevel.LEVEL_THREE, 5, 2, 1, 4);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals("Schiavisti", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(5, card.getPower());
        assertEquals(2, card.getLossPenalty().getAmount());
        assertEquals(1, card.getWinPenalty().getAmount());
        assertEquals(4, card.getWinReward().getAmount());
    }

    @Test
    public void testJsonConstructorWithFullData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL3");
        json.addProperty("power", 6);
        
        JsonObject reward = new JsonObject();
        reward.addProperty("credits", 5);
        json.add("reward", reward);
        
        JsonObject penalty = new JsonObject();
        penalty.addProperty("crewLoss", 3);
        penalty.addProperty("days", 2);
        json.add("penalty", penalty);
        
        Slavers card = new Slavers(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals(6, card.getPower());
        assertEquals(3, card.getLossPenalty().getAmount());
        assertEquals(2, card.getWinPenalty().getAmount());
        assertEquals(5, card.getWinReward().getAmount());
    }
    
    @Test
    public void testJsonConstructorWithMinimalData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL3");
        
        Slavers card = new Slavers(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        // Should use default values
        assertTrue(card.getPower() > 0);
        assertEquals(1, card.getLossPenalty().getAmount());
        assertEquals(0, card.getWinPenalty().getAmount());
        assertEquals(2, card.getWinReward().getAmount());
    }
}