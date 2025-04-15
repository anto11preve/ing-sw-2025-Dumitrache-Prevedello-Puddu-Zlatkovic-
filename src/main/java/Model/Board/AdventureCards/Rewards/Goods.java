package Model.Board.AdventureCards.Rewards;

import Model.Enums.Good;

import java.util.List;

public class Goods extends Reward {
    private final List<Good> goods;

    public Goods(List<Good> goods) {
        this.goods = goods;
    }

    public List<Good> getGoods() {
        return goods;
    }
}
