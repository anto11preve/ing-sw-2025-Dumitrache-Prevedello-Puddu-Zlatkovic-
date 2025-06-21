package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CannonTest {

    @Test
    public void testConstructor() {
        Cannon cannon = new Cannon(Card.CANNON, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  false);
        
        assertEquals(Card.CANNON, cannon.getType());
        assertEquals(Direction.UP, cannon.getOrientation());
        assertFalse(cannon.isDouble());
    }
//    TODO: test inutili
//    @Test
//    public void testGetFirepower() {
//        Cannon cannon = new Cannon(Card.CANNON,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  false);
//
//        // Cannon should have firepower in its facing direction
//        assertEquals(1, cannon.getFirepower(Direction.UP));
//        assertEquals(0, cannon.getFirepower(Direction.DOWN));
//        assertEquals(0, cannon.getFirepower(Direction.LEFT));
//        assertEquals(0, cannon.getFirepower(Direction.RIGHT));
//
//        // Test after rotation
//        cannon.rotate(); // Now facing RIGHT
//        assertEquals(0, cannon.getFirepower(Direction.UP));
//        assertEquals(0, cannon.getFirepower(Direction.DOWN));
//        assertEquals(0, cannon.getFirepower(Direction.LEFT));
//        assertEquals(1, cannon.getFirepower(Direction.RIGHT));
//    }
//
//    @Test
//    public void testDoubleCannon() {
//        Cannon cannon = new Cannon(Card.DOUBLE_CANNON,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  true);
//
//        // Double cannon should have double firepower when activated
//        cannon.activate();
//        assertEquals(2, cannon.getFirepower(Direction.UP));
//        assertEquals(0, cannon.getFirepower(Direction.DOWN));
//
//        // Test after rotation
//        cannon.rotate(); // Now facing RIGHT
//        assertEquals(0, cannon.getFirepower(Direction.UP));
//        assertEquals(2, cannon.getFirepower(Direction.RIGHT));
//
//        // Test deactivation
//        cannon.deactivate();
//        assertEquals(0, cannon.getFirepower(Direction.RIGHT));
//    }
}