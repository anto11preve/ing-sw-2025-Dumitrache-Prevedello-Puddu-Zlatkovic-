package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
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
              new CrewPenalty(json),
              new Credits(json)
        );
    }

    @Override
    public void visualize() {
        // 1) common header
        super.visualize();

        // 2) encounter power
        System.out.println("Power:                " + getPower());

        // 3) loss penalty (CrewPenalty)
        CrewPenalty cp = getLossPenalty();
        System.out.printf(
                "Loss Penalty:         %s (type: %s)%n",
                cp,
                cp.getClass().getSimpleName()
        );

        // 4) win penalty (DaysPenalty)
        DaysPenalty dp = getWinPenalty();
        System.out.printf(
                "Win Penalty:          %s (type: %s)%n",
                dp,
                dp.getClass().getSimpleName()
        );

        // 5) win reward (Credits)
        Credits cr = getWinReward();
        System.out.printf(
                "Win Reward:           %d credits (type: %s)%n",
                cr.getAmount(),
                cr.getClass().getSimpleName()
        );
    }

}
