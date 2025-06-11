package Model.Ship.Components;

import Model.Ship.ShipBoard;
import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import java.util.EnumSet;
import java.util.Set;

/**
 * Cannon – single or double, fires in specific directions based on orientation.
 * Firepower depends on direction, alien bonus, and battery activation for double cannons.
 */
public class Cannon extends SpaceshipComponent {

    private final boolean isDouble;
    private final boolean requiresBattery;
    private final int basePower;
    private Direction orientation;
    private boolean hasAlien;
    private boolean activated; // ✅ NEW: whether the cannon has been activated with a battery

    public Cannon(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.requiresBattery = isDouble;
        this.basePower = isDouble ? 2 : 1;
        this.orientation = Direction.UP;
        this.hasAlien = false;
        this.activated = !isDouble; // single cannons are always "activated"
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
        this.hasAlien = false;
        this.activated = !isDouble; // single cannons default to active
    }

//    public void setOrientation(Direction dir) {
//        this.orientation = dir;
//    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean requiresBattery() {
        return requiresBattery;
    }

    /**
     * Activates this cannon using a battery (needed for double cannons).
     */
    public void activate() {
        if (isDouble) {
            this.activated = true;
        }
    }

    /**
     * Deactivates the cannon (e.g., if battery is lost).
     */
    public void deactivate() {
        if (isDouble) {
            this.activated = false;
        }
    }

    /**
     * Returns whether this cannon has been activated (only matters for double cannons).
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Returns base cannon strength (without alien bonus).
     */
    public int getCannonStrength() {
        return basePower;
    }

    /**
     * Returns all directions this cannon can fire considering its orientation.
     */
    public Set<Direction> getFiringDirections() {
        EnumSet<Direction> directions = EnumSet.noneOf(Direction.class);
        directions.add(orientation);
        if (orientation == Direction.LEFT || orientation == Direction.RIGHT) {
            directions.add(Direction.UP);
            directions.add(Direction.DOWN);
        }
        return directions;
    }

    /**
     * Computes effective power in a specific direction.
     * Double cannons return 0 unless activated.
     * Adds +1 if an alien is onboard.
     */
    public int getEffectivePower(Direction fireDirection) {
        if (getFiringDirections().contains(fireDirection)) {
            if (isDouble && !activated) {
                return 0; // double cannon not activated → 0 power
            }
            int power = isDouble ? 2 : 1;
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

    @Override
    public void added(){
        if(getShipBoard().getCondensedShip().getCannons().contains(this)){
            throw new RuntimeException("Cannon already added to the ship.");
        } else {
            getShipBoard().getCondensedShip().addCannon(this);
        }
    }

    @Override
    public void removed() {
        if(!getShipBoard().getCondensedShip().getCannons().contains(this)){
            throw new RuntimeException("Cannon not found in the ship.");
        } else {
            getShipBoard().getCondensedShip().removeCannon(this);
        }

    }
}
