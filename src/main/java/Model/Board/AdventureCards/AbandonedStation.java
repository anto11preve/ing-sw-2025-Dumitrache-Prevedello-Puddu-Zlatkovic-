package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Good;

import java.util.List;

public class AbandonedStation extends AdventureCard {
    private final int crew;
    private final Goods landingReward;
    private final DaysPenalty landingPenalty;

    public AbandonedStation(int id, CardLevel level, int crew, List<Good> goods, int days) {
        super(id, level);
        this.crew = crew;
        this.landingPenalty = new DaysPenalty(days);
        this.landingReward = new Goods(goods);
    }

    public int getCrew() {
        return crew;
    }

    public DaysPenalty getLandingPenalty() {
        return landingPenalty;
    }

    public Goods getLandingReward(){
        return landingReward;
    }

    @Override
    public String getName() {
        return "Stazione Abbandonata";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
