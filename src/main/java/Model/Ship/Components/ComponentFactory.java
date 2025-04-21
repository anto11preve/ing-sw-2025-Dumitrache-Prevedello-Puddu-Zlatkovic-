package Model.Ship.Components;

import com.google.gson.JsonObject;

public class ComponentFactory {

    public static SpaceshipComponent fromJson(JsonObject json) {
        String type = json.get("type").getAsString();

        switch (type.toUpperCase()) {
            case "ENGINE":
                return new Engine(json);
            case "CANNON":
                return new Cannon(json);
            case "CABIN":
                return new Cabin(json);
            case "CARGOHOLD":
                return new CargoHold(json);
            case "SHIELDGENERATOR":
                return new ShieldGenerator(json);
            case "BATTERY":
                return new BatteryCompartment(json);
            case "ALIEN_SUPPORT":
                return new AlienLifeSupport(json);
            case "STRUCTURE":
                return new StructuralModule(json);
            default:
                throw new IllegalArgumentException("Unknown component type: " + type);
        }
    }
}
