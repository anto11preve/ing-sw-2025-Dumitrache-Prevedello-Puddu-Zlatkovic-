package Model.Board;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Enums.CardLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CardDeck class.
 * This test suite verifies the correct behavior of the deck construction,
 * copying, iteration, and basic manipulation features.
 */
public class CardDeckTest {

    // Dummy subclass to allow instantiation of abstract AdventureCardFilip
    static class DummyAdventureCard extends AdventureCardFilip {
        private final String name;
        private final String description;

        public DummyAdventureCard(int id, CardLevel level, String name, String description) {
            super(id, level);
            this.name = name;
            this.description = description;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    private AdventureCardFilip card1;
    private AdventureCardFilip card2;
    private AdventureCardFilip card3;

    @BeforeEach
    public void setup() {
        // Dummy cards for test purposes
        card1 = new DummyAdventureCard(1, CardLevel.LEVEL_ONE, "Card1", "Description1");
        card2 = new DummyAdventureCard(2, CardLevel.LEVEL_ONE, "Card2", "Description2");
        card3 = new DummyAdventureCard(3, CardLevel.LEVEL_ONE, "Card3", "Description3");
    }

    @Test
    public void constructor_createsEmptyDeck() {
        CardDeck deck = new CardDeck();
        assertNotNull(deck);
        assertFalse(deck.iterator().hasNext(), "Expected empty deck to have no cards");
    }

    @Test
    public void constructor_withList_copiesCards() {
        List<AdventureCardFilip> cards = Arrays.asList(card1, card2);
        CardDeck deck = new CardDeck(cards);

        Iterator<AdventureCardFilip> it = deck.iterator();
        assertEquals(card1, it.next(), "First card should match");
        assertEquals(card2, it.next(), "Second card should match");
    }

    @Test
    public void constructor_withArray_copiesCards() {
        AdventureCardFilip[] cards = new AdventureCardFilip[] { card1, card2, card3 };
        CardDeck deck = new CardDeck(cards);

        Iterator<AdventureCardFilip> it = deck.iterator();
        assertEquals(card1, it.next());
        assertEquals(card2, it.next());
        assertEquals(card3, it.next());
    }

    @Test
    public void shuffle_randomizesOrder() {
        List<AdventureCardFilip> cards = Arrays.asList(card1, card2, card3);
        CardDeck deck = new CardDeck(cards);

        List<AdventureCardFilip> originalOrder = new ArrayList<>(cards);
        deck.shuffle();

        List<AdventureCardFilip> shuffledOrder = new ArrayList<>();
        for (AdventureCardFilip card : deck) {
            shuffledOrder.add(card);
        }

        // It's possible for shuffle to return the same order, but unlikely
        assertNotEquals(originalOrder, shuffledOrder, "Shuffled deck should differ from original order (statistically)");
        assertTrue(shuffledOrder.containsAll(originalOrder), "Shuffled deck should contain the same cards");
    }
}
