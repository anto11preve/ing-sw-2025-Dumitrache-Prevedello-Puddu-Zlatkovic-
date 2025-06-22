package Model.Board.AdventureCards;

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

import java.util.ArrayList;
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
        //assertEquals(0, card.getCrewLost());

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
        //assertEquals(3, jsonCard.getCrewLost());
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


//    TODO: va cmbiato il costruttore di Smugglers per accettare i parametri corretti e il relativo assert finale
//
//    @Test
//    public void testSmugglers() {
//        // Test constructor
//        Smugglers card = new Smugglers(1, CardLevel.LEVEL_ONE, 3, 1, 2, 3);
//        assertEquals(1, card.getId());
//        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
//        assertEquals("Contrabbandieri", card.getName());
//        assertEquals("", card.getDescription());
//        assertEquals(3, card.getPower());
//        assertEquals(1, card.getLossPenalty().getAmount());
//        assertEquals(2, card.getWinPenalty().getAmount());
//        assertEquals(3, card.getWinReward().getAmount());
//
//        // Test JSON constructor
//        JsonObject json = new JsonObject();
//        json.addProperty("id", 2);
//        json.addProperty("level", "LEVEL1");
//        json.addProperty("power", 4);
//        JsonObject reward = new JsonObject();
//        reward.addProperty("credits", 3);
//        json.add("reward", reward);
//        JsonObject penalty = new JsonObject();
//        penalty.addProperty("cargoLoss", 2);
//        json.add("penalty", penalty);
//        Smugglers jsonCard = new Smugglers(json);
//        assertEquals(2, jsonCard.getId());
//        assertEquals(CardLevel.LEVEL_ONE, jsonCard.getLevel());
//        assertEquals(4, jsonCard.getPower());
//        assertEquals(2, jsonCard.getLossPenalty().getAmount());
//        assertEquals(3, jsonCard.getWinReward().getAmount());
//    }
}