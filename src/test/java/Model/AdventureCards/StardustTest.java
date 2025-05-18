package Model.AdventureCards;


import Model.Ship.ShipBoard;
import Model.Ship.Components.Cannon;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Utils.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StardustTest {

    @Test
    public void testStardustDestroysExposedComponent() {
        ShipBoard board = new ShipBoard();
        Cannon cannon = new Cannon(Card.CANNON, ConnectorType.UNIVERSAL, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE, false);
        board.addComponent(cannon, 2, 2);

        assertTrue(board.getExposedConnectors().containsKey(new Position(2,2)));

        board.resolveStardust();
        assertNull(board.getComponent(2,2), "Component should be destroyed by Stardust");
    }
}
