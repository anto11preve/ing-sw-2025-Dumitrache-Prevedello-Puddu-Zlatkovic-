package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;

public class Cabin extends SpaceshipComponent {
    private Crewmates occupants;
    private boolean canContainBrown;
    private boolean canContainPurple;

    public Cabin(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, Crewmates occupants) {
        super(Type, front, rear, left, right);
        this.occupants = Crewmates.EMPTY;
    }

    public boolean getCanContainBrown() {;
        return canContainBrown;
    }
    public boolean getCanContainPurple() {;
        return canContainPurple;
    }

    public void setCanContainBrown(boolean canContainBrown) {
        this.canContainBrown = canContainBrown;
    }
    public void setCanContainPurple(boolean canContainPurple) {
        this.canContainPurple = canContainPurple;
    }

    public void setOccupants(Crewmates occupants) {
        this.occupants = occupants;
    }
    public Crewmates getOccupants() {
        return occupants;
    }

    public boolean hasEngineAlien() {
        return this.hasEngineAlien;
    }

    public boolean hasCannonAlien() {
        return this.hasCannonAlien;
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
