package Model.Factories;

import Model.Ship.*;
import Model.Ship.Components.*;
import com.google.gson.JsonObject;

/**
 * Factory for dynamically creating specific SpaceshipComponent instances from a JsonObject.
 */
public class ComponentFactory {

    /**
     * Builds a specific component from JSON using the "type" field.
     *
     * @param obj the JSON object to parse
     * @return a specific SpaceshipComponent instance
     */
    public static SpaceshipComponent fromJson(JsonObject obj) {
        String type = obj.get("type").getAsString();

        return switch (type) {
            case "Cabin" -> new Cabin(obj);
            case "Engine" -> new Engine(obj);
            case "Cannon" -> new Cannon(obj);
            case "CargoHold" -> new CargoHold(obj);
            case "Battery" -> new BatteryCompartment(obj);
            case "Shield" -> new ShieldGenerator(obj);
            case "AlienLifeSupport" -> new AlienLifeSupport(obj);
            case "DoubleCannon" -> new Cannon(obj);
            case "DoubleEngine" -> new Engine(obj);
            default -> throw new IllegalArgumentException("Unknown component type: " + type);
        };
    }
}
