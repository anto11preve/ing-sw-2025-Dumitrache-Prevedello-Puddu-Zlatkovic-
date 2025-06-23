package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Good;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Smugglers extends Enemy<GoodsPenalty, Goods> {
    private List<Good> rewardGoods;

    public Smugglers(int id, CardLevel level, int power, int lostGoods, int days, List<Good> rewardList) {
        super(id, level, power, new GoodsPenalty(lostGoods), days, new Goods(rewardList));
    }

    @Override
    public final String getName() {
        return "Contrabbandieri";
    }

    @Override
    public final String getDescription() {
        return "";
    }

    public Smugglers(JsonObject json) {
        super(
                json,
                new GoodsPenalty(json),
                // we still need to pass a dummy Goods here
                new Goods(new ArrayList<>())
        );

        // 2) Now populate rewardGoods from JSON
        //rewardGoods.clear();
        rewardGoods = new ArrayList<>();
        if (json.has("rewardGoods")) {

            if (json.has("rewardGoods")) {
                JsonArray goodsArray = json.getAsJsonArray("rewardGoods");

                for (JsonElement e : goodsArray) {
                    rewardGoods.add(
                            Good.valueOf(e.getAsString().toUpperCase())
                    );
                }
            }else{
                throw new IllegalArgumentException("Missing 'rewardGoods' in JSON at id: " + getId());
            }
        }else{
            throw new IllegalArgumentException("Missing 'rewardGoods' in JSON at id: " + getId());
        }
    }

    @Override
    public void visualize() {
        // 1) common header
        super.visualize();

        // 2) show the encounter power
        System.out.println("Power:                 " + getPower());

        // 3) loss penalty: number of goods and its class
        GoodsPenalty gp = getLossPenalty();
        System.out.printf(
                "Loss Penalty:          %s (type: %s)%n",
                gp,                    // toString shows how many goods
                gp.getClass().getSimpleName()
        );

        // 4) win‐penalty in days (wrapped by DaysPenalty)
        //    getWinPenalty() returns a DaysPenalty instance
        System.out.printf(
                "Win Penalty:           %s (type: %s)%n",
                getWinPenalty(),       // toString prints “N days”
                getWinPenalty().getClass().getSimpleName()
        );

        // 5) win‐reward list and its type
        System.out.print("Reward Goods:          ");
        if (rewardGoods.isEmpty()) {
            System.out.println("(none)");
        } else {
            for (Good g : rewardGoods) {
                System.out.print(g + " ");
            }
            System.out.println();
        }
        System.out.printf(
                "Reward Type:           %s%n",
                getWinReward().getClass().getSimpleName()
        );
    }

}
