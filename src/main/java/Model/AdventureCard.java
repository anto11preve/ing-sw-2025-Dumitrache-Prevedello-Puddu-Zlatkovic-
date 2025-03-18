package Model;

// Classe per la gestione delle carte avventura
abstract class AdventureCard {
    protected final String name;
    protected String description;

    public AdventureCard(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
