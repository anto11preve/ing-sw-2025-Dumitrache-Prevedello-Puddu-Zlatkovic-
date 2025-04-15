package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.CardLevel;
import Model.Enums.Good;

import java.util.List;

public class Smugglers extends Enemy {

    public Smugglers(int id, CardLevel level, int power, int lostGoods, int days, List<Good> goods) {
        super(id, level, power, new GoodsPenalty(lostGoods), days, new Goods(goods));
    }

    @Override
    public final GoodsPenalty getLossPenalty() {
        return (GoodsPenalty) super.getLossPenalty();
    }

    @Override
    public Goods getWinReward() {
        return (Goods) super.getWinReward();
    }

    @Override
    public final String getName() {
        return "Contrabbandieri";
    }

    @Override
    public final String getDescription() {
        return "";
    }
}
