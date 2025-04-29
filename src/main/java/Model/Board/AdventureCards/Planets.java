package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.Planet;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Enums.CardLevel;

import java.util.Iterator;
import java.util.List;

public class Planets extends AdventureCard implements Iterable<Planet>{
    private final List<Planet> planetList;
    private final DaysPenalty landingPenalty;

    public Planets(int id, CardLevel level, int days, List<Planet> planetList) {
        super(id, level);
        this.planetList = planetList;
        this.landingPenalty = new DaysPenalty(days);
    }

    public DaysPenalty getLandingPenalty() {
        return landingPenalty;
    }

    @Override
    public String getName() {
        return "Pianeti";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Iterator<Planet> iterator() {
        return this.planetList.iterator();
    }
}
