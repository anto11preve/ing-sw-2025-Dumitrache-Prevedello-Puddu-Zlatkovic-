package Model.Board.AdventureCards.Projectiles;

import Model.Enums.Side;

public abstract class Projectile {
    private final boolean big;
    private final Side side;

    public Projectile(boolean big, Side side) {
        this.big = big;
        this.side = side;
    }

    public final boolean isBig() {
        return big;
    }

    public final Side getSide() {
        return side;
    }
}
