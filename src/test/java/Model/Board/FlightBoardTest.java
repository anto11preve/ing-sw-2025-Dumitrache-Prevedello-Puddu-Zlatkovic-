package Model.Board;

import Controller.Enums.MatchLevel;
import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.CardDeck;
import Model.Board.FlightBoard;
import Model.Enums.CardLevel;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the FlightBoard.
 * Verifies construction logic, player position management, and special methods.
 */
public class FlightBoardTest {

    private AdventureCardFilip[] learnerCards;

    @BeforeEach
    public void setup() {
        learnerCards = new AdventureCardFilip[8];
        for (int i = 0; i < 8; i++) {
            learnerCards[i] = createDummyCard(i, CardLevel.LEARNER);
        }
    }

    private AdventureCardFilip createDummyCard(int id, CardLevel level) {
        return new AdventureCardFilip(id, level) {
            @Override
            public String getName() {
                return "DummyCard_" + id;
            }

            @Override
            public String getDescription() {
                return "Dummy description for card " + id;
            }
        };
    }

    @Test
    public void testLearnerConstructorSetsCorrectValues() {

        FlightBoard board = new FlightBoard(new CardDeck(learnerCards));
        assertEquals(18, board.getCellNumber(), "Learner board should have 18 cells");
        assertNotNull(board.getHiddenCardDeck(), "Hidden card deck should not be null");
        assertNull(board.getTimer(), "Learner board should not have a timer");
    }

    @Test
    public void testFlightBoardWithPlayersInitializesPositions() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Alice"));
        players.add(new Player("Bob"));
        CardDeck hidden = new CardDeck(learnerCards);

        FlightBoard board = new FlightBoard(players, hidden, MatchLevel.TRIAL);
        for (Player p : players) {
            assertEquals(0, board.getPosition(p), "All players should start at position 0");
        }
    }

    @Test
    public void testUpdateAndGetPosition() {
        List<Player> players = new ArrayList<>();
        Player alice = new Player("Alice");
        players.add(alice);
        CardDeck hidden = new CardDeck(learnerCards);

        FlightBoard board = new FlightBoard(players, hidden, MatchLevel.TRIAL);
        board.updatePosition(alice, 5);
        assertEquals(5, board.getPosition(alice), "Position should be updated to 5");
    }

    @Test
    public void testSetStartingPositionsLearner() throws InvalidMethodParameters {
        FlightBoard board = new FlightBoard(new CardDeck(learnerCards));
        Player p = new Player("Carol");
        board.setStartingPositions(p, 2);
        assertEquals(2, board.getPosition(p));
    }

    @Test
    public void testDeltaFlightDaysForwardWrapAround() throws InvalidMethodParameters {
        List<Player> players = new ArrayList<>();
        Player p = new Player("Dave");
        players.add(p);
        CardDeck hidden = new CardDeck(learnerCards);
        FlightBoard board = new FlightBoard(players, hidden, MatchLevel.TRIAL);
        board.updatePosition(p, 17); // Near end
        board.deltaFlightDays(p, 3); // Should wrap and move more due to collisions
        int pos = board.getPosition(p);
        assertTrue(pos >= 0 && pos < 18, "Position should remain within board bounds");
    }
}
