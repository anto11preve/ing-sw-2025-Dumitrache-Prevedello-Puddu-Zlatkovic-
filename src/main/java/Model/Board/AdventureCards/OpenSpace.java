package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

public class OpenSpace extends AdventureCardFilip {

    public OpenSpace(int id, CardLevel level) {
        super(id, level);
    }

    @Override
    public String getName() {
        return "Spazio Aperto";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
