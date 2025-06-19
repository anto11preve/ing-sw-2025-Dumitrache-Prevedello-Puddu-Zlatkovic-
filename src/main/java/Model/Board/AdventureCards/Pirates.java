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
        super(json, 
              new CannonShotPenalty(createDefaultCannonShots()),
              json.has("penalty") && json.getAsJsonObject("penalty").has("days") ? 
                  json.getAsJsonObject("penalty").get("days").getAsInt() : 1,
              new Credits(json.has("reward") && json.getAsJsonObject("reward").has("credits") ? 
                  json.getAsJsonObject("reward").get("credits").getAsInt() : 2)
        );
    }

    private static List<CannonShot> createDefaultCannonShots() {
        List<CannonShot> list = new ArrayList<>();
        // Add a default cannon shot from the front
        list.add(new CannonShot(false, Side.FRONT));
        return list;
    }





}
