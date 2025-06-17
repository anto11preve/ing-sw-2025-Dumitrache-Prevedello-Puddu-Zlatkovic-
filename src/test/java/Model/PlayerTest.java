package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testConstructor() {
        Player player = new Player("TestPlayer");
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getCredits());
    }
    
    @Test
    public void testAddCredits() {
        Player player = new Player("TestPlayer");
        
        player.deltaCredits(5);
        assertEquals(5, player.getCredits());
        
        player.deltaCredits(3);
        assertEquals(8, player.getCredits());
    }
    
    @Test
    public void testRemoveCredits() {
        Player player = new Player("TestPlayer");
        
        player.deltaCredits(10);
        player.deltaCredits(-4);
        assertEquals(6, player.getCredits());
        
        player.deltaCredits(-6);
        assertEquals(0, player.getCredits());
    }
    
    @Test
    public void testRemoveMoreCreditsThanAvailable() {
        Player player = new Player("TestPlayer");
        
        player.deltaCredits(5);
        player.deltaCredits(-10);
        // Should not go below zero
        assertEquals(0, player.getCredits());
    }
    
    @Test
    public void testEquals() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player1");
        Player player3 = new Player("Player2");
        
        assertEquals(player1, player2);
        assertNotEquals(player1, player3);
        assertNotEquals(player1, null);
        assertNotEquals(player1, "Not a Player");
    }
    
    @Test
    public void testHashCode() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player1");
        
        assertEquals(player1.hashCode(), player2.hashCode());
    }
}