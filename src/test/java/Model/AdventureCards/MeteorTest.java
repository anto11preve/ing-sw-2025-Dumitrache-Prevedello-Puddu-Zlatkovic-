package Model.AdventureCards;

import Model.Exceptions.InvalidMethodParameters;
import Model.Ship.ShipBoard;
import Model.Ship.Components.Engine;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Utils.Position;
import Model.Enums.Side;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MeteorTest {

    @Test
    public void testMeteorDestroysComponent() throws InvalidMethodParameters {
        ShipBoard board = new ShipBoard();
        Engine engine = new Engine(Card.ENGINE, ConnectorType.UNIVERSAL, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE, false);
        board.addComponent(engine, 1, 1);

        board.applyDamage(new Position(1,1), Side.FRONT);
        assertNull(board.getComponent(1,1));
    }
}
