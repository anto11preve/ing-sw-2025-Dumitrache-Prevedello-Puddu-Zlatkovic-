package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Enums.Side;
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
        super(json.get("id").getAsInt(), CardLevel.valueOf(json.get("level").getAsString()));

        Side incomingDirection = Side.valueOf(json.get("incomingDirection").getAsString());
        int large = json.get("largeMeteors").getAsInt();
        int small = json.get("smallMeteors").getAsInt();

        // Construct meteor list from parsed data
        this.meteors = new ArrayList<>();
        for (int i = 0; i < large; i++) {
            meteors.add(new Meteor(true, incomingDirection)); // true = large meteor
        }
        for (int i = 0; i < small; i++) {
            meteors.add(new Meteor(false, incomingDirection)); // false = small meteor
        }
    }
}
