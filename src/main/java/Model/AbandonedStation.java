package Model;

import java.util.ArrayList;
import java.util.List;

public class AbandonedStation extends AdventureCard {
    private final Crewmates crew;
    private final List<Good> goods;
    private final int lostDays;

    public AbandonedStation(int id, CardLevel level, Crewmates crew, int lostDays, List<Good> goods) {
        super(id, level);
        this.crew = crew;
        this.lostDays = lostDays;
        this.goods = goods;
    }

    public Crewmates getCrew() {
        return crew;
    }

    public int getLostDays() {
        return lostDays;
    }

    public List<Good> getGoods() {
        return goods;
    }
}
