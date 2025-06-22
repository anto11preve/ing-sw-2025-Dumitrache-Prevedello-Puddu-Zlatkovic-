package Model.Ship.Components;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Ship.Components.BatteryCompartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the BatteryCompartment component which stores and manages batteries.
 * Tests battery capacity, consumption, and limits.
 */
public class BatteryCompartmentTest {

    private BatteryCompartment battery;

    /**
     * Sets up a battery compartment with capacity of 2 before each test.
     * The compartment starts fully charged with 2 batteries.
     */
    @BeforeEach
    public void setUp() {
        // Create a BatteryCompartment with 2 batteries
        battery = new BatteryCompartment(
                Card.BATTERY_COMPARTMENT,
                ConnectorType.SINGLE,
                ConnectorType.SINGLE,
                ConnectorType.NONE,
                ConnectorType.NONE,
                2
        );
    }

    /**
     * Tests that the battery compartment is initialized with the correct capacity and charge:
     * - The capacity should match the constructor parameter
     * - The initial battery count should equal the capacity
     */
    @Test
    public void testInitialCapacityAndCharge() {
        // Test that the battery is initialized with the correct capacity and battery count
        assertEquals(2, battery.getCapacity(), "Capacity should be 2");
        assertEquals(2, battery.getBatteries(), "Initial battery count should be 2");
    }

    /**
     * Tests setting and getting the battery count:
     * - setBatteries() should update the battery count
     * - getBatteries() should return the current count
     */
    @Test
    public void testSetAndGetBatteries() {
        // Test manually updating the battery count
        battery.setBatteries(1);
        assertEquals(1, battery.getBatteries(), "Battery count should be updated to 1");
    }

    /**
     * Tests removing a battery when batteries are available:
     * - removeBattery() should decrease the count by 1
     * - The operation should succeed when batteries are available
     */
    @Test
    public void testRemoveBatterySuccessfully() {
        // Remove a battery and check the remaining count
        battery.removeBattery();
        assertEquals(1, battery.getBatteries(), "Battery count should decrease by 1");
    }

    /**
     * Tests removing all batteries:
     * - Multiple removals should work until zero is reached
     * - The battery count should not go below zero
     */
    @Test
    public void testRemoveBatteryToZero() {
        // Remove all batteries
        battery.removeBattery();
        battery.removeBattery();
        assertEquals(0, battery.getBatteries(), "Battery count should be 0 after two removals");
    }

    /**
     * Tests removing batteries when none are left:
     * - Attempting to remove beyond zero should throw an exception
     * - The exception should be IllegalStateException
     */
    @Test
    public void testRemoveBatteryThrowsExceptionWhenEmpty() {
        // Removing beyond zero should throw exception
        battery.removeBattery();
        battery.removeBattery();
        assertThrows(IllegalStateException.class, () -> battery.removeBattery(),
                "Should throw when trying to remove battery with none left");
    }
}