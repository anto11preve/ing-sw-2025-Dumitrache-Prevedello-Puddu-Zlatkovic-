package Model.Board.AdventureCards.Projectiles;

import Model.Enums.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MeteorTest {

    @Test
    public void testConstructor() {
        Meteor meteor = new Meteor(true, Side.FRONT);
        assertTrue(meteor.isBig());
        assertEquals(Side.FRONT, meteor.getSide());
    }
    
    @Test
    public void testSmallMeteor() {
        Meteor meteor = new Meteor(false, Side.LEFT);
        assertFalse(meteor.isBig());
        assertEquals(Side.LEFT, meteor.getSide());
    }
    
    @Test
    public void testAllSides() {
        Meteor front = new Meteor(false, Side.FRONT);
        assertEquals(Side.FRONT, front.getSide());
        
        Meteor left = new Meteor(false, Side.LEFT);
        assertEquals(Side.LEFT, left.getSide());
        
        Meteor right = new Meteor(false, Side.RIGHT);
        assertEquals(Side.RIGHT, right.getSide());
        
        Meteor rear = new Meteor(false, Side.REAR);
        assertEquals(Side.REAR, rear.getSide());
    }
}