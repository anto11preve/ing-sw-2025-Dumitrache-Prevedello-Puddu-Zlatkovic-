package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import Model.Enums.Good;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AbandonedStationTest {

    @Test
    public void testConstructor() {
        List<Good> goods = new ArrayList<>();
        goods.add(Good.RED);
        goods.add(Good.BLUE);
        
        AbandonedStation card = new AbandonedStation(1, CardLevel.LEVEL_ONE, 2, goods, 1);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals("Stazione Abbandonata", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(2, card.getCrew());
        assertEquals(1, card.getLandingPenalty().getAmount());
        // Count the goods in the landing reward
        int goodsCount = 0;
        for (Good g : card.getLandingReward()) {
            goodsCount++;
        }
        assertEquals(2, goodsCount);
    }

    @Test
    public void testJsonConstructorWithFullData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL1");
        json.addProperty("crewRequired", 3);
        json.addProperty("days", 2);
        
        JsonObject reward = new JsonObject();
        reward.addProperty("cargo", 3);
        json.add("reward", reward);
        
        AbandonedStation card = new AbandonedStation(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals(3, card.getCrew());
        assertEquals(2, card.getLandingPenalty().getAmount());
    }
    
    @Test
    public void testJsonConstructorWithMinimalData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL1");
        
        AbandonedStation card = new AbandonedStation(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        // Should use default values
        assertEquals(1, card.getCrew());
        assertEquals(0, card.getLandingPenalty().getAmount());
    }
}