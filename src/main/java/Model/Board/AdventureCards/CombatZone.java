package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Penalties.*;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Enums.Criteria;
import Model.Enums.Side;
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

        // Parse every entry in the "lines" array
        if (json.has("lines")) {
            JsonArray array = json.getAsJsonArray("lines");
            for (JsonElement elem : array) {
                JsonObject entry      = elem.getAsJsonObject();
                // 1) Read the criteria
                Criteria criteria     = Criteria.valueOf(
                        entry.get("criteria").getAsString().toUpperCase()
                );
                // 2) Read the penalty object
                JsonObject penObj     = entry.getAsJsonObject("penalty");
                Penalty   penalty;

                // 3A) If they gave a "shots" array → ProjectilePenalty
                if (penObj.has("shots")) {
                    List<CannonShot> shots = new ArrayList<>();
                    for (JsonElement shotElem : penObj.getAsJsonArray("shots")) {
                        JsonObject s    = shotElem.getAsJsonObject();
                        boolean   large = s.get("isLarge").getAsBoolean();
                        Side dir   = Side.valueOf(
                                s.get("direction").getAsString().toUpperCase()
                        );
                        shots.add(new CannonShot(large, dir));
                    }
                    penalty = new CannonShotPenalty(shots);

                    // 3B) Otherwise, look for a "type" field and switch on it
                } else if (penObj.has("type")) {
                    String type = penObj.get("type").getAsString();
                    switch (type) {
                        case "DaysPenalty"   -> penalty = new DaysPenalty(
                                penObj.get("value").getAsInt()
                        );
                        case "CrewPenalty"   -> penalty = new CrewPenalty(
                                penObj.get("value").getAsInt()
                        );
                        case "GoodsPenalty"  -> penalty = new GoodsPenalty(
                                penObj.get("value").getAsInt()
                        );
                        default -> throw new IllegalArgumentException(
                                "Unsupported penalty type: " + type
                        );
                    }

                    // 3C) Fallback if no "shots" or "type" (shouldn’t happen if your JSON is correct)
                } else {
                    penalty = new DaysPenalty(1);
                }

                // 4) Add the line
                this.lines.add(new CombatZoneLine(criteria, penalty));
            }
        }
    }

    @Override
    public void visualize() {
        // 1) Print the generic card info
        super.visualize();

        // 2) Print each combat line
        System.out.println("Combat Lines:");
        for (int i = 0; i < lines.size(); i++) {
            CombatZoneLine line = lines.get(i);
            Penalty         pen  = line.getPenalty();
            Criteria        crit = line.getOrderingCriteria();

            System.out.printf(" [%d] Criteria: %s%n", i + 1, crit);

            // Simple penalties: rely on toString()
            if (pen instanceof DaysPenalty ||
                    pen instanceof CrewPenalty ||
                    pen instanceof GoodsPenalty) {

                System.out.printf("      Penalty: %s%n", pen);

            }
            // CannonShotPenalty: iterable of CannonShot
            else if (pen instanceof CannonShotPenalty) {
                CannonShotPenalty csp = (CannonShotPenalty) pen;
                int count = 0;
                System.out.printf("      Shots:%n");
                for (CannonShot shot : csp) {
                    count++;
                    System.out.printf(
                            "        #%d → large=%s, dir=%s%n",
                            count,
                            shot.isBig(),
                            shot.getSide()
                    );
                }
                if (count == 0) {
                    System.out.println("        (no shots)");
                }
            }
            // Fallback
            else {
                System.out.printf("      Unknown penalty type: %s%n", pen);
            }
        }
    }
}
