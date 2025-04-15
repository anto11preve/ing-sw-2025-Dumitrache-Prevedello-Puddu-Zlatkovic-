package Model.Board.AdventureCards.Penalties;

public abstract class RegularPenalty extends Penalty {
    private final int amount;

    public RegularPenalty(int amount) {
        this.amount = amount;
    }

    public final int getAmount() {
        return amount;
    }
}
