package Model.Board;

import Model.Board.AdventureCards.AdventureCard;

import java.util.ArrayList;
import java.util.List;


public class CardDeck {
    private List<AdventureCard> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
    }

    public AdventureCard popCard() {
        return cards.remove(0);
    }

    public void pushCard(AdventureCard card) {
        cards.add(card);
    }

    public List<AdventureCard> peekCards() {
        return cards;
    }
}
