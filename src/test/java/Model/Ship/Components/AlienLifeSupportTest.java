package Model.Ship.Components;

import org.junit.jupiter.api.Test;
import Model.Enums.ConnectorType;
import Model.Enums.Card;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the AlienLifeSupport component which supports alien life forms on the ship.
 * Tests alien color assignment and retrieval.
 */
class AlienLifeSupportTest {

    /**
     * Tests that the alien life support correctly stores and returns its alien color:
     * - The color specified in the constructor should be stored
     * - getColor() should return the correct alien color
     * - This is important because different alien colors have different requirements
     */
    @Test
    void getColor() {
        // Arrange
        Model.Enums.AlienColor expectedColor = Model.Enums.AlienColor.BROWN;
        Model.Ship.Components.AlienLifeSupport alienLifeSupport = new Model.Ship.Components.AlienLifeSupport(
                Card.ALIEN_LIFE_SUPPORT,
                ConnectorType.UNIVERSAL,
                ConnectorType.SINGLE,
                ConnectorType.SINGLE,
                ConnectorType.DOUBLE,
                expectedColor
        );

        // Act
        Model.Enums.AlienColor actualColor = alienLifeSupport.getColor();

        // Assert
        System.out.println("Expected Color: " + expectedColor + " Actual Color: " + actualColor);
        assertEquals(expectedColor, actualColor);
    }
}