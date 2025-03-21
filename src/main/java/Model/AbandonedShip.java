package Model;

public class AbandonedShip extends AdventureCard {
    private Crewmates crew;
    private int credits;
    private int days;

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
