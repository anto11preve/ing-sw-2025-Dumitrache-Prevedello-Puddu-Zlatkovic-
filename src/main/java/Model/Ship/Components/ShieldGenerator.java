package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;

public class ShieldGenerator extends SpaceshipComponent {

    public ShieldGenerator(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right) {
        super(Type, front, rear, left, right);
    }
}
