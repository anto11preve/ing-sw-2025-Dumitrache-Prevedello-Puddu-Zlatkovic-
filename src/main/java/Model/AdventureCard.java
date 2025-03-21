package Model;

// Classe per la gestione delle carte avventura
abstract class AdventureCard {
    private final int id;
    private final CardLevel level;

    public AdventureCard(int id, CardLevel level) {
        this.id = id;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public CardLevel getLevel() {
        return level;
    }

}
