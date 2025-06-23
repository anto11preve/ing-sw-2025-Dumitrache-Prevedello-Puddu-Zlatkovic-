package Model.Board.AdventureCards;

import Controller.CardResolverVisitor;
import Controller.Controller;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Good;
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
                new GoodsPenalty(json.has("penalty") &&
                        json.getAsJsonObject("penalty").has("cargoLoss")
                        ? json.getAsJsonObject("penalty").get("cargoLoss").getAsInt()
                        : 1),
                json.has("penalty") &&
                        json.getAsJsonObject("penalty").has("days")
                        ? json.getAsJsonObject("penalty").get("days").getAsInt()
                        : 0,
                // we still need to pass a dummy Goods here
                new Goods(new ArrayList<>())
        );

        // 2) Now populate rewardGoods from JSON
        //rewardGoods.clear();
        rewardGoods = new ArrayList<>();
        if (json.has("rewardGoods")) {
            for (JsonElement e : json.getAsJsonArray("rewardGoods")) {
                rewardGoods.add(
                        Good.valueOf(e.getAsString().toUpperCase())
                );
            }
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
                gp.getAmount(),                    // toString shows how many goods
                gp.getClass().getSimpleName()
        );

        // 4) win‐penalty in days (wrapped by DaysPenalty)
        //    getWinPenalty() returns a DaysPenalty instance
        System.out.printf(
                "Win Penalty:           %s (type: %s)%n",
                getWinPenalty().getAmount(),       // toString prints “N days”
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

    public String[] visualizeString() {
        List<String> lines = new ArrayList<>();

        // 1) common header da super.visualize()
        lines.add("==========================");
        lines.add("ID: " + this.getId());
        lines.add("Nome: " + this.getName());
        lines.add("Livello: " + this.getLevel());

        // 2) show the encounter power
        lines.add("Power:                 " + getPower());

        // 3) loss penalty: number of goods and its class
        GoodsPenalty gp = getLossPenalty();
        lines.add(String.format(
                "Loss Penalty:          %s (type: %s)",
                gp.getAmount(),                    // toString shows how many goods
                gp.getClass().getSimpleName()
        ));

        // 4) win‐penalty in days (wrapped by DaysPenalty)
        lines.add(String.format(
                "Win Penalty:           %s (type: %s)",
                getWinPenalty().getAmount(),       // toString prints "N days"
                getWinPenalty().getClass().getSimpleName()
        ));

        // 5) win‐reward list and its type
        StringBuilder rewardLine = new StringBuilder("Reward Goods:          ");
        if (rewardGoods.isEmpty()) {
            rewardLine.append("(none)");
        } else {
            for (Good g : rewardGoods) {
                rewardLine.append(g).append(" ");
            }
        }
        lines.add(rewardLine.toString());

        lines.add(String.format(
                "Reward Type:           %s",
                getWinReward().getClass().getSimpleName()
        ));

        return lines.toArray(new String[0]);
    }

    @Override
    public void accept(CardResolverVisitor cardResolverVisitor, Controller controller) {
        cardResolverVisitor.visit(this, controller);
    }
}
