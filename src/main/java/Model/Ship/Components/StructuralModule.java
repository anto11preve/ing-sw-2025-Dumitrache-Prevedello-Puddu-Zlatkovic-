package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;

public class StructuralModule extends SpaceshipComponent {

    public StructuralModule(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right){
        super(Type, front, rear, left, right);
    }
}
