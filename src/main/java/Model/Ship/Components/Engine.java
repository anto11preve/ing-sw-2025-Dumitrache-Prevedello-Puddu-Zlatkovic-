package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;

public class Engine extends SpaceshipComponent {
    private boolean isDouble;

    public Engine(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(Type, front, rear, left, right);
        this.isDouble = isDouble;
    }

    public boolean doubleEngine() {
        return isDouble;
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
