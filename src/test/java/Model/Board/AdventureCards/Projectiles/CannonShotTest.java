package Model.Board.AdventureCards.Projectiles;

import Model.Enums.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CannonShotTest {

    @Test
    public void testConstructor() {
        CannonShot shot = new CannonShot(true, Side.FRONT);
        assertTrue(shot.isBig());
        assertEquals(Side.FRONT, shot.getSide());
    }
    
    @Test
    public void testSingleShot() {
        CannonShot shot = new CannonShot(false, Side.LEFT);
        assertFalse(shot.isBig());
        assertEquals(Side.LEFT, shot.getSide());
    }
    
    @Test
    public void testAllSides() {
        CannonShot front = new CannonShot(false, Side.FRONT);
        assertEquals(Side.FRONT, front.getSide());
        
        CannonShot left = new CannonShot(false, Side.LEFT);
        assertEquals(Side.LEFT, left.getSide());
        
        CannonShot right = new CannonShot(false, Side.RIGHT);
        assertEquals(Side.RIGHT, right.getSide());
        
        CannonShot rear = new CannonShot(false, Side.REAR);
        assertEquals(Side.REAR, rear.getSide());
    }
}