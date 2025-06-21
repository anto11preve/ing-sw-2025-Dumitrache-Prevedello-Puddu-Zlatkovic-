package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

public class Smugglers extends Enemy<GoodsPenalty, Credits> {
    private String imagePath;

    public Smugglers(int id, CardLevel level, int power, int lostGoods, int days, int credits) {
        super(id, level, power, new GoodsPenalty(lostGoods), days, new Credits(credits));
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
        super(json,
              new GoodsPenalty(json.has("penalty") && json.getAsJsonObject("penalty").has("cargoLoss") ? 
                  json.getAsJsonObject("penalty").get("cargoLoss").getAsInt() : 1),
              json.has("penalty") && json.getAsJsonObject("penalty").has("days") ? 
                  json.getAsJsonObject("penalty").get("days").getAsInt() : 0,
              new Credits(json.has("reward") && json.getAsJsonObject("reward").has("credits") ? 
                  json.getAsJsonObject("reward").get("credits").getAsInt() : 3)
        );
        this.imagePath = json.get("imagePath").getAsString();
    }

    public String getImagePath() {
        return imagePath;
    }
}
