package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

import java.util.List;

public class Planets extends AdventureCard {
    private final List<Planet> planetList;
    private final int landingPenalty;

    public Planets(int id, CardLevel level, List<Planet> planetList, int landingPenalty) {
        super(id, level);
        this.planetList = planetList;
        this.landingPenalty = landingPenalty;
    }

    public List<Planet> showPlanets() {
        return planetList;
    }

    public int getLandingPenalty() {
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
}
