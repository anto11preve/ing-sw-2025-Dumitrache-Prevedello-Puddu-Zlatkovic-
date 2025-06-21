package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Penalties.Penalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Enums.Criteria;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CombatZone extends AdventureCardFilip implements Iterable<CombatZoneLine> {
    private final List<CombatZoneLine> lines;

    public CombatZone(int id, CardLevel level, List<CombatZoneLine> lines) {
        super(id, level);
        this.lines = lines;
    }

    @Override
    public String getName() {
        return "Zona di Guerra";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Iterator<CombatZoneLine> iterator() {
        List<CombatZoneLine> lines = new ArrayList<>(this.lines);

        return lines.iterator();
    }

    /**
     * Constructs a CombatZone from a JSON representation.
     * The JSON contains an array of lines, each with a criteria and a penalty.
     */
    public CombatZone(JsonObject json) {
        super(json);
        this.lines = new ArrayList<>();

        // Create a default combat zone line
        if (json.has("power")) {
            int power = json.get("power").getAsInt();
            Criteria criteria = Criteria.FIRE_POWER;
            
            Penalty penalty;
            if (json.has("penalty") && json.getAsJsonObject("penalty").has("days")) {
                int days = json.getAsJsonObject("penalty").get("days").getAsInt();
                penalty = new DaysPenalty(days);
            } else {
                penalty = new DaysPenalty(1); // Default
            }
            
            this.lines.add(new CombatZoneLine(criteria, penalty));
        }
        
        // If there's a lines array, parse it (but this is likely not in the actual JSON)
        if (json.has("lines")) {
            JsonArray array = json.getAsJsonArray("lines");
            for (JsonElement line : array) {
                JsonObject l = line.getAsJsonObject();

                // Parse the criteria of the line
                Criteria criteria = Criteria.valueOf(l.get("criteria").getAsString());

                // Parse the penalty
                JsonObject penaltyObj = l.getAsJsonObject("penalty");
                String penaltyType = penaltyObj.get("type").getAsString();
                int penaltyValue = penaltyObj.get("value").getAsInt();

                Penalty penalty = switch (penaltyType) {
                    case "DaysPenalty" -> new DaysPenalty(penaltyValue);
                    case "CrewPenalty" -> new CrewPenalty(penaltyValue);
                    case "GoodsPenalty" -> new GoodsPenalty(penaltyValue);
                    default -> throw new IllegalArgumentException("Unsupported penalty type in CombatZone: " + penaltyType);
                };

                // Add the line
                this.lines.add(new CombatZoneLine(criteria, penalty));
            }
        }
    }

}
