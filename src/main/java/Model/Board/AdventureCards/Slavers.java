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
        super(json,
              new CrewPenalty(json.has("penalty") && json.getAsJsonObject("penalty").has("crewLoss") ? 
                  json.getAsJsonObject("penalty").get("crewLoss").getAsInt() : 1),
              json.has("penalty") && json.getAsJsonObject("penalty").has("days") ? 
                  json.getAsJsonObject("penalty").get("days").getAsInt() : 0,
              new Credits(json.has("reward") && json.getAsJsonObject("reward").has("credits") ? 
                  json.getAsJsonObject("reward").get("credits").getAsInt() : 2)
        );
    }

    @Override
    public void visualize() {
        // 1) Print the shared header (ID, name, level, description, imagePath)
        super.visualize();

        // 2) Print the challenge power
        System.out.println("Power:           " + getPower());

        // 3) Print the loss penalty (CrewPenalty)
        System.out.println("Crew Lost:       " + getLossPenalty());

        // 4) Print the win penalty (DaysPenalty)
        System.out.println("Win Penalty:     " + getWinPenalty());

        // 5) Print the win reward (Credits)
        System.out.println(
                "Win Reward:      " + getWinReward().getAmount() + " credits"
        );
    }

}
