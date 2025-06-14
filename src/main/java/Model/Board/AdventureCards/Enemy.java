package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Penalties.Penalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Board.AdventureCards.Rewards.Reward;
import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

/**
 * @param <P> Penalty inflicted upon the player if the card is not beaten
 * @param <R> Reward given to the player if the card is beaten
 */
public abstract class Enemy<P extends Penalty, R extends Reward> extends AdventureCardFilip {
    private final int power;
    private final P lossPenalty;
    private final DaysPenalty winPenalty;
    private final R winReward;

    public Enemy(int id, CardLevel level, int power, P lossPenalty, int days, R winReward) {
        super(id, level);
        this.power = power;
        this.lossPenalty = lossPenalty;
        this.winPenalty = new DaysPenalty(days);
        this.winReward = winReward;
    }

    public final int getPower() {
        return power;
    }

    public final P getLossPenalty() {
        return lossPenalty;
    }

    public final DaysPenalty getWinPenalty() {
        return winPenalty;
    }

    public final R getWinReward() {
        return winReward;
    }


}
