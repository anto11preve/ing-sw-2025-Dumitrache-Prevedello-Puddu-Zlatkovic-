package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

import java.util.List;

public class Pirates extends AdventureCard {
    private final int firePower;
    private final List<CannonShot> cannonShots;
    private final int credits;
    private final int daysLost;

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
