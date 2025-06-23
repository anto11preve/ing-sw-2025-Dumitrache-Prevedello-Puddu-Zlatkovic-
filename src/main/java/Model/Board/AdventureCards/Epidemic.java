package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

public class Epidemic extends AdventureCardFilip {



    public Epidemic(int id, CardLevel level) {
        super(id, level);
    }

    public Epidemic(JsonObject json) {
        super(json);
    }

    @Override
    public void visualize() {
        // 1) Generic card info: ID, name, level, description, imagePath
        super.visualize();
        //System.out.println("Crew Lost:       " + crewLost);
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
