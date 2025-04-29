package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

public class Epidemic extends AdventureCardFilip {

    public Epidemic(int id, CardLevel level) {
        super(id, level);
    }

    @Override
    public String getName() {
        return "Epidemia";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
