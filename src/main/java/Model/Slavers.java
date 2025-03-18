package Model;

class Slavers extends AdventureCard {
    private int firePower;
    private int crewLost;
    private int credits;
    private int daysLost;

    public Slavers(int firePower, int crewLost, int credits, int daysLost) {
        super("Slavers", "Slavers attack");
        this.firePower = firePower;
        this.crewLost = crewLost;
        this.credits = credits;
        this.daysLost = daysLost;
    }

    public int getFirePower() {
        return firePower;
    }

    public int getCrew() {
        return crewLost;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysLost() {
        return daysLost;
    }
}
