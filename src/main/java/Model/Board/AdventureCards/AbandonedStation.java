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
        super(json);

        if (json.has("crewRequired")) {
            // Default values
            this.crew = json.get("crewRequired").getAsInt();
        }else{
            throw new IllegalArgumentException("JSON does not contain 'crewRequired' at id " + getId());
        }

        if (json.has("days")) {
            int days = json.get("days").getAsInt();
            this.landingPenalty = new DaysPenalty(days);
        }else{
            throw new IllegalArgumentException("JSON does not contain 'days' at id " + getId());
        }

        // Parse cargo from reward
        List<Good> parsedGoods = new ArrayList<>();

        if (json.has("reward") && json.getAsJsonObject("reward").has("goods")) {
            JsonArray goodsArr = json.getAsJsonObject("reward").getAsJsonArray("goods");
            // Add default goods based on cargo count
            for (JsonElement goodEl : goodsArr) {
                parsedGoods.add(Good.valueOf(goodEl.getAsString().toUpperCase()));
            }
        }else{
            throw new IllegalArgumentException("JSON does not contain 'reward.goods' at id " + getId());
        }


        this.landingReward = new Goods(parsedGoods);
    }

    @Override
    public void visualize() {
        // 1) common header: ID, name, level, description, imagePath
        super.visualize();

        // 2) crew required
        System.out.println("Crew Required:       " + crew);

        // 3) landing delay penalty and its type
        System.out.printf(
                "Landing Delay:       %s (type: %s)%n",
                landingPenalty,
                landingPenalty.getClass().getSimpleName()
        );

        // 4) reward goods count & list
        int goodsCount = 0;
        String goodsList = "";
        for (Good g : landingReward) {
            goodsCount++;
            goodsList += g + " ";
        }
        if (!goodsList.isEmpty()) {
            goodsList = goodsList.trim();
        }
        System.out.println("Reward Goods:        " + goodsCount);
        System.out.println("  Goods List:        " + (goodsList.isEmpty() ? "(none)" : goodsList));

        // 5) reward type
        System.out.println(
                "Reward Type:         " + landingReward.getClass().getSimpleName()
        );
    }


}
