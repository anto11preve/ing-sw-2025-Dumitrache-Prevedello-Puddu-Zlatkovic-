package Model.Ship.Components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import Model.Enums.Side;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an engine module for the spaceship.
 * Supports loading from JSON and logical management of installation/removal.
 */
public class Engine extends SpaceshipComponent {

    private final boolean isDouble;
    private final int enginePower;
    private final int energyCost;
    private final boolean requiresBattery;
    private final String imagePath;
    private final String animationPath;
    private final List<String> abilities;

    public Engine(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.enginePower = isDouble ? 2 : 1;
        this.energyCost = 0;
        this.requiresBattery = false;
        this.imagePath = "";
        this.animationPath = "";
        this.abilities = new ArrayList<>();
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
        this.enginePower = json.get("enginePower").getAsInt();
        this.energyCost = json.get("energyCost").getAsInt();
        this.requiresBattery = json.get("requiresBattery").getAsBoolean();
        this.imagePath = json.get("imagePath").getAsString();
        this.animationPath = json.get("animationPath").getAsString();

        this.abilities = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("abilities")) {
            abilities.add(e.getAsString());
        }
    }

    // === State methods ===
    public boolean isDoubleEngine() {
        return isDouble;
    }

    public int getEnginePower() {
        return enginePower;
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

    /**
     * Returns the current thrust direction of the engine based on its orientation.
     */
    public Direction getThrustDirection() {
        return getOrientation();
    }

    /**
     * Checks whether the engine is correctly oriented to push toward the rear of the ship.
     */
    public boolean isCorrectlyOriented(Direction shipRear) {
        return getThrustDirection() == shipRear;
    }

    // === Lifecycle hooks ===
    @Override
    public void added() {
        System.out.println("Engine added → Power: " + enginePower + ", orientation: " + getOrientation());
        // TODO: Register the engine with the ship system
    }

    @Override
    public void removed() {
        System.out.println("Engine removed → Clearing thrust contribution.");
        // TODO: Deregister or reduce ship's thrust power
    }
}
