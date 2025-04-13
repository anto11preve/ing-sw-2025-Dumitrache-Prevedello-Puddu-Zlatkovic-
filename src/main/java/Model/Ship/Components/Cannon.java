package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;

public class Cannon extends SpaceshipComponent {
    final private boolean  isDouble;


    /*
    Cannons "shoot direction" is the direction of the front connector, then the real orientation depends on the orientation of the cannon
     */

    public Cannon(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(Type, front, rear, left, right);
        this.isDouble = isDouble;
    }

    public boolean doubleCannon() {
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
