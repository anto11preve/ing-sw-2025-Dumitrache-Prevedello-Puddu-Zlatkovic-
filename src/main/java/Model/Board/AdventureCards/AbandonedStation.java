package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import Model.Enums.Crewmates;
import Model.Enums.Good;

import java.util.List;

public class AbandonedStation extends AdventureCard {
    private final int crew;
    private final List<Good> goods;
    private final int lostDays;

    public AbandonedStation(int id, CardLevel level, int crew, int lostDays, List<Good> goods) {
        super(id, level);
        this.crew = crew;
        this.lostDays = lostDays;
        this.goods = goods;
    }

    public int getCrew() {
        return crew;
    }

    public int getLostDays() {
        return lostDays;
    }

    public List<Good> getGoods() {
        return goods;
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
