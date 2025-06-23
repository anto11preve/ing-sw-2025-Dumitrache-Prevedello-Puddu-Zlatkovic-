package Model.Board.AdventureCards.Penalties;

import com.google.gson.JsonObject;

public class GoodsPenalty extends RegularPenalty {
    public GoodsPenalty(int amount) {
        super(amount);
    }
    public GoodsPenalty(JsonObject json) {
        super(json);

    }
}
