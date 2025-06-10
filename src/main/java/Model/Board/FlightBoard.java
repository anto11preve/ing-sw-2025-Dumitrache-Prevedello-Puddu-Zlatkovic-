package Model.Board;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Enums.CardLevel;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Controller.Enums.MatchLevel;

import java.util.*;

/**
 * FlightBoard: it holds all the good stuff you
 * need during the building and the flying phase.
 *
 * <p>This class would really benefit from getting
 * split into LearnerFlightBoard and LevelTwoFlightBoard.
 * Why? In the learner flight, specifically in the building
 * phase, timer doesn't exist (see page 4 of
 * {@code galaxy-trucker-rules-it.pdf}), the hiddenCardDeck
 * is technically not formed yet (see page 9) and there
 * is no such thing as a peekableCardDeck. Regardless of
 * the phase, the number of cells differs, as does the layout,
 * and it would overall be a good idea to abstract this better.
 *
 * <p>Also would benefit if split into FlightBoard
 * and BuildingFlightBoard (or BuildingBoard).
 * Why? timer, hiddenCardDeck and peekableCardDecks
 * do not exist while flying (but this is a less pressing
 * issue than the one stated earlier).
 */
public class FlightBoard {
    private final int cellNumber;

    private Timer timer;

    private List<CardDeck> peekableCardDecks;

    private CardDeck hiddenCardDeck;

    private CardDeck upcomingCardDeck;

    private final Map<Player, Integer> playerPositions;

    /**
     * FlightBoard default constructor stub.
     *
     * <p>Now always throws because: How can I know if
     * it's supposed to be a learner or a level 2
     * flightboard? How can I make such assumption?
     * Should I make such an assumption?
     *
     * @throws UnsupportedOperationException always
     */

    public FlightBoard() {
        throw new UnsupportedOperationException("This FlightBoard constructor is no longer accepted");
    }

    /**
     * FlightBoard constructor of learner level.
     * Sets the number of cells to 18.
     * Sets the hidden card deck to the passed value.
     * Sets the player positions map to an empty HashMap.
     * Sets every other member to {@code null}.
     *
     * @param hiddenCards the deck of cards to be used later in the
     *                    flight phase
     * @throws AssertionError if something is wrong with passed deck:
     *                        the number of cards is not 8, the level of the cards is not LEARNER...
     * @see CardDeck
     * @see AssertionError
     */
    public FlightBoard(AdventureCardFilip[] hiddenCards) throws AssertionError {
        this.cellNumber = 18;
        this.timer = null;
        this.peekableCardDecks = null;

        this.hiddenCardDeck = new CardDeck(hiddenCards);

        assert (this.hiddenCardDeck.peekCards().size() == 8);

        for (AdventureCardFilip card : this.hiddenCardDeck) {
            assert (card.getLevel() == CardLevel.LEARNER);
        }

        this.upcomingCardDeck = null;
        this.playerPositions = new HashMap<>();
    }

    /**
     * FlightBoard constructor of level 2.
     * Sets the number of cells to 24.
     * Sets the timer to a new Timer.
     * Sets the peekable card decks to the passed value.
     * Sets the hidden card deck to the passed value.
     * Sets the player positions map to an empty HashMap.
     * Sets every other member to {@code null}.
     *
     * @param hiddenCardDeck the deck of cards to be used later in the
     *                       flight phase
     * @param peekableCardDecks the list of peekable card decks
     * @throws AssertionError if something is wrong with passed decks:
     *                        peekableCardDecks size is not 3, peekableCardDecks elements size is not 4...
     */
    public FlightBoard(CardDeck hiddenCardDeck, List<CardDeck> peekableCardDecks) {
        this.cellNumber = 24;
        this.timer = new Timer();

        assert (3 == peekableCardDecks.size());
        for (CardDeck cardDeck : peekableCardDecks) {
            assert (4 == cardDeck.peekCards().size());
        }
        this.peekableCardDecks = peekableCardDecks;

        this.hiddenCardDeck = hiddenCardDeck;
        assert (4 == this.hiddenCardDeck.peekCards().size());

        this.upcomingCardDeck = null;
        this.playerPositions = new HashMap<>();
    }

    public final int getCellNumber() {
        return cellNumber;
    }

    public final Timer getTimer() {
        return timer;
    }

    public CardDeck getPeekableCardDeck(int index) throws IndexOutOfBoundsException {
        return peekableCardDecks.get(index);
    }

    public CardDeck getHiddenCardDeck() {
        return hiddenCardDeck;
    }

    public CardDeck getUpcomingCardDeck() {
        return upcomingCardDeck;
    }

