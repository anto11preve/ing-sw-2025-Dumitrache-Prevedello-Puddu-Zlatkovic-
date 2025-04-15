package Model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AdventureCardLoaderTest {

    @Test
    void testLoadAdventureCards() {
        List<AdventureCard> cards = AdventureCardLoader.loadCards("adventure_cards_complete.json");
        assertNotNull(cards, "La lista di carte non dovrebbe essere null");
        assertFalse(cards.isEmpty(), "La lista di carte non dovrebbe essere vuota");
        assertNotNull(cards.get(0).getName(), "La prima carta dovrebbe avere un nome");
    }

    @Test
    void testCardWithOptions() {
        List<AdventureCard> cards = AdventureCardLoader.loadCards("adventure_cards_complete.json");
        AdventureCard cardWithOptions = cards.stream()
                .filter(c -> c.isRequiresPlayerChoice() && c.getOptions() != null && !c.getOptions().isEmpty())
                .findFirst()
                .orElse(null);

        assertNotNull(cardWithOptions, "Dovrebbe esistere almeno una carta con opzioni");
        assertNotNull(cardWithOptions.getOptions().get(0).getLabel(), "L'opzione dovrebbe avere del testo");
    }

    @Test
    void testInvalidFileReturnsNull() {
        List<AdventureCard> result = AdventureCardLoader.loadCards("non_esiste.json");
        assertNull(result, "Il caricamento di un file non esistente dovrebbe restituire null");
    }
}
