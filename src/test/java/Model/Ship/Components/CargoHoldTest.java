package Model.Ship.Components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Good;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CargoHold component which stores goods on the ship.
 * Tests cargo capacity, adding/removing goods, and special cargo functionality.
 */
public class CargoHoldTest {
    private CargoHold cargoHold;

    /**
     * Sets up a special cargo hold with capacity of 3 before each test.
     * This cargo hold can store any type of good including red goods.
     */
    @BeforeEach
    void setUp() {
        // Initialize a special CargoHold with capacity of 3
        cargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, true);
    }

    /**
     * Tests that the cargo hold correctly reports its capacity.
     */
    @Test
    void testCapacity() {
        // Ensure the capacity is correctly reported
        assertEquals(3, cargoHold.getCapacity());
    }

    /**
     * Tests successfully adding goods to the cargo hold:
     * - Goods should be added when there is available capacity
     * - The method should return true to indicate success
     */
    @Test
    void testAddGoodSuccess() {
        // Add valid goods to the cargo hold
        assertTrue(cargoHold.addGood(Good.BLUE));
        assertTrue(cargoHold.addGood(Good.GREEN));
    }

    /**
     * Tests adding goods when the cargo hold is full:
     * - Adding beyond capacity should fail
     * - The method should return false when capacity is reached
     */
    @Test
    void testAddGoodFailureDueToCapacity() {
        // Fill up all slots and ensure an additional good cannot be added
        cargoHold.addGood(Good.BLUE);
        cargoHold.addGood(Good.GREEN);
        cargoHold.addGood(Good.YELLOW);
        assertFalse(cargoHold.addGood(Good.RED));
    }

    /**
     * Tests adding red goods to a non-special cargo hold:
     * - Regular cargo holds cannot store red goods
     * - The method should return false when attempting to add red goods
     */
    @Test
    void testAddRedGoodToNonSpecialCargoHold() {
        // Attempt to add a red good to a non-special cargo hold
        CargoHold normalCargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, false);
        assertFalse(normalCargoHold.addGood(Good.RED));
    }

    /**
     * Tests removing goods from the cargo hold:
     * - Removing a good should clear its slot
     * - The slot should become null after removal
     */
    @Test
    void testRemoveGood() {
        // Add and then remove a good, checking if the slot becomes null
        cargoHold.addGood(Good.BLUE);
        cargoHold.addGood(Good.GREEN);
        cargoHold.removeGood(0);
        assertNull(cargoHold.getGoods()[0]);
    }

    /**
     * Tests removing goods with invalid indices:
     * - Removing with invalid indices should not cause errors
     * - The goods array should remain intact
     */
    @Test
    void testRemoveGoodInvalidIndex() {
        // Try removing goods at invalid indexes and ensure no exception or null pointer occurs
        cargoHold.removeGood(-1);
        cargoHold.removeGood(5);
        assertNotNull(cargoHold.getGoods());
    }

    /**
     * Tests adding goods at specific indices:
     * - Adding at a valid empty index should succeed
     * - Adding at an occupied index should fail
     * - Adding at invalid indices should fail
     */
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

    /**
     * Tests adding red goods at specific indices in a non-special cargo hold:
     * - Regular cargo holds cannot store red goods even at specific indices
     * - The method should return false when attempting to add red goods
     */
    @Test
    void testAddRedGoodAtIndexInNonSpecialCargoHold() {
        // Attempt to add a red good at a specific index in a non-special cargo hold
        CargoHold normalCargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, false);
        assertFalse(normalCargoHold.addGoodAt(Good.RED, 0));
    }
}