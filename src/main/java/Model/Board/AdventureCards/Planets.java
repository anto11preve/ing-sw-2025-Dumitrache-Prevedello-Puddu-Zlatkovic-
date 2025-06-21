package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.Planet;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Enums.Good;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Planets extends AdventureCardFilip implements Iterable<Planet>{
    private final List<Planet> planetList;
    private final DaysPenalty landingPenalty;

    public Planets(int id, CardLevel level, int days, List<Planet> planetList) {
        super(id, level);
        this.planetList = planetList;
        this.landingPenalty = new DaysPenalty(days);
    }

    public DaysPenalty getLandingPenalty() {
        return landingPenalty;
    }

    @Override
    public String getName() {
        return "Pianeti";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Iterator<Planet> iterator() {
        return this.planetList.iterator();
    }

    public Planets(JsonObject json) {
        super(json);
        this.planetList = new ArrayList<>();

        if (json.has("planets")) {
            JsonArray array = json.getAsJsonArray("planets");
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();

                List<Good> goods = new ArrayList<>();
                if (obj.has("goods")) {
                    for (JsonElement g : obj.getAsJsonArray("goods")) {
                        goods.add(Good.valueOf(g.getAsString()));
                    }
                }

                String name = obj.has("name") ? obj.get("name").getAsString() : "Unknown Planet";
                this.planetList.add(new Planet(name, goods));
            }
        }

        // Default landing penalty
        int days = 0;
        if (json.has("landingPenalty") && json.getAsJsonObject("landingPenalty").has("value")) {
            days = json.getAsJsonObject("landingPenalty").get("value").getAsInt();
        }
        this.landingPenalty = new DaysPenalty(days);

    }

}
