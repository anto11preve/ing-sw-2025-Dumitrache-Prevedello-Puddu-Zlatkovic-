package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Coordinates class which represents a position on the ship board.
 * Tests basic functionality like construction, equality, and string representation.
 */
public class CoordinatesTest {

    /**
     * Tests the constructor to ensure coordinates are properly initialized:
     * - The x and y values should be stored correctly
     * - getX() and getY() should return the values passed to the constructor
     */
    @Test
    public void testConstructor() {
        Coordinates coordinates = new Coordinates(3, 4);
        assertEquals(3, coordinates.getI());
        assertEquals(4, coordinates.getJ());
    }
    
    /**
     * Tests the equals method for proper comparison:
     * - Two coordinates with the same x and y values should be equal
     * - Coordinates with different values should not be equal
     * - Comparison with null should return false
     * - Comparison with a different type should return false
     */
    @Test
    public void testEquals() {
        Coordinates coordinates1 = new Coordinates(3, 4);
        Coordinates coordinates2 = new Coordinates(3, 4);
        Coordinates coordinates3 = new Coordinates(5, 6);
        
        assertEquals(coordinates1, coordinates2);
        assertNotEquals(coordinates3, coordinates1);
        assertNotEquals(null, coordinates1);
        assertNotEquals("Not a Coordinates", coordinates1);
    }
    
    /**
     * Tests the hashCode method for consistency with equals:
     * - Two equal coordinates should have the same hash code
     * - This is important for using Coordinates in hash-based collections
     */
    @Test
    public void testHashCode() {
        Coordinates coordinates1 = new Coordinates(3, 4);
        Coordinates coordinates2 = new Coordinates(3, 4);
        
        assertEquals(coordinates1.hashCode(), coordinates2.hashCode());
    }
    
    /**
     * Tests the toString method for proper string representation:
     * - The string should include both x and y values
     * - The format should match the expected pattern
     */
    @Test
    public void testToString() {
        Coordinates coordinates = new Coordinates(3, 4);
        String expected = "Coordinates{x=3, y=4}";
        assertEquals(expected, coordinates.toString());
    }
}