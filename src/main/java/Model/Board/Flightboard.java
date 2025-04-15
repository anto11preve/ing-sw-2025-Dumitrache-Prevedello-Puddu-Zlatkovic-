package Model.Board;

import Model.Board.AdventureCards.AdventureCard;
import Model.Enums.TimerPhase;
import Model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//we're missing the 4 piles of cards
public class Flightboard {
    Timer timer;
    List<CardDeck> peekableCardDecks;
    CardDeck hiddenCardDeck;
    CardDeck upcomingCardDeck;
    private Map<Player, Integer> playerPositions;

    public Flightboard() {
        this.timer = new Timer(TimerPhase.NOT_USED);
        this.peekableCardDecks = List.of(new CardDeck(), new CardDeck(), new CardDeck());
        this.hiddenCardDeck = new CardDeck();
        this.upcomingCardDeck = new CardDeck();
        this.playerPositions = new HashMap<>();
    }

    public Timer getTimer() {
        return timer;
    }

    public CardDeck getPeekableCardDeck(int index) {
        return peekableCardDecks.get(index);
    }
    public CardDeck getHiddenCardDeck() {
        return hiddenCardDeck;
    }

    public CardDeck getUpcomingCardDeck() {
        return upcomingCardDeck;
    }

    public void setUpcomingCardDeck(){
        // Aggiungi le carte dei peekableCardDecks
        for (CardDeck deck : peekableCardDecks) {
            for (AdventureCard card : deck.peekCards()) {
                upcomingCardDeck.pushCard(card);
            }
        }

        // Aggiungi le carte del hiddenCardDeck
        for (AdventureCard card : hiddenCardDeck.peekCards()) {
            upcomingCardDeck.pushCard(card);
        }

    }

    //public Model.Cards.AdventureCard nextAdventure() { }
    public void updatePosition(Player player, int position) {
        playerPositions.put(player, position);
    }

    public Player[] getTurnOrder() {
        return playerPositions.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toArray(Player[]::new);
    }
}
