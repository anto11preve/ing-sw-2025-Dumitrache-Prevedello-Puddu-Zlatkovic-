package Model.Board;

import Controller.Enums.MatchLevel;
import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.AdventureCards.OpenSpace;
import Model.Enums.CardLevel;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightBoardTest {

    @Test
    public void testConstructorWithMatchLevel() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        
        List<AdventureCardFilip> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new OpenSpace(i + 1, CardLevel.LEARNER));
        }
        CardDeck deck = new CardDeck(cards);
        
        FlightBoard trialBoard = new FlightBoard(players, deck, MatchLevel.TRIAL);
        assertEquals(18, trialBoard.getCellNumber());
        assertNull(trialBoard.getTimer());
        
        FlightBoard level2Board = new FlightBoard(players, deck, MatchLevel.LEVEL2);
        assertEquals(24, level2Board.getCellNumber());
        assertNotNull(level2Board.getTimer());
    }
    
    @Test
    public void testUpdatePosition() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        players.add(player1);
        players.add(player2);
        
        List<AdventureCardFilip> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new OpenSpace(i + 1, CardLevel.LEARNER));
        }
        CardDeck deck = new CardDeck(cards);
        
        FlightBoard board = new FlightBoard(players, deck, MatchLevel.TRIAL);
        
        // Initial positions should be 0
        assertEquals(0, board.getPosition(player1));
        assertEquals(0, board.getPosition(player2));
        
        // Update positions
        board.updatePosition(player1, 5);
        board.updatePosition(player2, 10);
        
        assertEquals(5, board.getPosition(player1));
        assertEquals(10, board.getPosition(player2));
    }
    
    @Test
    public void testSetStartingPositions() throws InvalidMethodParameters {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        players.add(player1);
        players.add(player2);
        
        List<AdventureCardFilip> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new OpenSpace(i + 1, CardLevel.LEARNER));
        }
        CardDeck deck = new CardDeck(cards);
        
        FlightBoard trialBoard = new FlightBoard(players, deck, MatchLevel.TRIAL);
        
        // Set starting positions
        trialBoard.setStartingPositions(player1, 1); // Position 4
        trialBoard.setStartingPositions(player2, 2); // Position 2
        
        assertEquals(4, trialBoard.getPosition(player1));
        assertEquals(2, trialBoard.getPosition(player2));
        
        // Test invalid position
        assertThrows(InvalidMethodParameters.class, () -> trialBoard.setStartingPositions(player1, 5));
    }
    
    @Test
    public void testGetTurnOrder() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        
        List<AdventureCardFilip> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new OpenSpace(i + 1, CardLevel.LEARNER));
        }
        CardDeck deck = new CardDeck(cards);
        
        FlightBoard board = new FlightBoard(players, deck, MatchLevel.TRIAL);
        
        // Set positions
        board.updatePosition(player1, 10);
        board.updatePosition(player2, 5);
        board.updatePosition(player3, 15);
        
        // Get turn order
        Player[] turnOrder = board.getTurnOrder();
        
        // Order should be player2 (5), player1 (10), player3 (15)
        assertEquals(player2, turnOrder[0]);
        assertEquals(player1, turnOrder[1]);
        assertEquals(player3, turnOrder[2]);
    }
    
    @Test
    public void testRemovePlayingPlayer() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        players.add(player1);
        players.add(player2);
        
        List<AdventureCardFilip> cards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            cards.add(new OpenSpace(i + 1, CardLevel.LEARNER));
        }
        CardDeck deck = new CardDeck(cards);
        
        FlightBoard board = new FlightBoard(players, deck, MatchLevel.TRIAL);
        
        // Remove player1
        board.removePlayingPlayer(player1);
        
        // player1 should be in finished flight players
        assertTrue(board.getFinishedFlightPlayers().contains(player1));
        
        // Trying to get position of player1 should throw exception
        assertThrows(NullPointerException.class, () -> board.getPosition(player1));
        
        // Trying to remove a player not in the board should throw exception
        Player player3 = new Player("Player3");
        assertThrows(IllegalArgumentException.class, () -> board.removePlayingPlayer(player3));
    }
}