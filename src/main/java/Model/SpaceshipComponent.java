package Model;

// Classe astratta per componenti della nave spaziale
abstract class SpaceshipComponent {
    private /*final*/ Card type;
    private ConnectorType frontConnector;
    private ConnectorType rearConnector;
    private ConnectorType leftConnector;
    private ConnectorType rightConnector;
    private int orientation;
    private ShipBoard shipBoard;

    public SpaceshipComponent() {
        //to do, how do we handle the type of the component?
    }

    public Card getType() {
        return type;
    }

    public void rotate() {
        ConnectorType temp = frontConnector;
        frontConnector = leftConnector;
        leftConnector = rearConnector;
        rearConnector = rightConnector;
        rightConnector = temp;
    }

    public boolean connect(SpaceshipComponent component, Side side) {
        //to do
        return true;
    }

    public ConnectorType getSide(Side side) {
        switch (side) {
            case Side.FRONT:
                return frontConnector;
            case Side.REAR:
                return rearConnector;
            case Side.LEFT:
                return leftConnector;
            case Side.RIGHT:
                return rightConnector;
            default:
                return null;
        }
    }
}
