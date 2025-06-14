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
                json.get("id").getAsInt(),
                CardLevel.valueOf(json.get("level").getAsString()),
                json.getAsJsonObject("enemy").get("firepower").getAsInt(),
                new CannonShotPenalty(parseCannonShots(json.getAsJsonObject("enemy").getAsJsonArray("penalty"))),
                json.getAsJsonObject("enemy").get("days").getAsInt(),
                new Credits(json.getAsJsonObject("enemy").getAsJsonObject("reward").get("value").getAsInt())
        );
    }

    private static List<CannonShot> parseCannonShots(JsonArray array) {
        List<CannonShot> list = new ArrayList<>();
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            list.add(new CannonShot(
                    false, // not a double cannon
                    Side.valueOf(obj.get("direction").getAsString())
            ));
        }
        return list;
    }





}
