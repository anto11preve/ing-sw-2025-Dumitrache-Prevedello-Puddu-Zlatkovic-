package Model.Board;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.AdventureCards.OpenSpace;
import Model.Enums.CardLevel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckTest {

    @Test
    public void testConstructor() {
        List<AdventureCardFilip> cards = new ArrayList<>();
        cards.add(new OpenSpace(1, CardLevel.LEARNER));
        cards.add(new OpenSpace(2, CardLevel.LEVEL_ONE));
        
        CardDeck deck = new CardDeck(cards);
        assertEquals(2, deck.peekCards().size());
        assertFalse(deck.peekCards().isEmpty());
    }
    
    @Test
    public void testEmptyDeck() {
        CardDeck deck = new CardDeck(new ArrayList<>());
        assertEquals(0, deck.peekCards().size());
        assertTrue(deck.peekCards().isEmpty());
    }
    
    @Test
    public void testPopCard() {
        List<AdventureCardFilip> cards = new ArrayList<>();
        OpenSpace card1 = new OpenSpace(1, CardLevel.LEARNER);
        OpenSpace card2 = new OpenSpace(2, CardLevel.LEVEL_ONE);
        cards.add(card1);
        cards.add(card2);
        
        CardDeck deck = new CardDeck(cards);
        AdventureCardFilip drawn = deck.popCard();
        
        assertEquals(1, deck.peekCards().size());
        assertNotNull(drawn);
        assertTrue(drawn == card1 || drawn == card2);
    }
    
    @Test
    public void testPopFromEmptyDeck() {
        CardDeck deck = new CardDeck(new ArrayList<>());
        assertThrows(IndexOutOfBoundsException.class, deck::popCard);
    }
}