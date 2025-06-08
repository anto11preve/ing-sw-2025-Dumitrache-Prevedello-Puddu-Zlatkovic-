package Model;

import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.ShipBoard;
import Model.Ship.Coordinates;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Player class, which manages player state and ship.
 */
public class PlayerTest {

    @Test
    public void testShipBoardCanBeReplaced() {
        Player player = new Player("Dana");
        ShipBoard newBoard = new ShipBoard(4, 6); // Custom dimensions

        // Replace ship board
        player.setShipBoard(newBoard);

        assertEquals(newBoard, player.getShipBoard(), "ShipBoard should be replaced with new instance");
    }

    @Test
    public void testComponentPlacementOnShipBoard() {
        Player player = new Player("Eve");

        // Create a cabin with dummy valid values from enums
        SpaceshipComponent cabin = new Cabin(
                Card.CABIN,
                ConnectorType.NONE,
                ConnectorType.NONE,
                ConnectorType.NONE,
                ConnectorType.NONE,
                Crewmates.EMPTY
        );

        Coordinates pos = new Coordinates(2, 3); // Valid position on the ship grid

        // Try placing the component on the board
        boolean success = player.getShipBoard().placeComponent(cabin, pos);

        assertTrue(success, "Component should be placed successfully");
        assertEquals(cabin, player.getShipBoard().getComponentAt(pos), "Component should be placed at correct position");
    }
}