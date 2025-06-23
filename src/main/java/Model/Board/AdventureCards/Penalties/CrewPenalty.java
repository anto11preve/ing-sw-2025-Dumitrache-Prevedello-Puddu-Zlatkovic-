package Model.Board.AdventureCards.Penalties;

import com.google.gson.JsonObject;

public class CrewPenalty extends RegularPenalty {
    public CrewPenalty(int amount) {
        super(amount);
    }

    public CrewPenalty(JsonObject json) {
        super(json);

    }
}
