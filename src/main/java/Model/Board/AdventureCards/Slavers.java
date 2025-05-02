package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;

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
}
