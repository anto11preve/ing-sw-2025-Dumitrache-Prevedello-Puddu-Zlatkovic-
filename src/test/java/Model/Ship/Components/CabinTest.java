package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Cabin component which houses crew members and aliens.
 * Tests cabin properties like alien compatibility and occupant management.
 */
class CabinTest {

    /**
     * Tests getting the brown alien compatibility flag:
     * - The cabin should correctly report whether it can contain brown aliens
     */
    @Test
    void testGetCanContainBrown() {
        Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
        cabin.setCanContainBrown(true);
        assertTrue(cabin.getCanContainBrown());
    }

    /**
     * Tests setting the brown alien compatibility flag:
     * - The cabin should store the brown alien compatibility setting
     * - The flag can be toggled between true and false
     */
    @Test
    void testSetCanContainBrown() {
        Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
        cabin.setCanContainBrown(true);
        assertTrue(cabin.getCanContainBrown());
        cabin.setCanContainBrown(false);
        assertFalse(cabin.getCanContainBrown());
    }

    /**
     * Tests setting the purple alien compatibility flag:
     * - The cabin should store the purple alien compatibility setting
     * - The flag can be toggled between true and false
     */
    @Test
    void testSetCanContainPurple() {
        Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
        cabin.setCanContainPurple(true);
        assertTrue(cabin.getCanContainPurple());
        cabin.setCanContainPurple(false);
        assertFalse(cabin.getCanContainPurple());
    }

    /**
     * Tests setting the cabin occupants:
     * - The cabin should store the assigned occupants
     * - Different types of occupants (humans, aliens) can be assigned
     */
    @Test
    void testSetOccupants() {
        Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
        cabin.setOccupants(Crewmates.BROWN_ALIEN);
        assertEquals(Crewmates.BROWN_ALIEN, cabin.getOccupants());
    }

    /**
     * Tests getting the cabin occupants:
     * - A new cabin should start with EMPTY occupants
     * - After setting occupants, getOccupants should return the correct value
     */
    @Test
    void testGetOccupants() {
        Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
        assertEquals(Crewmates.EMPTY, cabin.getOccupants());
        cabin.setOccupants(Crewmates.BROWN_ALIEN);
        assertEquals(Crewmates.BROWN_ALIEN, cabin.getOccupants());
    }
}