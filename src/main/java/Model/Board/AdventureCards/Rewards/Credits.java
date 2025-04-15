package Model.Board.AdventureCards.Rewards;

public class Credits extends Reward{
    private final int amount;

    public Credits(int amount) {
        this.amount = amount;
    }

    public final int getAmount() {
        return amount;
    }
}
