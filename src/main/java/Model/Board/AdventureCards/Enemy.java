package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Penalties.Penalty;
import Model.Board.AdventureCards.Rewards.Reward;
import Model.Enums.CardLevel;

public abstract class Enemy extends AdventureCard{
    private final int power;
    private final Penalty lossPenalty;
    private final DaysPenalty winPenalty;
    private final Reward winReward;

    public Enemy(int id, CardLevel level, int power, Penalty lossPenalty, int days, Reward winReward) {
        super(id, level);
        this.power = power;
        this.lossPenalty = lossPenalty;
        this.winPenalty = new DaysPenalty(days);
        this.winReward = winReward;
    }

    public final int getPower() {
        return power;
    }

    public Penalty getLossPenalty() {
        return lossPenalty;
    }

    public final DaysPenalty getWinPenalty() {
        return winPenalty;
    }

    public Reward getWinReward() {
        return winReward;
    }
}
