package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import Model.Board.AdventureCards.Projectiles.CannonShot;

import java.util.List;

public class CombatZone extends AdventureCard {
    private final int penalty;
    private final List<CannonShot> cannonShots;
    private final int days;

    public CombatZone(int id, CardLevel level, int penalty, int days, List<CannonShot> cannonShots) {
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

    @Override
    public String getName() {
        return "Zona di Guerra";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
