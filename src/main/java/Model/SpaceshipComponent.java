package Model;

// Classe astratta per componenti della nave spaziale
abstract class SpaceshipComponent {
    private final Card type;
    private ConnectorType frontConnector;
    private ConnectorType rearConnector;
    private ConnectorType leftConnector;
    private ConnectorType rightConnector;
    private int orientation;    //0 standard, 1 girato a destra, 2 girato a 180°, 3 girato a sinistra
    private ShipBoard shipBoard;

    public SpaceshipComponent( Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right) {
        this.type = Type;
        this.frontConnector = front;
        this.rearConnector = rear;
        this.leftConnector = left;
        this.rightConnector = right;
        this.orientation = 0;
        //this.shipBoard = ???
    }

    public Card getType() {
        return type;
    }

    //rotazione oraria
    public void rotate() {
        ConnectorType temp = frontConnector;
        frontConnector = leftConnector;
        leftConnector = rearConnector;
        rearConnector = rightConnector;
        rightConnector = temp;
        orientation = (orientation + 1) % 4;
    }

    public int getOrientation() {
        return orientation;
    }

    public ConnectorType getConnectorType( int direction) {
        switch (direction) {
            case 0:
                return frontConnector;
            case 1:
                return rearConnector;
            case 2:
                return leftConnector;
            case 3:
                return rightConnector;
            default:
                return null;
        }
    }

    public void getShipBoard(ShipBoard shipBoard) {
        this.shipBoard = shipBoard;
    }
    public void setShipBoard(ShipBoard ship) {
        this.shipBoard = ship;
    }

    /*
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
    */

    public void added() {
        //to do
    }
    public void removed() {
        //to do
    }
    public void reserved() {
        //to do
    }
}

