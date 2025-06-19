package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Enums.CardLevel;
import Model.Enums.Side;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PiratesTest {

    @Test
    public void testConstructor() {
        List<CannonShot> shots = new ArrayList<>();
        shots.add(new CannonShot(false, Side.FRONT));
        
        Pirates card = new Pirates(1, CardLevel.LEVEL_TWO, 4, shots, 2, 3);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        assertEquals("Pirati", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(4, card.getPower());
        assertEquals(2, card.getWinPenalty().getAmount());
        assertEquals(3, card.getWinReward().getAmount());
    }

    @Test
    public void testJsonConstructorWithFullData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL2");
        json.addProperty("power", 5);
        
        JsonObject reward = new JsonObject();
        reward.addProperty("credits", 4);
        json.add("reward", reward);
        
        JsonObject penalty = new JsonObject();
        penalty.addProperty("days", 3);
        json.add("penalty", penalty);
        
        Pirates card = new Pirates(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        assertEquals(5, card.getPower());
        assertEquals(3, card.getWinPenalty().getAmount());
        assertEquals(4, card.getWinReward().getAmount());
    }
    
    @Test
    public void testJsonConstructorWithMinimalData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL2");
        
        Pirates card = new Pirates(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        // Should use default values
        assertTrue(card.getPower() > 0);
        assertEquals(1, card.getWinPenalty().getAmount());
        assertEquals(2, card.getWinReward().getAmount());
    }
}