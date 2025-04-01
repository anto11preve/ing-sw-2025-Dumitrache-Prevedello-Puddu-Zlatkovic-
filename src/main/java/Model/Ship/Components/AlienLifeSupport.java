package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;

public class AlienLifeSupport extends SpaceshipComponent {
    private final int color;

    public AlienLifeSupport(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, int color) {
        super(Type, front, rear, left, right);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    /*
    public void added() {
        //to do
    }
    public void removed() {
        //to do
    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    */
}
