package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import Controller.Enums.MatchLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdventureCardLoaderTest {

//        TODO: non c'è più un file per ogni livello
//    @Test
//    public void testLoadCards_trialPath_success() {
//        List<AdventureCardFilip> cards = AdventureCardLoader.loadCards("adventure_cards_trial.json");
//
//      assertNotNull(cards, "List should not be null when file exists");
//      assertEquals(8, cards.size(), "TRIAL file should contain exactly 8 cards");
//
//        for (AdventureCardFilip card : cards) {
//            assertNotNull(card.getName(), "Card name must not be null");
//            assertTrue(card.getId() > 0, "Card ID must be positive");
//        }
//    }
//
//
//    @Test
//    public void testLoadCards_level2Path_success() {
//        List<AdventureCardFilip> cards = AdventureCardLoader.loadCards("adventure_cards_level2.json");
//
//        assertNotNull(cards, "List should not be null when file exists");
//        assertEquals(16, cards.size(), "LEVEL2 file should contain exactly 16 cards");
//
//        for (AdventureCardFilip card : cards) {
//            assertNotNull(card.getDescription(), "Card description should not be null");
//        }
//    }
//
//    @Test
//    public void testLoadCards_invalidPath_returnsNull() {
//        List<AdventureCardFilip> cards = AdventureCardLoader.loadCards();
//
//        assertNull(cards, "If file does not exist, method should return null");
//    }

    @Test
    public void testLoadAdventureCards_trial() {
        List<AdventureCardFilip> cards = AdventureCardLoader.loadAdventureCards(MatchLevel.TRIAL, true);

        assertNotNull(cards);
        assertEquals(8, cards.size());
        for (AdventureCardFilip card : cards) {
            assertEquals(MatchLevel.TRIAL, card.getLevel());
        }
    }

    @Test
    public void testLoadAdventureCards_level2() {
        List<AdventureCardFilip> cards = AdventureCardLoader.loadAdventureCards(MatchLevel.LEVEL2, true);

        assertNotNull(cards);
        assertEquals(16, cards.size());
        for (AdventureCardFilip card : cards) {
            assertEquals(MatchLevel.LEVEL2, card.getLevel());
        }
    }
}
