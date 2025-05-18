package Model.Ship.Components;

import com.google.gson.JsonObject;

/**
 * Factory class for creating SpaceshipComponent objects from JSON.
 * Matches component type with the appropriate subclass.
 */
public class ComponentFactory {

    /**
     * Parses a JSON object and returns the appropriate SpaceshipComponent.
     *
     * @param json JsonObject representing the component
     * @return SpaceshipComponent instance or null if type unknown
     */
    public static SpaceshipComponent fromJson(JsonObject json) {
        String type = json.get("type").getAsString().toUpperCase(); // Normalizza per sicurezza

        switch (type) {
            case "CABIN":
            case "LUXURY_CABIN":
            case "CREW_MODULE":
                return new Cabin(json);

            case "CARGO_HOLD":
            case "SPECIAL_CARGO_HOLD":
                return new CargoHold(json);

            case "BATTERY_COMPARTMENT":
                return new BatteryCompartment(json);

            case "ENGINE":
                return new Engine(json);

            case "CANNON":
                return new Cannon(json);

            case "SHIELD_GENERATOR":
                return new ShieldGenerator(json);

            case "STRUCTURAL_MODULE":
            case "UNIVERSAL_CONNECTOR":
                return new StructuralModule(json);

            case "ALIEN_LIFE_SUPPORT":
                return new AlienLifeSupport(json);

            default:
                System.err.println("Unknown component type: " + type);
                return null;
        }
    }
}
