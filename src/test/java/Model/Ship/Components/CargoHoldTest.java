package Model.Ship.Components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Good;

import static org.junit.jupiter.api.Assertions.*;


public class CargoHoldTest {
    private CargoHold cargoHold;

    @BeforeEach
    void setUp() {
        cargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, true);
    }

    @Test
    void testCapacity() {
        assertEquals(3, cargoHold.getCapacity());
    }

    @Test
    void testAddGoodSuccess() {
        assertTrue(cargoHold.addGood(Good.BLUE));
        assertTrue(cargoHold.addGood(Good.GREEN));
    }

    @Test
    void testAddGoodFailureDueToCapacity() {
        cargoHold.addGood(Good.BLUE);
        cargoHold.addGood(Good.GREEN);
        cargoHold.addGood(Good.YELLOW);
        assertFalse(cargoHold.addGood(Good.RED));
    }

    @Test
    void testAddRedGoodToNonSpecialCargoHold() {
        CargoHold normalCargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, false);
        assertFalse(normalCargoHold.addGood(Good.RED));
    }

    @Test
    void testRemoveGood() {
        cargoHold.addGood(Good.BLUE);
        cargoHold.addGood(Good.GREEN);
        cargoHold.removeGood(0);
        assertNull(cargoHold.getGoods()[0]);
    }

    @Test
    void testRemoveGoodInvalidIndex() {
        cargoHold.removeGood(-1);
        cargoHold.removeGood(5);
        assertNotNull(cargoHold.getGoods());
    }
}

