package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShieldGeneratorTest {

    @Test
    public void testConstructor() {
        ShieldGenerator shield = new ShieldGenerator(Card.SHIELD_GENERATOR, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  Direction.UP);
        
        assertEquals(Card.SHIELD_GENERATOR, shield.getType());
        assertEquals(Direction.UP, shield.getOrientation());
        //assertEquals(Direction.UP, shield.getDirection()); TODO: non capisco cosa sia getDirection(), se l'unico parametro è l'orientamento
    }
//    TODO: test inutili
//    @Test
//    public void testBlocks() {
//        ShieldGenerator shield = new ShieldGenerator(Card.SHIELD_GENERATOR,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  ConnectorType.UNIVERSAL,
//                                  Direction.UP);
//
//        // Shield should block in its facing direction
//        assertTrue(shield.blocks(Direction.UP));
//        assertFalse(shield.blocks(Direction.DOWN));
//        assertFalse(shield.blocks(Direction.LEFT));
//        assertFalse(shield.blocks(Direction.RIGHT));
//
//        // Test after changing direction
//        shield.setDirection(Direction.RIGHT);
//        assertFalse(shield.blocks(Direction.UP));
//        assertFalse(shield.blocks(Direction.DOWN));
//        assertFalse(shield.blocks(Direction.LEFT));
//        assertTrue(shield.blocks(Direction.RIGHT));
//    }
}