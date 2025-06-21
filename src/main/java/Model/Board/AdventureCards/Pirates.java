package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CannonShotPenalty;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;
import Model.Board.AdventureCards.Projectiles.CannonShot;

import java.util.ArrayList;
import java.util.List;


import Model.Enums.Side;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Pirates extends Enemy<CannonShotPenalty, Credits> {

    public Pirates(int id, CardLevel level, int power, List<CannonShot> cannonShots, int days, int credits) {
        super(id, level, power, new CannonShotPenalty(cannonShots), days, new Credits(credits));
    }

    @Override
    public final String getName() {
        return "Pirati";
    }

    @Override
    public final String getDescription() {
        return "";
    }

    public Pirates(JsonObject json) {
        super(
                json,
                // build the cannon‐shot list from JSON
                new CannonShotPenalty(
                        parseShots(
                                json.getAsJsonObject("penalty")
                                        .getAsJsonArray("shots")
                        )
                ),
                // day cost
                json.getAsJsonObject("penalty").has("days")
                        ? json.getAsJsonObject("penalty").get("days").getAsInt()
                        : 1,
                // credit reward
                new Credits(
                        json.getAsJsonObject("reward").has("credits")
                                ? json.getAsJsonObject("reward").get("credits").getAsInt()
                                : 2
                )
        );
    }

    private static List<CannonShot> parseShots(JsonArray arr) {
        List<CannonShot> shots = new ArrayList<>();
        for (JsonElement e : arr) {
            JsonObject o    = e.getAsJsonObject();
            boolean   large = o.get("isLarge").getAsBoolean();
            Side      dir   = Side.valueOf(o.get("direction").getAsString());
            shots.add(new CannonShot(large, dir));
        }
        return shots;
    }






}
