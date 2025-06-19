package Model.Ship.Components;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Ship.Components.BatteryCompartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BatteryCompartment class.
 * These tests verify capacity, battery consumption, and limits.
 */
public class BatteryCompartmentTest {

    private BatteryCompartment battery;

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

    @Test
    public void testInitialCapacityAndCharge() {
        // Test that the battery is initialized with the correct capacity and battery count
        assertEquals(2, battery.getCapacity(), "Capacity should be 2");
        assertEquals(2, battery.getBatteries(), "Initial battery count should be 2");
    }

    @Test
    public void testSetAndGetBatteries() {
        // Test manually updating the battery count
        battery.setBatteries(1);
        assertEquals(1, battery.getBatteries(), "Battery count should be updated to 1");
    }

    @Test
    public void testRemoveBatterySuccessfully() {
        // Remove a battery and check the remaining count
        battery.removeBattery();
        assertEquals(1, battery.getBatteries(), "Battery count should decrease by 1");
    }

    @Test
    public void testRemoveBatteryToZero() {
        // Remove all batteries
        battery.removeBattery();
        battery.removeBattery();
        assertEquals(0, battery.getBatteries(), "Battery count should be 0 after two removals");
    }

    @Test
    public void testRemoveBatteryThrowsExceptionWhenEmpty() {
        // Removing beyond zero should throw exception
        battery.removeBattery();
        battery.removeBattery();
        assertThrows(IllegalStateException.class, () -> battery.removeBattery(),
                "Should throw when trying to remove battery with none left");
    }
}
