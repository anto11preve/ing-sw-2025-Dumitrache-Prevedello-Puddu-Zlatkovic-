package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import com.google.gson.JsonObject;

/**
 * Represents a Shield Generator component in Galaxy Trucker.
 * Shield Generators provide directional defense, often requiring battery power.
 */
public class ShieldGenerator extends SpaceshipComponent {

    private Direction direction; // The direction this shield protects (e.g., FRONT, LEFT)

    /**
     * Constructor with explicit parameters.
     */
    public ShieldGenerator(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, Direction direction) {
        super(type, front, rear, left, right);
        this.direction = direction;
    }

    /**
     * Constructor to initialize a ShieldGenerator from a JSON object.
     * Used by ComponentFactory for dynamic component creation.
     */
    public ShieldGenerator(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );

        // Read the direction property for shield orientation
        this.direction = Direction.valueOf(json.get("direction").getAsString().toUpperCase());
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
