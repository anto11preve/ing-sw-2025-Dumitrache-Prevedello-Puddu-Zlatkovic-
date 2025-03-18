package Model;

import java.util.List;

class Pirates extends AdventureCard {
    private int firePower;
    private List<Cannonata> cannonate;
    private int credits;
    private int daysLost;

    public Pirates(int firePower, List<Cannonata> cannonate, int credits, int daysLost) {
        super("Pirates", "Pirates attack");
        this.firePower = firePower;
        this.cannonate = cannonate;
        this.credits = credits;
        this.daysLost = daysLost;
    }

    public int getFirePower() {
        return firePower;
    }

    public List<Cannonata> getCannonate() {
        return cannonate;
    }

    public int getCredits() {
        return credits;
    }

    public int getDaysLost() {
        return daysLost;
    }
}
