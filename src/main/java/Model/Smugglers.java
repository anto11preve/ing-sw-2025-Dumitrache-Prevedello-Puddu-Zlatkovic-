package Model;

import java.util.ArrayList;
import java.util.List;

public class Smugglers extends AdventureCard{
    private int firePower;
    private List<Good> goods;
    private int LostGoods;
    private int daysLost;

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

}
