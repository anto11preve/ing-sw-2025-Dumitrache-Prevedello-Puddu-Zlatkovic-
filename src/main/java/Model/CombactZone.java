package Model;

import java.util.ArrayList;
import java.util.List;

public class CombactZone extends AdventureCard {
    private final int penalty;
    private final List<CannonShot> cannonShots;
    private final int days;

    public CombactZone(int id, CardLevel level, int penalty, int days, List<CannonShot> cannonShots) {
        super(id, level);
        this.penalty = penalty;
        this.days = days;
        this.cannonShots = cannonShots;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getDays() {
        return days;
    }

    public List<CannonShot> getCannonShots() {
        return cannonShots;
    }

}
