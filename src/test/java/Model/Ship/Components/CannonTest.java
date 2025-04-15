package Model.Ship.Components;

import org.junit.jupiter.api.Test;
import Model.Enums.Card;
import Model.Enums.ConnectorType;


import static org.junit.jupiter.api.Assertions.*;

class CannonTest {

    @Test
    void doubleCannon() {
        Cannon cannon = new Cannon(Card.CANNON, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, true);
        assertTrue(cannon.doubleCannon());

        Cannon cannon2 = new Cannon(Card.CANNON, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, ConnectorType.DOUBLE, ConnectorType.DOUBLE, false);
        assertFalse(cannon2.doubleCannon());
    }
}