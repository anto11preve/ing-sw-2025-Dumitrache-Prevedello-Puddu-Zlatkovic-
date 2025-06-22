package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import Model.Enums.Side;
import Model.Ship.ShipBoard;

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
    protected Direction orientation; // UP = standard, RIGHT = 90°, DOWN = 180°, LEFT = 270°
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

    public void setVisible() {
        this.isVisible = true;
    }

    public boolean isVisible() {
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
        switch (orientation){
            case UP: orientation = Direction.RIGHT; break;
            case RIGHT: orientation = Direction.DOWN; break;
            case DOWN: orientation = Direction.LEFT; break;
            case LEFT: orientation = Direction.UP; break;
        }
    }

    /**
     * Rotates to match a specific target orientation (minimal steps).
     */
    public void setOrientation(Direction orientation) {
        while (this.orientation != orientation) {
            rotate();
        }
    }

    /**
     * Rotates the current orientation 90 degrees clockwise.
     *
     * @param dir the current direction
     * @return the new direction after rotation
     */
    private Direction rotateClockwise(Direction dir) {
        return switch (dir) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    /**
     * Returns the current orientation of the component.
     */
    public Direction getOrientation() {
        return orientation;
    }

    /**
     * Returns the connector type at a given side considering current orientation.
     */
    public ConnectorType getConnectorAt(Side side) {
        // Map the logical side based on the current rotation
        //Side rotatedSide = getRotatedSide(side); // TODO: secondo me considerava il modo che avevo pensato io di gestire l'orientazione, ma per come l'ha impelementato ora, si bugga e basta
        //switch (rotatedSide) {
        return switch (side) {
            case FRONT -> frontConnector;
            case REAR -> rearConnector;
            case LEFT -> leftConnector;
            case RIGHT -> rightConnector;
        };
    }


// TODO: non serve più, ma lo lascio per ora per sicurezza
//
//    private Side getRotatedSide(Side side) {
//        int rotations = orientation.ordinal() % 4;
//        Side[] order = {Side.FRONT, Side.RIGHT, Side.REAR, Side.LEFT};
//        int idx = -1;
//        for (int i = 0; i < order.length; i++) {
//            if (order[i] == side) {
//                idx = i;
//                break;
//            }
//        }
//        if (idx == -1) return side;
//        return order[(idx + rotations) % 4];
//    }

    public ShipBoard getShipBoard() {
        return shipBoard;
    }

    public void setShipBoard(ShipBoard ship) {
        this.shipBoard = ship;
    }

    /**
     * Hooks for lifecycle events: override in subclasses if needed.
     */
    public abstract void added();
    public abstract void removed();


    public String[] renderSmall(){
        return null;
    }

    public void renderBig(){

    }




//    public void reserved() {
//        // to be implemented if needed
//    }

}
