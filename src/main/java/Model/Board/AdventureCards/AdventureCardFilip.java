package Model.Board.AdventureCards;

import Controller.CardResolverVisitor;
import Controller.Controller;
import Model.Enums.CardLevel;

// Classe per la gestione delle carte avventura
public abstract class AdventureCardFilip {
    private final int id;
    private final CardLevel level;

    public AdventureCardFilip(int id, CardLevel level) {
        this.id = id;
        this.level = level;
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
