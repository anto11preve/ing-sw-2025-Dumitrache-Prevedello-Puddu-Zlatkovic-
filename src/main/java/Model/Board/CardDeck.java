package Model.Board;

import Model.Board.AdventureCards.AdventureCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Basically a wrapper of {@code List<AdventureCard>}.
 *
 * <p>Originally intended as an abstraction to be
 * further expanded upon, it is currently only a
 * wrapper.
 *
 * <p>Would benefit if further specialized into
 * {@code HiddenCardDeck}, {@code PeekableCardDeck}
 * and {@code UpcomingCardDeck}. Why? Mostly because
 * not every method should be usable for each type
 * of deck (and even if so they should not all
 * behave the same way).
 *
 * @see List
 * @see AdventureCard
 */
public class CardDeck implements Iterable<AdventureCard> {
    /**
     * {@code List} of {@code AdventureCard}
     *
     * @see List
     * @see AdventureCard
     */
    private final List<AdventureCard> cards;

    /**
     * Default {@code CardDeck} constructor.
     *
     * <p>Sets {@code this.cards} to be an empty
     * {@code ArrayList} of {@code AdventureCard}s.
     */
    public CardDeck() {
        this.cards = new ArrayList<>();
    }

    /**
     * {@code CardDeck} constructor given a {@code List}
     * of {@code AdventureCard}s.
     *
     * <p>Sets {@code this.cards} as an {@code ArrayList}
     * copy of the {@code cards} parameter.
     *
     * @param cards {@code List} of {@code AdventureCards}
     *              to be copied
     */
    public CardDeck(List<AdventureCard> cards) {
        this();

        this.cards.addAll(cards);
    }

    /**
     * {@code CardDeck} constructor given an {@code Array}
     * of {@code AdventureCard}s.
     *
     * <p>Sets {@code this.cards} as an {@code ArrayList}
     * copy of the {@code cards} parameter.
     *
     * @param cards {@code Array} of {@code AventureCard}s
     *              to be copied
     */
    public CardDeck(AdventureCard[] cards) {
        this(Arrays.asList(cards));
    }

    /**
     * {@code CardDeck} suffler: creates new {@code CardDeck}
     * with shuffled {@code AdventureCards}. Does
     * <i>not</i> change {@code this} deck's {@code List}
     * of {@code AdventureCards}.
     *
     * @return new {@code CardDeck} with elements of
     * {@code this}, but shuffled
     */
    public CardDeck shuffleDeck() {
        CardDeck other = new CardDeck(this.cards);

        Collections.shuffle(other.cards);

        return other;
    }

    /**
     * Removes the first {@code AdventureCard}
     * from the deck and it returns it
     *
     * @return card {@code AdventureCard} that
     * was on top of the deck
     */
    public AdventureCard popCard() {
        return cards.removeFirst();
    }

    /**
     * Puts {@code AdventureCard} {@code card} at the top
     * of the deck.
     *
     * <p>Any call to {@code popCard} will return the
     * content of the latest {@code pushCard} call.
     *
     * @param card Card to be placed on top of the deck
     */
    public void pushCard(AdventureCard card) {
        cards.addFirst(card);
    }

    /**
     * This method creates a copy of the cards
     * present in the deck and returns them as
     * a List.
     *
     * @return copy of {@code this.cards} list
     */
    public List<AdventureCard> peekCards() {
        ArrayList<AdventureCard> cards = new ArrayList<>();

        for (AdventureCard card : this.cards) {
            cards.add(card);
        }

        return cards;
    }

    @Override
    public Iterator<AdventureCard> iterator() {
        return peekCards().iterator();
    }
}
