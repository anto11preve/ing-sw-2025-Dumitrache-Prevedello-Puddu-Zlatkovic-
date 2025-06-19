package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.CardLevel;
import Model.Enums.Side;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MeteorSwarmTest {

    @Test
    public void testConstructor() {
        List<Meteor> meteors = new ArrayList<>();
        meteors.add(new Meteor(true, Side.FRONT));
        meteors.add(new Meteor(false, Side.LEFT));
        
        MeteorSwarm card = new MeteorSwarm(1, CardLevel.LEVEL_ONE, meteors);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals("Pioggia di Meteoriti", card.getName());
        assertEquals("", card.getDescription());
        
        int count = 0;
        for (Meteor m : card) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testJsonConstructor() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL1");
        json.addProperty("incomingDirection", "FRONT");
        json.addProperty("largeMeteors", 2);
        json.addProperty("smallMeteors", 3);
        
        MeteorSwarm card = new MeteorSwarm(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        
        int largeCount = 0;
        int smallCount = 0;
        for (Meteor m : card) {
            if (m.isBig()) {
                largeCount++;
            } else {
                smallCount++;
            }
        }
        assertEquals(2, largeCount);
        assertEquals(3, smallCount);
    }
}