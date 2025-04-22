package Model.Ship.Components;

import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;

/**
 * Cannon – single or double, fires forward. Firepower depends on orientation.
 */
public class Cannon extends SpaceshipComponent {

    private final boolean isDouble;
    private final boolean requiresBattery;
    private final int basePower;
    private Direction orientation;

    public Cannon(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(Type, front, rear, left, right);
        this.isDouble = isDouble;
        this.requiresBattery = isDouble;
        this.basePower = isDouble ? 2 : 1;
        this.orientation = Direction.UP;
    }

    public Cannon(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );
        this.isDouble = json.get("isDoubleCannon").getAsBoolean();
        this.requiresBattery = json.get("requiresBattery").getAsBoolean();
        this.basePower = json.get("cannonStrength").getAsInt();
        this.orientation = Direction.UP;
    }

    public void setOrientation(Direction dir) {
        this.orientation = dir;
    }

    public Direction getOrientation() {
        return this.orientation;
    }

    /**
     * Returns the actual firepower based on orientation and whether it’s a double cannon.
     */
    public int getEffectivePower(Direction shipForward) {
        return (isDouble && orientation == shipForward) ? 2 : 1;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean requiresBattery() {
        return requiresBattery;
    }

    public int getCannonStrength() {
        return basePower;
    }
}
