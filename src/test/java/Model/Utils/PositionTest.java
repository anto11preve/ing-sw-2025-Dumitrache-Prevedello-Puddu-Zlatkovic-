package Model.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void testConstructor() {
        Position position = new Position(3, 4);
        assertEquals(3, position.getX());
        assertEquals(4, position.getY());
    }
    
    @Test
    public void testEquals() {
        Position position1 = new Position(3, 4);
        Position position2 = new Position(3, 4);
        Position position3 = new Position(5, 6);
        
        assertEquals(position1, position2);
        assertNotEquals(position1, position3);
        assertNotEquals(position1, null);
        assertNotEquals(position1, "Not a Position");
    }
    
    @Test
    public void testHashCode() {
        Position position1 = new Position(3, 4);
        Position position2 = new Position(3, 4);
        
        assertEquals(position1.hashCode(), position2.hashCode());
    }
    
    @Test
    public void testToString() {
        Position position = new Position(3, 4);
        String expected = "Position{x=3, y=4}";
        assertEquals(expected, position.toString());
    }
}