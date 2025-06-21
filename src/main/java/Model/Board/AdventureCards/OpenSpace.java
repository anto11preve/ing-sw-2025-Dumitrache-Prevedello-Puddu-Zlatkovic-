package Model.Board.AdventureCards;

import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

public class OpenSpace extends AdventureCardFilip {
    private String imagePath;

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
        this.imagePath = json.get("imagePath").getAsString();
    }

    public String getImagePath() {
        return imagePath;
    }

}
