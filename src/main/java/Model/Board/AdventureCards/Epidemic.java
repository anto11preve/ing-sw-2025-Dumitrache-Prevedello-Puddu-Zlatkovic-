package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

public class Epidemic extends AdventureCardFilip {

    private int crewLost = 0;

    public Epidemic(int id, CardLevel level) {
        super(id, level);
    }

    public Epidemic(JsonObject json) {
        super(json);
        if (json.has("penalty") && json.getAsJsonObject("penalty").has("crewLoss")) {
            this.crewLost = json.getAsJsonObject("penalty").get("crewLoss").getAsInt();
        }
    }

    public int getCrewLost() {
        return crewLost;
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
