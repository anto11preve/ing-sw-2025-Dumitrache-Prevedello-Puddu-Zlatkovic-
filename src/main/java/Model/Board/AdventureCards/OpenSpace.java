package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

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

    public OpenSpace(JsonObject json) {
        super(json);
    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("---------------------------------------");
        System.out.println("This is an open space, no special action is required.");
        System.out.println("---------------------------------------");
    }

}
