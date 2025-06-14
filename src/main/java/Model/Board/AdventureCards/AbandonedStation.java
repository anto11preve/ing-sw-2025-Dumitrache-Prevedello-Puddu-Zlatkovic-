package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Good;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AbandonedStation extends AdventureCardFilip {
    private final int crew;
    private final Goods landingReward;
    private final DaysPenalty landingPenalty;

    public AbandonedStation(int id, CardLevel level, int crew, List<Good> goods, int days) {
        super(id, level);
        this.crew = crew;
        this.landingPenalty = new DaysPenalty(days);
        this.landingReward = new Goods(goods);
    }

    public int getCrew() {
        return crew;
    }

    public DaysPenalty getLandingPenalty() {
        return landingPenalty;
    }

    public Goods getLandingReward(){
        return landingReward;
    }

    @Override
    public String getName() {
        return "Stazione Abbandonata";
    }

    @Override
    public String getDescription() {
        return "";
    }

    public AbandonedStation(JsonObject json) {
        super(json.get("id").getAsInt(), CardLevel.valueOf(json.get("level").getAsString()));

        this.crew = json.get("crewRequired").getAsInt();
        int days = json.get("days").getAsInt();
        this.landingPenalty = new DaysPenalty(days);

        // Parse goods from reward
        JsonArray goodsArray = json.getAsJsonObject("reward").getAsJsonArray("value");
        List<Good> parsedGoods = new ArrayList<>();
        for (JsonElement g : goodsArray) {
            parsedGoods.add(Good.valueOf(g.getAsString()));
        }
        this.landingReward = new Goods(parsedGoods);
    }

}
