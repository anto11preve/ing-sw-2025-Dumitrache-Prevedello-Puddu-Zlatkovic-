package Model.Board.AdventureCards.Penalties;

import Model.Board.AdventureCards.Projectiles.CannonShot;

import java.util.Iterator;
import java.util.List;

public class CannonShotPenalty extends Penalty implements Iterable<CannonShot> {
    private final List<CannonShot> CannonShots;

    public CannonShotPenalty(List<CannonShot> CannonShots) {
        this.CannonShots = CannonShots;
    }

    @Override
    public final Iterator<CannonShot> iterator() {
        return this.CannonShots.iterator();
    }
}
