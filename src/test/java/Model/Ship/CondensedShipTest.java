package Model.Ship;

import org.junit.jupiter.api.Test;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.CargoHold;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class CondensedShipTest {
    private CondensedShip ship;

    @BeforeEach
    void setUp() {
        ship = new CondensedShip();
    }

    @Test
    void testAddAndRemoveCabin() {
        Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, Crewmates.EMPTY);
        ship.addCabin(cabin);
        assertTrue(ship.getCabins().contains(cabin));

        ship.removeCabin(cabin);
        assertFalse(ship.getCabins().contains(cabin));
    }

    @Test
    void testAddAndRemoveBatteryCompartment() {
        BatteryCompartment battery = new BatteryCompartment(Card.BATTERY_COMPARTMENT, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 2);
        ship.addBatteryCompartment(battery);
        assertTrue(ship.getBatteryCompartments().contains(battery));

        ship.removeBatteryCompartment(battery);
        assertFalse(ship.getBatteryCompartments().contains(battery));
    }

    @Test
    void testAddAndRemoveCargoHold() {
        CargoHold cargoHold = new CargoHold(Card.CARGO_HOLD, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, 3, true);
        ship.addCargoHold(cargoHold);
        assertTrue(ship.getCargoHolds().contains(cargoHold));

        ship.removeCargoHold(cargoHold);
        assertFalse(ship.getCargoHolds().contains(cargoHold));
    }

    @Test
    void testAddAndRemoveCannon() {
        Cannon cannon = new Cannon(Card.CANNON, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, ConnectorType.SINGLE, false);
        ship.addCannon(cannon);
        assertTrue(ship.getCannons().contains(cannon));

        ship.removeCannon(cannon);
        assertFalse(ship.getCannons().contains(cannon));
    }

    @Test
    void testSetEngines() {
        final int singles = 2, doubles = 3;

        ship.getEngines().setSingleEngines(singles);
        assertEquals(singles, ship.getEngines().getSingleEngines());
        ship.getEngines().setDoubleEngines(doubles);
        assertEquals(doubles, ship.getEngines().getDoubleEngines());
    }

    @Test
    void testSetShields() {
        final int northShields = 1, eastShields = 2, southShields = 3, westShields = 4;
        ship.getShields().setNorthShields(northShields);
        assertEquals(northShields, ship.getShields().getNorthShields());
        ship.getShields().setEastShields(eastShields);
        assertEquals(eastShields, ship.getShields().getEastShields());
        ship.getShields().setSouthShields(southShields);
        assertEquals(southShields, ship.getShields().getSouthShields());
        ship.getShields().setWestShields(westShields);
        assertEquals(westShields, ship.getShields().getWestShields());
    }
}
