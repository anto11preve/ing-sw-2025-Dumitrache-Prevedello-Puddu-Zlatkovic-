package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

public class Slavers extends Enemy<CrewPenalty, Credits> {
    public Slavers(int id, CardLevel level, int power, int crew, int days, int credits) {
        super(id, level, power, new CrewPenalty(crew), days, new Credits(credits));
    }

    @Override
    public final String getName() {
        return "Schiavisti";
    }

    @Override
    public final String getDescription() {
        return "";
    }

    public Slavers(JsonObject json) {
        super(
                json.get("id").getAsInt(),
                CardLevel.valueOf(json.get("level").getAsString()),
                json.getAsJsonObject("enemy").get("firepower").getAsInt(),
                new CrewPenalty(json.getAsJsonObject("enemy").getAsJsonObject("penalty").get("value").getAsInt()),
                json.getAsJsonObject("enemy").get("days").getAsInt(),
                new Credits(json.getAsJsonObject("enemy").getAsJsonObject("reward").get("value").getAsInt())
        );
    }
}
