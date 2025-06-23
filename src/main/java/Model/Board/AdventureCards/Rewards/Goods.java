package Model.Board.AdventureCards.Rewards;

import Model.Enums.Good;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Goods extends Reward implements Iterable<Good> {
    private final List<Good> goods;

    public Goods(List<Good> goods) {
        this.goods = goods;
    }


    @Override
    public final Iterator<Good> iterator() {
        return this.goods.iterator();
    }
}
