package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmugglersTest {

//    TODO: va cmbiato il costruttore di Smugglers per accettare i parametri corretti e il relativo assert finale
//    @Test
//    public void testConstructor() {
//        Smugglers card = new Smugglers(1, CardLevel.LEVEL_ONE, 3, 1, 2, 3);
//        assertEquals(1, card.getId());
//        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
//        assertEquals("Contrabbandieri", card.getName());
//        assertEquals("", card.getDescription());
//        assertEquals(3, card.getPower());
//        assertEquals(1, card.getLossPenalty().getAmount());
//        assertEquals(2, card.getWinPenalty().getAmount());
//        assertEquals(3, card.getWinReward().getAmount());
//    }



//    @Test
//    public void testJsonConstructorWithFullData() {
//        JsonObject json = new JsonObject();
//        json.addProperty("id", 2);
//        json.addProperty("level", "LEVEL1");
//        json.addProperty("power", 4);
//
//        JsonObject reward = new JsonObject();
//        reward.addProperty("credits", 3);
//        json.add("reward", reward);
//
//        JsonObject penalty = new JsonObject();
//        penalty.addProperty("cargoLoss", 2);
//        penalty.addProperty("days", 1);
//        json.add("penalty", penalty);
//
//        Smugglers card = new Smugglers(json);
//        assertEquals(2, card.getId());
//        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
//        assertEquals(4, card.getPower());
//        assertEquals(2, card.getLossPenalty().getAmount());
//        assertEquals(1, card.getWinPenalty().getAmount());
//        assertEquals(3, card.getWinReward().getAmount());
//    }
    
//    @Test
//    public void testJsonConstructorWithMinimalData() {
//        JsonObject json = new JsonObject();
//        json.addProperty("id", 3);
//        json.addProperty("level", "LEVEL1");
//
//        Smugglers card = new Smugglers(json);
//        assertEquals(3, card.getId());
//        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
//        // Should use default values
//        assertTrue(card.getPower() > 0);
//        assertEquals(1, card.getLossPenalty().getAmount());
//        assertEquals(0, card.getWinPenalty().getAmount());
//        assertEquals(3, card.getWinReward().getAmount());
//    }
}