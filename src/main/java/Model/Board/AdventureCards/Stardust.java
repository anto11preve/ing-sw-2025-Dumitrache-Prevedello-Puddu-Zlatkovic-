package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

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

    public Stardust(JsonObject json) {
        super(json);
    }

    @Override
    public void visualize() {
        super.visualize();
    }
}
