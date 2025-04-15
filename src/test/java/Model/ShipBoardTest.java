package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipBoardTest {

    static class DummyComponent extends SpaceshipComponent {
        public DummyComponent(Card card, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right) {
            super(card, front, rear, left, right);
        }
    }

    private SpaceshipComponent comp(Card type, ConnectorType f, ConnectorType r, ConnectorType l, ConnectorType rt) {
        return new DummyComponent(type, f, r, l, rt);
    }

    @Test
    void testValidEngine() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.ENGINE, ConnectorType.NONE, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE), 2, 3);
        assertTrue(board.validateShip());
    }

    @Test
    void testBlockedEngineFails() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.ENGINE, ConnectorType.NONE, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE), 2, 3);
        board.addComponent(comp(Card.CABIN, ConnectorType.STANDARD, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE), 3, 3);
        assertFalse(board.validateShip());
    }

    @Test
    void testValidCannon() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.CANNON, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE), 2, 3);
        assertTrue(board.validateShip());
    }

    @Test
    void testBlockedCannonFails() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.CANNON, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE), 2, 3);
        board.addComponent(comp(Card.CABIN, ConnectorType.STANDARD, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE), 1, 3);
        assertFalse(board.validateShip());
    }

    @Test
    void testCabinNoConnectorsFails() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.CABIN, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE), 2, 3);
        assertFalse(board.validateShip());
    }

    @Test
    void testAlienLifeSupportNoCabinFails() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.ALIEN_LIFE_SUPPORT, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 3);
        assertFalse(board.validateShip());
    }

    @Test
    void testAlienLifeSupportWithCabinSucceeds() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.ALIEN_LIFE_SUPPORT, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 3);
        board.addComponent(comp(Card.CABIN, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 4);
        assertTrue(board.validateShip());
    }

    @Test
    void testShieldGeneratorValidPosition() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.SHIELD_GENERATOR, ConnectorType.STANDARD, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE), 2, 3);
        assertTrue(board.validateShip());
    }

    @Test
    void testShieldGeneratorInvalidPosition() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.SHIELD_GENERATOR, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 3);
        assertFalse(board.validateShip());
    }

    @Test
    void testSpecialCargoWithoutCargoFails() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.SPECIAL_CARGO_HOLD, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 3);
        assertFalse(board.validateShip());
    }

    @Test
    void testSpecialCargoAdjacentToCargoSucceeds() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.SPECIAL_CARGO_HOLD, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 3);
        board.addComponent(comp(Card.CARGO_HOLD, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 2, 4);
        assertTrue(board.validateShip());
    }

    @Test
    void testEmptyShipIsValid() {
        ShipBoard board = new ShipBoard();
        assertTrue(board.checkIntegrity(), "Una nave vuota dovrebbe essere considerata integra");
    }

    @Test
    void testDisconnectedComponents() {
        ShipBoard board = new ShipBoard();
        board.addComponent(comp(Card.CABIN, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 0, 0);
        board.addComponent(comp(Card.CABIN, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL, ConnectorType.UNIVERSAL), 4, 6);
        assertFalse(board.checkIntegrity(), "Componenti isolate non devono passare l'integrità");
    }
}
