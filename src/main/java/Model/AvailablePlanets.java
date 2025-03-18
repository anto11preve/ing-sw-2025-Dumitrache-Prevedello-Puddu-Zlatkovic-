package Model;

import java.util.List;

class AvailablePlanets extends AdventureCard {
    private final List<Planet> planetList;
    private final int landingPenalty;

    public AvailablePlanets(List<Planet> planetList, int landingPenalty) {
        super("Available Planets", "List of planets where you can land");
        this.planetList = planetList;
        this.landingPenalty = landingPenalty;
    }

    public List<Planet> showPlanets() {
        return planetList;
    }

    public int getLandingPenalty() {
        return landingPenalty;
    }
}
