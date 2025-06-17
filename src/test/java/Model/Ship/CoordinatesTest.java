package Model.Ship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesTest {

    @Test
    public void testConstructor() {
        Coordinates coordinates = new Coordinates(3, 4);
        assertEquals(3, coordinates.getX());
        assertEquals(4, coordinates.getY());
    }
    
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
    
    @Test
    public void testHashCode() {
        Coordinates coordinates1 = new Coordinates(3, 4);
        Coordinates coordinates2 = new Coordinates(3, 4);
        
        assertEquals(coordinates1.hashCode(), coordinates2.hashCode());
    }
    
    @Test
    public void testToString() {
        Coordinates coordinates = new Coordinates(3, 4);
        String expected = "Coordinates{x=3, y=4}";
        assertEquals(expected, coordinates.toString());
    }
}