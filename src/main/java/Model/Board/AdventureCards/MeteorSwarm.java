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
    private String imagePath;

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

        for (JsonElement elem : json.getAsJsonArray("meteors")) {
            JsonObject m = elem.getAsJsonObject();
            boolean isLarge = m.get("large").getAsBoolean();
            Side dir       = Side.valueOf(m.get("direction").getAsString());
            meteors.add(new Meteor(isLarge, dir));
        }
        this.imagePath = json.get("imagePath").getAsString();
    }

    public String getImagePath() {
        return imagePath;
    }
}
