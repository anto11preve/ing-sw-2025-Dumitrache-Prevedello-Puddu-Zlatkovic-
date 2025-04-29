package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;

public class AbandonedShip extends AdventureCardFilip {
    private final CrewPenalty winPenalty;
    private final Credits landingReward;
    private final DaysPenalty landingPenalty;

    public AbandonedShip(int id, CardLevel level, int crew, int credits, int days) {
        super(id, level);
        this.winPenalty = new CrewPenalty(crew);
        this.landingReward = new Credits(credits);
        this.landingPenalty = new DaysPenalty(days);
    }

    public int getCrew() {
        return winPenalty.getAmount();
    }

    public CrewPenalty getWinPenalty() {
        return winPenalty;
    }

    public Credits getLandingReward() {
        return landingReward;
    }

    public DaysPenalty getLandingPenalty() {
        return landingPenalty;
    }

    @Override
    public String getName() {
        return "Nave Abbandonata";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
