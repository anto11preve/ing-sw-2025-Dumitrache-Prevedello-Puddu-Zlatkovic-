package Model;

class Cabin extends SpaceshipComponent {
    private Crewmates occupants;

    public Cabin(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, Crewmates occupants) {
        super(Type, front, rear, left, right);
        this.occupants = occupants;
    }

    public boolean canContainBrown() {;
        //to do
        return true;
    }
    private boolean canContainPurple() {;
        //to do
        return true;
    }

    public void setOccupants(Crewmates occupants) {
        this.occupants = occupants;
    }
    public Crewmates getOccupants() {
        return occupants;
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
