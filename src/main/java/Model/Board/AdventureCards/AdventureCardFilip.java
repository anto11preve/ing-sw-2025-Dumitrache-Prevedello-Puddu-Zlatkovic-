package Model.Board.AdventureCards;

import Controller.CardResolverVisitor;
import Controller.Controller;
import Model.Enums.CardLevel;
import Model.Utils.CardLevelMapper;
import com.google.gson.JsonObject;

// Classe per la gestione delle carte avventura
public abstract class AdventureCardFilip {
    private final int id;
    private final CardLevel level;
    private String imagePath;

    public AdventureCardFilip(int id, CardLevel level) {
        this.id = id;
        this.level = level;
    }

    public AdventureCardFilip(JsonObject json) {
        this.id         = json.get("id").getAsInt();
        this.level      = CardLevelMapper.mapJsonLevelToCardLevel(json.get("level").getAsString());
        this.imagePath  = json.has("imagePath")
                ? json.get("imagePath").getAsString()
                : "";               // or null
    }

    public String getImagePath() {
        return imagePath;
    }

    public final int getId() {
        return id;
    }

    public final CardLevel getLevel() {
        return level;
    }

    public abstract String getName();

    public abstract String getDescription();

    public void accept(CardResolverVisitor cardResolverVisitor, Controller controller) {
    }
}
