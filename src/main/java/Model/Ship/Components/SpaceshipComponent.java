package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import Model.Enums.Side;
import Model.Ship.ShipBoard;
import javafx.scene.control.skin.TextInputControlSkin;

/**
 * Abstract class representing a spaceship component.
 * Handles rotation, connectors, and association with the shipboard.
 */
public abstract class SpaceshipComponent {
    private final Card type;
    private ConnectorType frontConnector;
    private ConnectorType rearConnector;
    private ConnectorType leftConnector;
    private ConnectorType rightConnector;
    private Direction orientation; // UP = standard, RIGHT = 90°, DOWN = 180°, LEFT = 270°
    private ShipBoard shipBoard;
    private boolean isVisible;



    public SpaceshipComponent(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right) {
        this.type = type;
        this.frontConnector = front;
        this.rearConnector = rear;
        this.leftConnector = left;
        this.rightConnector = right;
        this.orientation = Direction.UP; // Default orientation
        this.isVisible = false;
        this.shipBoard = null;
    }

    public void setVisible(){
        this.isVisible = true;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public Card getType() {
        return type;
    }

    /**
     * Rotates the component 90 degrees clockwise.
     */
    public void rotate() {
        ConnectorType temp = frontConnector;
        frontConnector = leftConnector;
        leftConnector = rearConnector;
        rearConnector = rightConnector;
        rightConnector = temp;
        orientation = rotateClockwise(orientation);
    }

    public void setOrientation(Direction orientation) {

        while (this.orientation != orientation) {
            rotate();
        }
    }


    private Direction rotateClockwise(Direction dir) {
        switch (dir) {
            case UP: return Direction.RIGHT;
            case RIGHT: return Direction.DOWN;
            case DOWN: return Direction.LEFT;
            case LEFT: return Direction.UP;
            default: return dir;
        }
    }


//    public void setOrientation(Direction orientation) {
//        this.orientation = orientation;
//    }

    /**
     * Returns the connector type at a given side considering current orientation.
     */
    public ConnectorType getConnectorAt(Side side) {
        // Map the logical side based on the current rotation
        Side rotatedSide = getRotatedSide(side);
        switch (rotatedSide) {
            case FRONT: return frontConnector;
            case REAR: return rearConnector;
            case LEFT: return leftConnector;
            case RIGHT: return rightConnector;
            default: return null;
        }
    }

    private Side getRotatedSide(Side side) {
        int rotations = orientation.ordinal() % 4;
        Side[] order = {Side.FRONT, Side.RIGHT, Side.REAR, Side.LEFT};
        int idx = -1;
        for (int i = 0; i < order.length; i++) {
            if (order[i] == side) {
                idx = i;
                break;
            }
        }
        if (idx == -1) return side;
        return order[(idx + rotations) % 4];
    }

    public ShipBoard getShipBoard() {
        return shipBoard;
    }

    public void setShipBoard(ShipBoard ship) {
        this.shipBoard = ship;
    }

    /**
     * Hooks for lifecycle events: override in subclasses if needed.
     */
    public void added() {
        // to be implemented if needed
    }

    public void removed() {
        // to be implemented if needed
    }

    public void reserved() {
        // to be implemented if needed
    }

}

