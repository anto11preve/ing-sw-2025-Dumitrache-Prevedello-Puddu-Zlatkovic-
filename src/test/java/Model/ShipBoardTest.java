package Model;

import Model.ShipBoard;
import Model.SpaceshipComponent;
import Model.ConnectorType;
import Model.Card;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShipBoardTest {

    public static class TestComponent extends SpaceshipComponent {
        public TestComponent() {
            super(new Card(), ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE);
        }
    }

    @Test
    void testEmptyShipIsValid() {
        ShipBoard board = new ShipBoard();
        assertTrue(board.checkIntegrity(), "Una nave vuota dovrebbe essere considerata integra");
    }

    @Test
    void testConnectedComponents() {
        ShipBoard board = new ShipBoard();
        board.addComponent(new TestComponent(), 2, 2);
        board.addComponent(new TestComponent(), 2, 3);
        board.addComponent(new TestComponent(), 2, 4);

        assertTrue(board.checkIntegrity(), "Componenti adiacenti dovrebbero essere considerate connesse");
    }

    @Test
    void testDisconnectedComponents() {
        ShipBoard board = new ShipBoard();
        board.addComponent(new TestComponent(), 0, 0);
        board.addComponent(new TestComponent(), 4, 6);

        assertFalse(board.checkIntegrity(), "Componenti isolate non devono passare l'integrità");
    }

    @Test
    void testAddAndGetComponent() {
        ShipBoard board = new ShipBoard();
        SpaceshipComponent c = new TestComponent();
        assertTrue(board.addComponent(c, 1, 1));
        assertEquals(c, board.getComponent(1, 1));
    }

    @Test
    void testAddComponentOutOfBounds() {
        ShipBoard board = new ShipBoard();
        assertFalse(board.addComponent(new TestComponent(), -1, 0));
        assertFalse(board.addComponent(new TestComponent(), 5, 0));
        assertFalse(board.addComponent(new TestComponent(), 0, 7));
    }

    @Test
    void testGetComponentOutOfBounds() {
        ShipBoard board = new ShipBoard();
        assertNull(board.getComponent(-1, -1));
        assertNull(board.getComponent(5, 0));
    }

    @Test
    void testGetShipBoundaries() {
        ShipBoard board = new ShipBoard();
        board.addComponent(new TestComponent(), 1, 2);
        board.addComponent(new TestComponent(), 2, 3);
        board.addComponent(new TestComponent(), 3, 4);

        int[] expected = {1, 3, 2, 4};
        assertArrayEquals(expected, board.getShipBoundaries());
    }

    @Test
    void testPrintOccupiedMatrix() {
        ShipBoard board = new ShipBoard();
        board.addComponent(new TestComponent(), 2, 2);
        board.addComponent(new TestComponent(), 2, 3);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        board.printOccupiedMatrix();

        String output = out.toString();
        assertTrue(output.contains("[X][X]"), "L'output dovrebbe contenere i moduli occupati");
        System.setOut(System.out); // resetta l'output
    }

    @Test
    void testReserveAndRemoveComponent() {
        ShipBoard board = new ShipBoard();
        SpaceshipComponent c = new TestComponent();
        board.reserveComponent(c);
        List<SpaceshipComponent> reserved = board.getReservedComponents();
        assertTrue(reserved.contains(c));

        board.removeReservedComponent(c);
        assertFalse(reserved.contains(c));
    }
}
