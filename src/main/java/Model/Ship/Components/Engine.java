package Model.Ship.Components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an engine module for the spaceship.
 * Supports loading from JSON and logical management of installation/removal.
 * Handles double engine activation via battery.
 */
public class Engine extends SpaceshipComponent {

    private final boolean isDouble;
    private final int baseEnginePower;
    private final int energyCost;
    private final boolean requiresBattery;
    private final String imagePath;
    private final String animationPath;
    private final List<String> abilities;
    private boolean hasAlien;
    private boolean activated; //   whether the engine has been activated with a battery

    public Engine(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.baseEnginePower = isDouble ? 2 : 1;
        this.energyCost = 0;
        this.requiresBattery = isDouble;
        this.imagePath = "";
        this.animationPath = "";
        this.abilities = new ArrayList<>();
        this.hasAlien = false;
        this.activated = !isDouble; // single engines are always "activated"
    }

    public Engine(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );

        this.isDouble = json.has("isDoubleEngine") && json.get("isDoubleEngine").getAsBoolean();
        this.baseEnginePower = json.get("enginePower").getAsInt();
        this.energyCost = json.get("energyCost").getAsInt();
        this.requiresBattery = json.get("requiresBattery").getAsBoolean();
        this.imagePath = json.get("imagePath").getAsString();
        this.animationPath = json.get("animationPath").getAsString();
        this.abilities = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("abilities")) {
            abilities.add(e.getAsString());
        }
        this.hasAlien = false;
        this.activated = !isDouble; // single engines default to active
    }

    public boolean isDoubleEngine() {
        return isDouble;
    }

    /**
     * Activates this engine using a battery (needed for double engines).
     */
    public void activate() {
        if (isDouble) {
            this.activated = true;
        }
    }

    /**
     * Deactivates this engine (e.g., if battery is lost).
     */
    public void deactivate() {
        if (isDouble) {
            this.activated = false;
        }
    }

    @Override
    public int getThrust(Direction shipRear) {
        return isCorrectlyOriented(shipRear) ? getEnginePower() : 0;
    }


    /**
     * Returns whether this engine has been activated (only matters for double engines).
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Returns the base engine power (without alien bonus).
     */
    public int getEnginePower() {
        return baseEnginePower;
    }

    /**
     * Returns the effective engine power considering activation and alien bonus.
     * Double engines return 0 if not activated.
     */
    public int getEffectiveEnginePower() {
        if (isDouble && !activated) {
            return 0;
        }
        return hasAlien ? baseEnginePower + 1 : baseEnginePower;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public boolean requiresBattery() {
        return requiresBattery;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAnimationPath() {
        return animationPath;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public Direction getThrustDirection() {
        return getOrientation();
    }

    public boolean isCorrectlyOriented(Direction shipRear) {
        return getThrustDirection() == shipRear;
    }

    public boolean hasAlien() {
        return hasAlien;
    }

    public void setAlien(boolean hasAlien) {
        this.hasAlien = hasAlien;
    }

    @Override
    public void added() {
        System.out.println("Engine added → Power: " + baseEnginePower + ", orientation: " + getOrientation());
    }

    @Override
    public void removed() {
        System.out.println("Engine removed → Clearing thrust contribution.");
    }
}
