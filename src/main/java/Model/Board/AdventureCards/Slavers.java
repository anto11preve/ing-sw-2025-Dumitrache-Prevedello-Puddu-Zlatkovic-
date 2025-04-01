package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

class Slavers extends AdventureCard {
    private final int firePower;
    private final int crewLost;
    private final int credits;
    private final int daysLost;

    public Slavers(int id, CardLevel level, int firePower, int crewLost, int credits, int daysLost) {
        super(id, level);
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
