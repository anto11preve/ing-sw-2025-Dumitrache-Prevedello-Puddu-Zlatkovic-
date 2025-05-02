package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

public class Stardust extends AdventureCardFilip {

    public Stardust(int id, CardLevel level) {
        super(id, level);
    }

    @Override
    public final String getName() {
        return "Polvere Stellare";
    }

    @Override
    public final String getDescription() {
        return "";
    }
}
