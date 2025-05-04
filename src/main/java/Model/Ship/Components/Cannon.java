package Model.Ship.Components;

import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import java.util.EnumSet;
import java.util.Set;

/**
 * Cannon – single or double, fires in specific directions based on orientation.
 * Firepower depends on direction relative to ship's forward.
 */
public class Cannon extends SpaceshipComponent {

    private final boolean isDouble;
    private final boolean requiresBattery;
    private final int basePower;
    private Direction orientation;
    private boolean hasAlien; // ✅ indicates if an alien is onboard

    public Cannon(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.requiresBattery = isDouble;
        this.basePower = isDouble ? 2 : 1;
        this.orientation = Direction.UP;
        this.hasAlien = false; // default no alien
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
        this.hasAlien = false; // default no alien
    }

    public void setOrientation(Direction dir) {
        this.orientation = dir;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean requiresBattery() {
        return requiresBattery;
    }

    /**
     * Returns base cannon strength (without alien bonus).
     */
    public int getCannonStrength() {
        return basePower;
    }

    /**
     * Returns base cannon strength plus alien bonus if present.
     */
    public int getEffectiveCannonStrength() {
        return hasAlien ? basePower + 1 : basePower;
    }

    /**
     * Returns all directions this cannon can fire considering its orientation.
     */
    public Set<Direction> getFiringDirections() {
        EnumSet<Direction> directions = EnumSet.noneOf(Direction.class);
        directions.add(orientation); // Always fires where it's oriented
        if (orientation == Direction.LEFT || orientation == Direction.RIGHT) {
            directions.add(Direction.UP);    // Also can fire forward if mounted sideways
            directions.add(Direction.DOWN);  // And backward
        }
        return directions;
    }

    /**
     * Computes effective power in a specific direction, considering alien bonus.
     * - If firing forward (matching orientation), and it's double, stronger.
     * - Adds +1 power if an alien is onboard.
     */
    public int getEffectivePower(Direction fireDirection) {
        if (getFiringDirections().contains(fireDirection)) {
            int power = (isDouble && fireDirection == orientation) ? 2 : 1;
            if (hasAlien) {
                power += 1;
            }
            return power;
        }
        return 0;
    }

    /**
     * Returns whether this cannon has an alien onboard.
     */
    public boolean hasAlien() {
        return hasAlien;
    }

    /**
     * Sets whether this cannon has an alien onboard.
     */
    public void setAlien(boolean hasAlien) {
        this.hasAlien = hasAlien;
    }
}
