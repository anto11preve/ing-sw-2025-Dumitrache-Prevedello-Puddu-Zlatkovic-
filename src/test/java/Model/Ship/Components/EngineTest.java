package Model.Ship.Components;

import Model.Enums.ConnectorType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EngineTest {

    @Test
    void doubleEngine() {
        // Arrange
        Model.Ship.Components.Engine engine = new Model.Ship.Components.Engine(Model.Enums.Card.ENGINE, ConnectorType.UNIVERSAL, ConnectorType.DOUBLE, ConnectorType.SINGLE, ConnectorType.DOUBLE, true);

        // Act
        boolean result = engine.isDoubleEngine();

        // Assert
        System.out.println("Expected: true, Actual: " + result);
        assertTrue(result);
    }
}