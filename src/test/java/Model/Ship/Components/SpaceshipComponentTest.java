package Model.Ship.Components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Model.Enums.Card;
import Model.Enums.ConnectorType;

//serve perché SpaceshipComponent è astratta
class TestSpaceshipComponent extends SpaceshipComponent {
    public TestSpaceshipComponent(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right) {
        super(type, front, rear, left, right);
    }
}

public class SpaceshipComponentTest {
    private TestSpaceshipComponent component;

    @BeforeEach
    void setUp() {
        component = new TestSpaceshipComponent(Card.CABIN, ConnectorType.SINGLE, ConnectorType.DOUBLE, ConnectorType.UNIVERSAL, ConnectorType.SINGLE);
    }

    @Test
    void testInitialOrientation() {
        assertEquals(0, component.getOrientation());
    }

    @Test
    void testRotation() {
        component.rotate();
        assertEquals(1, component.getOrientation());
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorType(0));
        assertEquals(ConnectorType.DOUBLE, component.getConnectorType(2));
    }

    @Test
    void testFullRotation() {
        for (int i = 0; i < 4; i++) {
            component.rotate();
        }
        assertEquals(0, component.getOrientation());
    }

    @Test
    void testGetConnectorType() {
        assertEquals(ConnectorType.SINGLE, component.getConnectorType(0));
        assertEquals(ConnectorType.DOUBLE, component.getConnectorType(1));
        assertEquals(ConnectorType.UNIVERSAL, component.getConnectorType(2));
        assertEquals(ConnectorType.SINGLE, component.getConnectorType(3));
    }
}
