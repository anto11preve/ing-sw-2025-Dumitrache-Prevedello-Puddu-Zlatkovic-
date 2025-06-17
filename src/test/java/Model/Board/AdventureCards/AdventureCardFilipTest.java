package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdventureCardFilipTest {

    // Test implementation of AdventureCardFilip for testing
    private static class TestCard extends AdventureCardFilip {
        public TestCard(int id, CardLevel level) {
            super(id, level);
        }
        
        public TestCard(JsonObject json) {
            super(json);
        }

        @Override
        public String getName() {
            return "Test Card";
        }

        @Override
        public String getDescription() {
            return "Test Description";
        }
    }

    @Test
    public void testConstructor() {
        TestCard card = new TestCard(1, CardLevel.LEARNER);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
        assertEquals("Test Card", card.getName());
        assertEquals("Test Description", card.getDescription());
    }

    @Test
    public void testJsonConstructor() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "TRIAL");
        
        TestCard card = new TestCard(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
    }
}