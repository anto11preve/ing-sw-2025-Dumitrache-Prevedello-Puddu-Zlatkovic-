package Model;

public class AbandonedShip extends AdventureCard {
    private final Crewmates crew;
    private final int credits;
    private final int days;

    public AbandonedShip(int id, CardLevel level, Crewmates crew, int credits, int days) {
        super(id, level);
        this.crew = crew;
        this.credits = credits;
        this.days = days;
    }

    public Crewmates getCrew() {
        return crew;
    }

    public int getCredits() {
        return credits;
    }

    public int getDays() {
        return days;
    }
}
