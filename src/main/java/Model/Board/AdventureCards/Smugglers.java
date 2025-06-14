package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Good;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Smugglers extends Enemy<GoodsPenalty, Goods> {

    public Smugglers(int id, CardLevel level, int power, int lostGoods, int days, List<Good> goods) {
        super(id, level, power, new GoodsPenalty(lostGoods), days, new Goods(goods));
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
                json.get("id").getAsInt(),
                CardLevel.valueOf(json.get("level").getAsString()),
                json.getAsJsonObject("enemy").get("firepower").getAsInt(),
                new GoodsPenalty(json.getAsJsonObject("enemy").getAsJsonObject("penalty").get("value").getAsInt()),
                json.getAsJsonObject("enemy").get("days").getAsInt(),
                parseGoodsList(json.getAsJsonObject("enemy").getAsJsonObject("reward").getAsJsonArray("value"))
        );
    }

    private static Goods parseGoodsList(JsonArray goodsArray) {
        List<Good> goodsList = new ArrayList<>();
        for (JsonElement e : goodsArray) {
            goodsList.add(Good.valueOf(e.getAsString()));
        }
        return new Goods(goodsList);
    }



}
