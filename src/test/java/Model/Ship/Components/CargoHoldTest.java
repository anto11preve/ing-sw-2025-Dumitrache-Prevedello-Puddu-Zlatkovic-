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
        // Initialize a special CargoHold with capacity of 3
        cargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, true);
    }

    @Test
    void testCapacity() {
        // Ensure the capacity is correctly reported
        assertEquals(3, cargoHold.getCapacity());
    }

    @Test
    void testAddGoodSuccess() {
        // Add valid goods to the cargo hold
        assertTrue(cargoHold.addGood(Good.BLUE));
        assertTrue(cargoHold.addGood(Good.GREEN));
    }

    @Test
    void testAddGoodFailureDueToCapacity() {
        // Fill up all slots and ensure an additional good cannot be added
        cargoHold.addGood(Good.BLUE);
        cargoHold.addGood(Good.GREEN);
        cargoHold.addGood(Good.YELLOW);
        assertFalse(cargoHold.addGood(Good.RED));
    }

    @Test
    void testAddRedGoodToNonSpecialCargoHold() {
        // Attempt to add a red good to a non-special cargo hold
        CargoHold normalCargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, false);
        assertFalse(normalCargoHold.addGood(Good.RED));
    }

    @Test
    void testRemoveGood() {
        // Add and then remove a good, checking if the slot becomes null
        cargoHold.addGood(Good.BLUE);
        cargoHold.addGood(Good.GREEN);
        cargoHold.removeGood(0);
        assertNull(cargoHold.getGoods()[0]);
    }

    @Test
    void testRemoveGoodInvalidIndex() {
        // Try removing goods at invalid indexes and ensure no exception or null pointer occurs
        cargoHold.removeGood(-1);
        cargoHold.removeGood(5);
        assertNotNull(cargoHold.getGoods());
    }

    @Test
    void testAddGoodAtSpecificIndex() {
        // Add a good at a specific valid index
        assertTrue(cargoHold.addGoodAt(Good.BLUE, 2));
        assertEquals(Good.BLUE, cargoHold.getGoods()[2]);

        // Try to overwrite the same index which is already occupied
        assertFalse(cargoHold.addGoodAt(Good.GREEN, 2));

        // Test invalid indexes: negative and out of bounds
        assertFalse(cargoHold.addGoodAt(Good.YELLOW, -1));
        assertFalse(cargoHold.addGoodAt(Good.YELLOW, 5));
    }

    @Test
    void testAddRedGoodAtIndexInNonSpecialCargoHold() {
        // Attempt to add a red good at a specific index in a non-special cargo hold
        CargoHold normalCargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, false);
        assertFalse(normalCargoHold.addGoodAt(Good.RED, 0));
    }
}
