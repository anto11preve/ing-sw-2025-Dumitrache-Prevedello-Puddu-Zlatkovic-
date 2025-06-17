package Model.Board.AdventureCards.Penalties;

import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Enums.Side;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CannonShotPenaltyTest {

    @Test
    public void testConstructor() {
        List<CannonShot> shots = new ArrayList<>();
        shots.add(new CannonShot(false, Side.FRONT));
        shots.add(new CannonShot(true, Side.LEFT));
        
        CannonShotPenalty penalty = new CannonShotPenalty(shots);
        
        // Count shots and check their properties
        int count = 0;
        CannonShot firstShot = null;
        CannonShot secondShot = null;
        
        for (CannonShot shot : penalty) {
            count++;
            if (count == 1) {
                firstShot = shot;
            } else if (count == 2) {
                secondShot = shot;
            }
        }
        
        assertEquals(2, count);
        assertNotNull(firstShot);
        assertNotNull(secondShot);
        assertFalse(firstShot.isBig());
        assertEquals(Side.FRONT, firstShot.getSide());
        assertTrue(secondShot.isBig());
        assertEquals(Side.LEFT, secondShot.getSide());
    }
    
    @Test
    public void testEmptyList() {
        CannonShotPenalty penalty = new CannonShotPenalty(new ArrayList<>());
        
        // Check if the iterator returns any elements
        int count = 0;
        for (CannonShot shot : penalty) {
            count++;
        }
        
        assertEquals(0, count);
    }
    
    @Test
    public void testNullList() {
        assertThrows(NullPointerException.class, () -> new CannonShotPenalty(null));
    }
}