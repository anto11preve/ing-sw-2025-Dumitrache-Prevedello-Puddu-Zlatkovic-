package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import Model.Enums.Good;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Smugglers class which represents smuggler encounters.
 * Tests constructor, JSON parsing, and visualization functionality.
 */
public class SmugglersTest {

    /**
     * Tests JSON constructor with actual card ID 5 from adventure_cards.json.
     */
    @Test
    public void testJsonConstructorCard5() {
        JsonObject json = new JsonObject();
        json.addProperty("id", "5");
        json.addProperty("type", "Smugglers");
        json.addProperty("level", "LEARNER");
        json.addProperty("enemyStrength", 4);
        json.addProperty("stealGoodsOnLoss", 2);

        JsonArray rewardGoods = new JsonArray();
        rewardGoods.add(new JsonPrimitive("YELLOW"));
        rewardGoods.add(new JsonPrimitive("GREEN"));
        rewardGoods.add(new JsonPrimitive("BLUE"));
        json.add("rewardGoods", rewardGoods);

        json.addProperty("dayCost", 1);
        json.addProperty("imagePath", "src/main/resources/pics/cards/5.jpg");

        Smugglers card = new Smugglers(json);
        assertEquals(5, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
    }

    /**
     * Tests JSON constructor with actual card ID 40 from adventure_cards.json.
     */
    @Test
    public void testJsonConstructorCard40() {
        JsonObject json = new JsonObject();
        json.addProperty("id", "40");
        json.addProperty("type", "Smugglers");
        json.addProperty("level", "LEVEL_TWO");
        json.addProperty("enemyStrength", 8);
        json.addProperty("stealGoodsOnLoss", 3);

        JsonArray rewardGoods = new JsonArray();
        rewardGoods.add(new JsonPrimitive("RED"));
        rewardGoods.add(new JsonPrimitive("YELLOW"));
        rewardGoods.add(new JsonPrimitive("YELLOW"));
        json.add("rewardGoods", rewardGoods);

        json.addProperty("dayCost", 1);
        json.addProperty("imagePath", "src/main/resources/pics/cards/40.jpg");

        Smugglers card = new Smugglers(json);
        assertEquals(40, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
    }

    /**
     * Tests JSON constructor with actual card ID 45 from adventure_cards.json.
     */
    @Test
    public void testJsonConstructorCard45() {
        JsonObject json = new JsonObject();
        json.addProperty("id", "45");
        json.addProperty("type", "Smugglers");
        json.addProperty("level", "LEVEL_ONE");
        json.addProperty("enemyStrength", 4);
        json.addProperty("stealGoodsOnLoss", 2);

        JsonArray rewardGoods = new JsonArray();
        rewardGoods.add(new JsonPrimitive("YELLOW"));
        rewardGoods.add(new JsonPrimitive("GREEN"));
        rewardGoods.add(new JsonPrimitive("BLUE"));
        json.add("rewardGoods", rewardGoods);

        json.addProperty("dayCost", 1);
        json.addProperty("imagePath", "src/main/resources/pics/cards/5.jpg");

        Smugglers card = new Smugglers(json);
        assertEquals(45, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
    }

    /**
     * Tests the basic constructor.
     */
    @Test
    public void testConstructor() {
        List<Good> goods = Arrays.asList(Good.RED, Good.BLUE, Good.GREEN);
        Smugglers card = new Smugglers(5, CardLevel.LEARNER, 4, 2, 1, goods);
        assertEquals(5, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
        assertEquals("Contrabbandieri", card.getName());
        assertEquals("", card.getDescription());
    }

    /**
     * Tests the visualize method.
     */
    @Test
    public void testVisualize() {
        List<Good> goods = Arrays.asList(Good.YELLOW, Good.GREEN, Good.BLUE);
        Smugglers card = new Smugglers(5, CardLevel.LEARNER, 4, 2, 1, goods);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        card.visualize();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.length() > 0);
    }
}