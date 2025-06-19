package Model.Board.AdventureCards;

import Controller.CardResolverVisitor;
import Controller.Controller;
import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

// Classe per la gestione delle carte avventura
public abstract class AdventureCardFilip {
    private final int id;
    private final CardLevel level;

    public AdventureCardFilip(int id, CardLevel level) {
        this.id = id;
        this.level = level;
    }
    
    /**
     * Constructor for creating an AdventureCardFilip from a JSON object.
     * This is meant to be used by subclasses.
     * 
     * @param json the JSON object containing the card data
     */
    public AdventureCardFilip(com.google.gson.JsonObject json) {
        this.id = json.get("id").getAsInt();
        this.level = Model.Utils.CardLevelMapper.mapJsonLevelToCardLevel(json.get("level").getAsString());
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
