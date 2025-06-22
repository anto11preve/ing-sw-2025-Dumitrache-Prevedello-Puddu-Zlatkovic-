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
                }else{
                    throw new IllegalArgumentException("Planet JSON object does not contain 'goods' array at id " + getId());
                }

                if(!obj.has("name")) {
                    throw new IllegalArgumentException("Planet JSON object does not contain 'name' field at id " + getId());
                }
                String name = obj.has("name") ? obj.get("name").getAsString() : "Unknown Planet";
                this.planetList.add(new Planet(name, goods));
            }
        }else{
            throw new IllegalArgumentException("JSON does not contain 'planets' array at id " + getId());
        }

        // Default landing penalty
        int days = 0;
        if (json.has("landingPenalty") && json.getAsJsonObject("landingPenalty").has("value")) {
            days = json.getAsJsonObject("landingPenalty").get("value").getAsInt();
        }else{
            throw new IllegalArgumentException("JSON does not contain 'landingPenalty' object with 'value' field at id " + getId());
        }
        this.landingPenalty = new DaysPenalty(days);

    }

    @Override
    public void visualize() {
        super.visualize();

        System.out.println("Planets:");
        if (planetList.isEmpty()) {
            System.out.println("  (no planets)");
        } else {
            int idx = 0;
            for (Planet p : planetList) {
                idx++;
                System.out.printf("  #%d → %s%n", idx, p.getName());

                // --- Reflection to access private List<Good> goods ---
                List<Good> goods;
                try {
                    java.lang.reflect.Field f = p.getClass().getDeclaredField("goods");
                    f.setAccessible(true);
                    //noinspection unchecked
                    goods = (List<Good>) f.get(p);
                } catch (Exception e) {
                    // fallback if reflection fails
                    goods = List.of();
                }

                // Build the goods line with plain String concatenation
                String goodsLine = "";
                for (Good g : goods) {
                    goodsLine += g + " ";
                }
                goodsLine = goodsLine.trim();  // remove trailing space

                if (goods.isEmpty()) {
                    System.out.println("      Goods: (no goods)");
                } else {
                    System.out.println("      Goods: " + goodsLine);
                }
            }
        }

        System.out.println("Landing Penalty: " + landingPenalty);
    }





}