    public boolean setUpcomingCardDeck() {
        if (timer == null || !(timer.getPhase() == Timer.Phase.LAST_PHASE && timer.getTimeLeft() == 0.0f)) {
            return false;
        }
        List<AdventureCardFilip> cards = new ArrayList<>();
        upcomingCardDeck = new CardDeck();
        // Aggiungi le carte dei peekableCardDecks
        for (CardDeck deck : peekableCardDecks) {
            while (!deck.peekCards().isEmpty()) {
                upcomingCardDeck.pushCard(deck.popCard());
            }
        }

        // Aggiungi le carte del hiddenCardDeck
        while (!hiddenCardDeck.peekCards().isEmpty()) {
            upcomingCardDeck.pushCard(hiddenCardDeck.popCard());
        }

        peekableCardDecks = null;
        hiddenCardDeck = null;

        this.timer = null;

        return true;
    }

    /**
     * Returns the position of the specified player, or {@code null}
     * if no position was found for such player.
     *
     * @param player the player whose associated position is to be returned
     * @return the position to which the specified player is mapped, or
     * {@code null} if this map contains no position for such player
     * @throws NullPointerException if the specified key is null
     */
    public int getPosition(Player player) throws NullPointerException {
        if (player == null) {
            throw new NullPointerException("player is null");
        }
        return playerPositions.get(player);
    }

    public FlightBoard(List<Player> players, CardDeck deck, MatchLevel level) {
        this.cellNumber = (level == MatchLevel.TRIAL) ? 18 : 24;
        this.timer = (level == MatchLevel.LEVEL2) ? new Timer() : null;
        this.hiddenCardDeck = deck;
        this.peekableCardDecks = (level == MatchLevel.LEVEL2) ? new ArrayList<>() : null;
        this.upcomingCardDeck = null;
        this.playerPositions = new HashMap<>();
        for (Player p : players) {
            playerPositions.put(p, 0);
        }
    }


    public void updatePosition(Player player, int position) {
        playerPositions.put(player, position);
    }


    /**
     * Sets the starting positions of the players.
     * @param player
     * @param position
     * @throws InvalidMethodParameters
     */
    public void setStartingPositions(Player player, int position) throws InvalidMethodParameters {
        if(this.getCellNumber()==18){
            if(position==4){playerPositions.put(player, 0);}
            else if (position==3) {playerPositions.put(player, 1);}
            else if (position==2) {playerPositions.put(player, 2);}
            else if (position==1) {playerPositions.put(player, 4);}
            else{throw new InvalidMethodParameters("Invalid position: " + position+"Should be between 1 and 4");}


        } else if (this.getCellNumber() == 24) {

            if(position==4){playerPositions.put(player, 0);}
            else if (position==3) {playerPositions.put(player, 1);}
            else if (position==2) {playerPositions.put(player, 3);}
            else if (position==1) {playerPositions.put(player, 5);}
            else{throw new InvalidMethodParameters("Invalid position: " + position+"Should be between 1 and 4");}


        } else {
            throw new IllegalStateException("Invalid cell number: " + this.getCellNumber());

        }

    }


    /**
     * Moves the player by the specified number of days.
     * If the number of days is negative, the player moves backwards.
     * If the number of days is positive, the player moves forwards.
     *
     * @param player the player to move
     * @param deltaDays the number of days to move
     * @throws InvalidMethodParameters if the player is not found in the flight board
     */
    public void deltaFlightDays(Player player, int deltaDays) throws InvalidMethodParameters {

        /*
        The main idea is that recoursively, the position of the player is updated,
        the number of players between the current position and the new position is counted, and the funzion is called again
         */
        if (!playerPositions.containsKey(player)) {
            throw new InvalidMethodParameters("Player not found in flight board");
        }


        int currentPosition = playerPositions.get(player);
        int newPosition = (currentPosition + deltaDays) % cellNumber;
        int new_delta=0;



        Collection<Integer> allPositions=playerPositions.values();
        if (deltaDays > 0) {

            if(newPosition<currentPosition){
                //this means that the player has passed the end of the board,
                //so we don't need to count the players that are between the current position and the new position
                //instead we need to count the players between the start of the table and the new position
                //and the players between the current position and the end of the table
                for (Integer position : allPositions) {
                    if (position <= newPosition || position > currentPosition) {
                        new_delta++;
                    }
                }
            }else{
                for (Integer position : allPositions) {
                    if (position <= newPosition && position > currentPosition) {
                        new_delta++;
                    }
                }
            }
            playerPositions.put(player, newPosition);
            deltaFlightDays(player, new_delta);
        }else if(deltaDays<0){

            if(newPosition>currentPosition){
                for (Integer position : allPositions) {
                    if (position >= newPosition || position < currentPosition) {
                        new_delta++;
                    }
                }
            }else{
                for (Integer position : allPositions) {
                    if (position >= newPosition && position < currentPosition) {
                        new_delta++;
                    }
                }
            }
            playerPositions.put(player, newPosition);
            deltaFlightDays(player, -new_delta);
        }

    }



    public Player[] getTurnOrder() {
        return playerPositions.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toArray(Player[]::new);
    }


}
