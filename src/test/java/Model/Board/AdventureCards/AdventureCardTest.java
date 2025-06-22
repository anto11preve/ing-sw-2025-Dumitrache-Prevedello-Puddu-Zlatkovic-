package Model.Board.AdventureCards;

import Model.AdventureCardOption;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Components.Planet;
import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Criteria;
import Model.Enums.Good;
import Model.Enums.Side;
import Model.Utils.CardLevelMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdventureCardTest {

    @Test
    public void testCardLevelMapper() {
        assertEquals(CardLevel.LEARNER, CardLevelMapper.mapJsonLevelToCardLevel("TRIAL"));
        assertEquals(CardLevel.LEVEL_ONE, CardLevelMapper.mapJsonLevelToCardLevel("LEVEL1"));
        assertEquals(CardLevel.LEVEL_TWO, CardLevelMapper.mapJsonLevelToCardLevel("LEVEL2"));
        assertEquals(CardLevel.LEVEL_THREE, CardLevelMapper.mapJsonLevelToCardLevel("LEVEL3"));
        assertThrows(IllegalArgumentException.class, () -> CardLevelMapper.mapJsonLevelToCardLevel("INVALID"));
    }

    @Test
    public void testOpenSpace() {
        // Test constructor
        OpenSpace card = new OpenSpace(1, CardLevel.LEARNER);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEARNER, card.getLevel());
        assertEquals("Spazio Aperto", card.getName());
        assertEquals("", card.getDescription());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "TRIAL");
        OpenSpace jsonCard = new OpenSpace(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEARNER, jsonCard.getLevel());
    }

    @Test
    public void testMeteorSwarm() {
        // Test constructor
        List<Meteor> meteors = new ArrayList<>();
        meteors.add(new Meteor(true, Side.FRONT));
        meteors.add(new Meteor(false, Side.LEFT));
        MeteorSwarm card = new MeteorSwarm(1, CardLevel.LEVEL_ONE, meteors);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals("Pioggia di Meteoriti", card.getName());
        assertEquals("", card.getDescription());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL1");
        json.addProperty("incomingDirection", "FRONT");
        json.addProperty("largeMeteors", 2);
        json.addProperty("smallMeteors", 3);
        MeteorSwarm jsonCard = new MeteorSwarm(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_ONE, jsonCard.getLevel());
        int count = 0;
        for (Meteor m : jsonCard) {
            count++;
        }
        assertEquals(5, count); // 2 large + 3 small
    }

    @Test
    public void testStardust() {
        // Test constructor
        Stardust card = new Stardust(1, CardLevel.LEVEL_TWO);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        assertEquals("Polvere Stellare", card.getName());
        assertEquals("", card.getDescription());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL2");
        Stardust jsonCard = new Stardust(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_TWO, jsonCard.getLevel());
    }

    @Test
    public void testAbandonedShip() {
        // Test constructor
        AbandonedShip card = new AbandonedShip(1, CardLevel.LEVEL_THREE, 2, 3, 1);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals("Nave Abbandonata", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(2, card.getCrew());
        assertEquals(3, card.getLandingReward().getAmount());
        assertEquals(1, card.getLandingPenalty().getAmount());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL3");
        JsonObject reward = new JsonObject();
        reward.addProperty("crew", 3);
        json.add("reward", reward);
        AbandonedShip jsonCard = new AbandonedShip(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_THREE, jsonCard.getLevel());
        assertEquals(3, jsonCard.getCrew());
    }

    @Test
    public void testAbandonedStation() {
        // Test constructor
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

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL1");
        JsonObject reward = new JsonObject();
        reward.addProperty("cargo", 3);
        json.add("reward", reward);
        AbandonedStation jsonCard = new AbandonedStation(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_ONE, jsonCard.getLevel());
    }

    @Test
    public void testCombatZone() {
        // Test constructor
        List<CombatZoneLine> lines = new ArrayList<>();
        lines.add(new CombatZoneLine(Criteria.FIRE_POWER, new DaysPenalty(2)));
        CombatZone card = new CombatZone(1, CardLevel.LEVEL_TWO, lines);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        assertEquals("Zona di Guerra", card.getName());
        assertEquals("", card.getDescription());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL2");
        json.addProperty("power", 4);
        JsonObject penalty = new JsonObject();
        penalty.addProperty("days", 2);
        json.add("penalty", penalty);
        CombatZone jsonCard = new CombatZone(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_TWO, jsonCard.getLevel());
        int count = 0;
        for (CombatZoneLine line : jsonCard) {
            count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void testEpidemic() {
        // Test constructor
        Epidemic card = new Epidemic(1, CardLevel.LEVEL_THREE);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals("Epidemia", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(0, card.getCrewLost());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL3");
        JsonObject penalty = new JsonObject();
        penalty.addProperty("crewLoss", 3);
        json.add("penalty", penalty);
        Epidemic jsonCard = new Epidemic(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_THREE, jsonCard.getLevel());
        assertEquals(3, jsonCard.getCrewLost());
    }

    @Test
    public void testPlanets() {
        // Test constructor
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

        // Test JSON constructor
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
        Planets jsonCard = new Planets(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_ONE, jsonCard.getLevel());
        int count = 0;
        for (Planet p : jsonCard) {
            count++;
        }
        assertEquals(1, count);
    }

    @Test
    public void testPirates() {
        // Test constructor
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

        // Test JSON constructor
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
        Pirates jsonCard = new Pirates(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_TWO, jsonCard.getLevel());
        assertEquals(5, jsonCard.getPower());
        assertEquals(3, jsonCard.getWinPenalty().getAmount());
        assertEquals(4, jsonCard.getWinReward().getAmount());
    }

    @Test
    public void testSlavers() {
        // Test constructor
        Slavers card = new Slavers(1, CardLevel.LEVEL_THREE, 5, 2, 1, 4);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_THREE, card.getLevel());
        assertEquals("Schiavisti", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(5, card.getPower());
        assertEquals(2, card.getLossPenalty().getAmount());
        assertEquals(1, card.getWinPenalty().getAmount());
        assertEquals(4, card.getWinReward().getAmount());

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL3");
        json.addProperty("power", 6);
        JsonObject reward = new JsonObject();
        reward.addProperty("credits", 5);
        json.add("reward", reward);
        JsonObject penalty = new JsonObject();
        penalty.addProperty("crewLoss", 3);
        json.add("penalty", penalty);
        Slavers jsonCard = new Slavers(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_THREE, jsonCard.getLevel());
        assertEquals(6, jsonCard.getPower());
        assertEquals(3, jsonCard.getLossPenalty().getAmount());
        assertEquals(5, jsonCard.getWinReward().getAmount());
    }

    @Test
    public void testSmugglers() {
        // Test constructor
        List<Good> goods = Arrays.asList(Good.RED, Good.BLUE, Good.GREEN);
        Smugglers card = new Smugglers(1, CardLevel.LEVEL_ONE, 3, 1, 2, goods);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals("Contrabbandieri", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(3, card.getPower());
        assertEquals(1, card.getLossPenalty().getAmount());
        assertEquals(2, card.getWinPenalty().getAmount());
        int count = 0;
        for (Good g : card.getWinReward()) {
            count++;
        }
        assertEquals(3, count);

        // Test JSON constructor
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL1");
        json.addProperty("power", 4);
        JsonObject reward = new JsonObject();
        reward.addProperty("credits", 3);
        json.add("reward", reward);
        JsonObject penalty = new JsonObject();
        penalty.addProperty("cargoLoss", 2);
        json.add("penalty", penalty);
        Smugglers jsonCard = new Smugglers(json);
        assertEquals(2, jsonCard.getId());
        assertEquals(CardLevel.LEVEL_ONE, jsonCard.getLevel());
        assertEquals(4, jsonCard.getPower());
        assertEquals(2, jsonCard.getLossPenalty().getAmount());
        int jsonCount = 0;
        for (Good g : jsonCard.getWinReward()) {
            jsonCount++;
        }
        assertEquals(3, jsonCount);
    }

    @Test
    public void testBaseAdventureCard() throws Exception {
        AdventureCard card = new AdventureCard();
        
        // Test with null values
        assertNull(card.getId());
        assertNull(card.getName());
        assertNull(card.getType());
        assertNull(card.getLevel());
        assertNull(card.getDescription());
        assertNull(card.getEffect());
        assertNull(card.getConditions());
        assertNull(card.getRewards());
        assertNull(card.getPenalties());
        assertNull(card.getImagePath());
        assertNull(card.getAnimationPath());
        assertNull(card.getSoundEffect());
        assertFalse(card.isRequiresPlayerChoice());
        assertNull(card.getOptions());
        
        // Test with populated values using reflection
        setField(card, "id", "test-123");
        setField(card, "name", "Test Card");
        setField(card, "type", "TEST");
        setField(card, "level", "LEVEL1");
        setField(card, "description", "Test description");
        setField(card, "effect", "Test effect");
        setField(card, "conditions", Arrays.asList("cond1", "cond2"));
        setField(card, "rewards", Arrays.asList("reward1"));
        setField(card, "penalties", Arrays.asList("penalty1", "penalty2"));
        setField(card, "imagePath", "/test.png");
        setField(card, "animationPath", "/test.gif");
        setField(card, "soundEffect", "/test.wav");
        setField(card, "requiresPlayerChoice", true);
        
        AdventureCardOption option = new AdventureCardOption();
        setOptionField(option, "label", "Test Option");
        setOptionField(option, "conditions", Arrays.asList("opt_cond"));
        setOptionField(option, "rewards", Arrays.asList("opt_reward"));
        setOptionField(option, "penalties", Arrays.asList("opt_penalty"));
        setField(card, "options", Arrays.asList(option));
        
        // Test all getters
        assertEquals("test-123", card.getId());
        assertEquals("Test Card", card.getName());
        assertEquals("TEST", card.getType());
        assertEquals("LEVEL1", card.getLevel());
        assertEquals("Test description", card.getDescription());
        assertEquals("Test effect", card.getEffect());
        assertEquals(2, card.getConditions().size());
        assertEquals("cond1", card.getConditions().get(0));
        assertEquals(1, card.getRewards().size());
        assertEquals("reward1", card.getRewards().get(0));
        assertEquals(2, card.getPenalties().size());
        assertEquals("penalty1", card.getPenalties().get(0));
        assertEquals("/test.png", card.getImagePath());
        assertEquals("/test.gif", card.getAnimationPath());
        assertEquals("/test.wav", card.getSoundEffect());
        assertTrue(card.isRequiresPlayerChoice());
        assertEquals(1, card.getOptions().size());
        assertEquals("Test Option", card.getOptions().get(0).getLabel());
        assertEquals("opt_cond", card.getOptions().get(0).getConditions().get(0));
        assertEquals("opt_reward", card.getOptions().get(0).getRewards().get(0));
        assertEquals("opt_penalty", card.getOptions().get(0).getPenalties().get(0));
    }
    
    private void setField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
    
    private void setOptionField(AdventureCardOption option, String fieldName, Object value) throws Exception {
        Field field = AdventureCardOption.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(option, value);
    }
}