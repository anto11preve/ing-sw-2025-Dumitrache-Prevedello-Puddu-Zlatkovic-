package Model;

import java.util.ArrayList;
import java.util.List;

public class AbandonedStation extends AdventureCard {
    private Crewmates crew;
    List<Good> goods;
    private int lostDays;

    public AbandonedStation(int id, CardLevel level, Crewmates crew, int lostDays) {
        super(id, level);
        this.crew = crew;
        this.lostDays = lostDays;
        this.goods = new ArrayList<>();
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
