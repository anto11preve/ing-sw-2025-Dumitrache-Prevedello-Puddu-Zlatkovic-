package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.Planet;
import Model.Enums.CardLevel;
import Model.Enums.Good;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanetsTest {

    @Test
    public void testConstructor() {
        List<Planet> planets = new ArrayList<>();
        List<Good> goods = new ArrayList<>();
        goods.add(Good.RED);
        goods.add(Good.YELLOW);
        planets.add(new Planet("Test Planet", goods));
        
        Planets card = new Planets(1, CardLevel.LEVEL_ONE, 2, planets);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals("Pianeti", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(2, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
            assertEquals("Test Planet", p.getName());
            
            // Count the goods in the landing reward
            int goodsCount = 0;
            for (Good g : p.getLandingReward()) {
                goodsCount++;
            }
            assertEquals(2, goodsCount);
        }
        assertEquals(1, count);
    }

    @Test
    public void testJsonConstructorWithFullData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL1");
        
        JsonArray planetsArray = new JsonArray();
        JsonObject planet = new JsonObject();
        planet.addProperty("name", "Test Planet");
        JsonArray goodsArray = new JsonArray();
        goodsArray.add(new JsonPrimitive("RED"));
        goodsArray.add(new JsonPrimitive("BLUE"));
        planet.add("goods", goodsArray);
        planetsArray.add(planet);
        json.add("planets", planetsArray);
        
        JsonObject landingPenalty = new JsonObject();
        landingPenalty.addProperty("value", 2);
        json.add("landingPenalty", landingPenalty);
        
        Planets card = new Planets(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals(2, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
            assertEquals("Test Planet", p.getName());
            
            // Count the goods in the landing reward
            int goodsCount = 0;
            for (Good g : p.getLandingReward()) {
                goodsCount++;
            }
            assertEquals(2, goodsCount);
        }
        assertEquals(1, count);
    }
    
    @Test
    public void testJsonConstructorWithMinimalData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL1");
        
        Planets card = new Planets(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals(0, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
        }
        assertEquals(0, count);
    }
}