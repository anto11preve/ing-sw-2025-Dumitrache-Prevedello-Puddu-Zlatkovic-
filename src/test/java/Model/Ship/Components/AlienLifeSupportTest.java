package Model.Ship.Components;

import org.junit.jupiter.api.Test;
import Model.Enums.ConnectorType;
import Model.Enums.Card;

import static org.junit.jupiter.api.Assertions.*;
class AlienLifeSupportTest {

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