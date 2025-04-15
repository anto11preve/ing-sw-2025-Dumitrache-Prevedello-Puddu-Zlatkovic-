package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    class CabinTest {

        @Test
        void testGetCanContainBrown() {
            Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
            cabin.setCanContainBrown(true);
            assertTrue(cabin.getCanContainBrown());
        }

        @Test
        void testSetCanContainBrown() {
            Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
            cabin.setCanContainBrown(true);
            assertTrue(cabin.getCanContainBrown());
            cabin.setCanContainBrown(false);
            assertFalse(cabin.getCanContainBrown());
        }

        @Test
        void testSetCanContainPurple() {
            Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
            cabin.setCanContainPurple(true);
            assertTrue(cabin.getCanContainPurple());
            cabin.setCanContainPurple(false);
            assertFalse(cabin.getCanContainPurple());
        }

        @Test
        void testSetOccupants() {
            Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
            cabin.setOccupants(Crewmates.BROWN_ALIEN);
            assertEquals(Crewmates.BROWN_ALIEN, cabin.getOccupants());
        }

        @Test
        void testGetOccupants() {
            Cabin cabin = new Cabin(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.UNIVERSAL, Crewmates.EMPTY);
            assertEquals(Crewmates.EMPTY, cabin.getOccupants());
            cabin.setOccupants(Crewmates.BROWN_ALIEN);
            assertEquals(Crewmates.BROWN_ALIEN, cabin.getOccupants());
        }
    }
