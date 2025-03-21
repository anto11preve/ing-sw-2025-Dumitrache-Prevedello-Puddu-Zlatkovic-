package Model;

import java.util.ArrayList;
import java.util.List;

public class CombactZone extends AdventureCard {
    private int penalty;
    private List<CannonShot> cannonShots;
    private int days;

    public CombactZone(int id, CardLevel level, int penalty, int days) {
        super(id, level);
        this.penalty = penalty;
        this.days = days;
        this.cannonShots = new ArrayList<>();
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
