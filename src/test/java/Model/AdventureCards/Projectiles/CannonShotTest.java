package Model.AdventureCards.Projectiles;

import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Enums.Side;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CannonShot class.
 * This class verifies that CannonShot correctly stores its properties
 * inherited from the Projectile abstract class.
 */
public class CannonShotTest {

    @Test
    public void constructor_setsCorrectValues_forSmallShot() {
        CannonShot shot = new CannonShot(false, Side.FRONT);
        assertFalse(shot.isBig(), "Expected shot to be small (isBig == false)");
        assertEquals(Side.FRONT, shot.getSide(), "Expected side to be FRONT");
    }

    @Test
    public void constructor_setsCorrectValues_forBigShot() {
        CannonShot shot = new CannonShot(true, Side.RIGHT);
        assertTrue(shot.isBig(), "Expected shot to be big (isBig == true)");
        assertEquals(Side.RIGHT, shot.getSide(), "Expected side to be RIGHT");
    }
}