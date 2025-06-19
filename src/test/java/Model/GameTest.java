package Model;

import Controller.Enums.MatchLevel;
import Model.Board.FlightBoard;
import Model.Ship.Components.SpaceshipComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testConstructor() {
        Game game = new Game(MatchLevel.TRIAL);
        
        assertNotNull(game.getTiles());
        assertNotNull(game.getFlightBoard());
        assertEquals(MatchLevel.TRIAL, game.getLevel());
        assertTrue(game.getPlayers().isEmpty());
        assertFalse(game.isError());
    }
    
    @Test
    public void testAddPlayer() {
        Game game = new Game(MatchLevel.TRIAL);
        
        game.addPlayer("Player1");
        game.addPlayer("Player2");
        
        assertEquals(2, game.getPlayers().size());
        assertEquals("Player1", game.getPlayers().get(0).getName());
        assertEquals("Player2", game.getPlayers().get(1).getName());
    }
    
    @Test
    public void testRemovePlayer() {
        Game game = new Game(MatchLevel.TRIAL);
        
        game.addPlayer("Player1");
        game.addPlayer("Player2");
        
        assertEquals(2, game.getPlayers().size());
        
        game.removePlayer("Player1");
        
        assertEquals(1, game.getPlayers().size());
        assertEquals("Player2", game.getPlayers().get(0).getName());
    }
    
    @Test
    public void testGetPlayer() {
        Game game = new Game(MatchLevel.TRIAL);
        
        game.addPlayer("Player1");
        game.addPlayer("Player2");
        
        Player player1 = game.getPlayer("Player1");
        assertNotNull(player1);
        assertEquals("Player1", player1.getName());
        
        Player player3 = game.getPlayer("Player3");
        assertNull(player3);
    }
    
    @Test
    public void testRollDice() {
        Game game = new Game(MatchLevel.TRIAL);
        
        // Roll the dice 100 times and check that the result is always between 1 and 6
        for (int i = 0; i < 100; i++) {
            int result = game.rollDice();
            assertTrue(result >= 1 && result <= 6);
        }
    }
    
    @Test
    public void testPickComponent() {
        Game game = new Game(MatchLevel.TRIAL);
        
        // Get the tiles
        SpaceshipComponent[] tiles = game.getTiles();
        
        // Find a non-null tile
        int index = -1;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                index = i;
                break;
            }
        }
        
        // If we found a non-null tile, pick it
        if (index != -1) {
            SpaceshipComponent component = game.pickComponent(index);
            assertNotNull(component);
            assertNull(game.getTiles()[index]);
        }
        
        // Test picking an invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> game.pickComponent(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> game.pickComponent(tiles.length));
    }
    
    @Test
    public void testViewVisibleComponents() {
        Game game = new Game(MatchLevel.TRIAL);
        
        // Initially, there should be no visible components
        assertTrue(game.viewVisibleComponents().isEmpty());
        
        // Make some components visible
        SpaceshipComponent[] tiles = game.getTiles();
        int count = 0;
        for (int i = 0; i < tiles.length && count < 3; i++) {
            if (tiles[i] != null) {
                tiles[i].setVisible();
                count++;
            }
        }
        
        // Now there should be some visible components
        assertEquals(count, game.viewVisibleComponents().size());
    }
    
    @Test
    public void testGetFlightBoard() {
        Game game = new Game(MatchLevel.TRIAL);
        
        FlightBoard board = game.getFlightBoard();
        assertNotNull(board);
        assertEquals(18, board.getCellNumber()); // Trial board has 18 cells
    }
}