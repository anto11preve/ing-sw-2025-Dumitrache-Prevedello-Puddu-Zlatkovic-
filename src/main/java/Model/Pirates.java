package Model;

import java.util.List;

class Pirates extends AdventureCard {
    private int firePower;
    private List<CannonShot> cannonShots;
    private int credits;
    private int daysLost;

    public Pirates(int id, CardLevel level, int firePower, List<CannonShot> cannonShots, int credits, int daysLost) {
        super(id, level);
        this.firePower = firePower;
        this.cannonShots = cannonShots;
        this.credits = credits;
        this.daysLost = daysLost;
    }

    public int getFirePower() {
        return firePower;
    }

    public List<CannonShot> getCannonShots() {
        return cannonShots;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysLost() {
        return daysLost;
    }

}
