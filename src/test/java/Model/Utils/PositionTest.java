package Model.Utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Position class.
 * Position represents a cell coordinate on the ship board grid.
 */
public class PositionTest {

    @Test
    public void testConstructorAndGetters() {
        Position pos = new Position(2, 5);
        assertEquals(2, pos.getX(), "getX() should return the correct row");
        assertEquals(5, pos.getY(), "getY() should return the correct column");
    }

    @Test
    public void testEqualsAndHashCode() {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(3, 4);
        Position p3 = new Position(1, 2);

        assertEquals(p1, p2, "Positions with same coordinates should be equal");
        assertNotEquals(p1, p3, "Positions with different coordinates should not be equal");
        assertEquals(p1.hashCode(), p2.hashCode(), "Equal positions should have same hash code");
    }

    @Test
    public void testNotEqualsWithNullOrDifferentClass() {
        Position pos = new Position(1, 1);
        assertNotEquals(null, pos, "Position should not be equal to null");
        assertNotEquals("Some string", pos, "Position should not be equal to object of another class");
    }
}
