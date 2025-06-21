package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Enums.Side;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MeteorSwarm extends AdventureCardFilip implements Iterable<Meteor> {
    private final List<Meteor> meteors;

    public MeteorSwarm(int id, CardLevel level, List<Meteor> meteors) {
        super(id, level);

        this.meteors = meteors;
    }

    @Override
    public String getName() {
        return "Pioggia di Meteoriti";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Iterator<Meteor> iterator() {
        return meteors.iterator();
    }

    /**
     * Constructs a MeteorSwarm from a JSON object.
     * Parses the number of large and small meteors and the direction.
     */
    public MeteorSwarm(JsonObject json) {
        super(json);
        this.meteors = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("meteors")) {
            JsonObject m = e.getAsJsonObject();
            boolean large = m.get("large").getAsBoolean();
            Side dir     = Side.valueOf(m.get("direction").getAsString().toUpperCase());
            this.meteors.add(new Meteor(large, dir));
        }
    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("Total Meteors:  " + meteors.size());
        System.out.println("Details:");
        for (int i = 0; i < meteors.size(); i++) {
            Meteor m = meteors.get(i);
            System.out.printf(
                    "  #%d → large=%s, dir=%s%n",
                    i + 1,
                    m.isBig(),
                    m.getSide()
            );
        }
    }

}
