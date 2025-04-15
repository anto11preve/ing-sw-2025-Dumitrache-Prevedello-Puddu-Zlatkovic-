package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

public class AbandonedShip extends AdventureCard {
    private final int crew;
    private final int credits;
    private final int days;

    public AbandonedShip(int id, CardLevel level, int crew, int credits, int days) {
        super(id, level);
        this.crew = crew;
        this.credits = credits;
        this.days = days;
    }

    public int getCrew() {
        return crew;
    }

    public int getCredits() {
        return credits;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String getName() {
        return "Nave Abbandonata";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
