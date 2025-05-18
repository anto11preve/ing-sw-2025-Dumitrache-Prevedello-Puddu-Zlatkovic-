package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;

/**
 * Represents a Shield Generator component in Galaxy Trucker.
 */
public class ShieldGenerator extends SpaceshipComponent {

    private Direction direction; // ✅ Ensure it's the correct Direction type

    public ShieldGenerator(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, Direction direction) {
        super(type, front, rear, left, right);
        this.direction = direction;
    }

    /**
     * Returns the direction the shield is facing.
     * @return the shield's Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction the shield is facing.
     * @param direction the new Direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
