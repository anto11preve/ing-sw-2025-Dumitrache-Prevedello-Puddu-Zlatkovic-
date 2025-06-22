package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Penalties.RegularPenalty;
import Model.Enums.CardLevel;
import Model.Enums.Criteria;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CombatZoneTest {

    @Test
    public void testConstructor() {
        List<CombatZoneLine> lines = new ArrayList<>();
        lines.add(new CombatZoneLine(Criteria.FIRE_POWER, new DaysPenalty(2)));

        CombatZone card = new CombatZone(1, CardLevel.LEVEL_TWO, lines);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());
        assertEquals("Zona di Guerra", card.getName());
        assertEquals("", card.getDescription());

        int count = 0;
        for (CombatZoneLine line : card) {
            count++;
            assertEquals(Criteria.FIRE_POWER, line.getOrderingCriteria());
            assertEquals(2, ((RegularPenalty)line.getPenalty()).getAmount());
        }
        assertEquals(1, count);
    }

    @Test
    public void testJsonConstructorWithPower() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL2");
        json.addProperty("power", 4);

        JsonObject penalty = new JsonObject();
        penalty.addProperty("days", 2);
        json.add("penalty", penalty);

        CombatZone card = new CombatZone(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());

        int count = 0;
        for (CombatZoneLine line : card) {
            count++;
            assertEquals(Criteria.FIRE_POWER, line.getOrderingCriteria());
            assertEquals(2, ((RegularPenalty)line.getPenalty()).getAmount());
        }
        assertEquals(1, count);
    }

    @Test
    public void testJsonConstructorWithLines() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL2");

        JsonArray lines = new JsonArray();
        JsonObject line = new JsonObject();
        line.addProperty("criteria", "FIRE_POWER");

        JsonObject penaltyObj = new JsonObject();
        penaltyObj.addProperty("type", "DaysPenalty");
        penaltyObj.addProperty("value", 3);
        line.add("penalty", penaltyObj);

        lines.add(line);
        json.add("lines", lines);

        CombatZone card = new CombatZone(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_TWO, card.getLevel());


        int count = 0;
        for (CombatZoneLine l : card) {
            count++;
            assertEquals(Criteria.FIRE_POWER, l.getOrderingCriteria());
            assertEquals(3, ((RegularPenalty)l.getPenalty()).getAmount());
        }
        assertEquals(1, count);
    }
}