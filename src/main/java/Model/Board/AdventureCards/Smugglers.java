package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import Model.Enums.Good;

import java.util.List;

public class Smugglers extends AdventureCard {
    private final int firePower;
    private final List<Good> goods;
    private final int LostGoods;
    private final int daysLost;

    public Smugglers(int id, CardLevel level, int firePower, int LostGoods, int daysLost, List<Good> goods) {
        super(id, level);
        this.firePower = firePower;
        this.LostGoods = LostGoods;
        this.daysLost = daysLost;
        this.goods = goods;
    }

    public int getFirePower() {
        return firePower;
    }

    public int getLostGoods() {
        return LostGoods;
    }

    public int getDaysLost() {
        return daysLost;
    }

    public List<Good> getGoods() {
        return goods;
    }

    @Override
    public String getName() {
        return "Contrabbandieri";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
