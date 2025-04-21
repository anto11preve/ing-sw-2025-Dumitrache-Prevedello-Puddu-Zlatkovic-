package Model.Ship.Components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
/*import Model.Visitor;*/

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un modulo motore nella nave.
 * Supporta caricamento da JSON e gestione logica di inserimento/rimozione.
 */
public class Engine extends SpaceshipComponent {

    private final boolean isDouble;
    private final int enginePower;
    private final int energyCost;
    private final boolean requiresBattery;
    private final Direction direction;
    private final String imagePath;
    private final String animationPath;
    private final List<String> abilities;

    // === Costruttore classico (fallback manuale) ===
    public Engine(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.enginePower = isDouble ? 2 : 1;
        this.energyCost = 0;
        this.requiresBattery = false;
        this.direction = Direction.REAR_ONLY;
        this.imagePath = "";
        this.animationPath = "";
        this.abilities = new ArrayList<>();
    }

    // === Costruttore da JSON ===
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
        this.direction = Direction.valueOf(json.get("direction").getAsString().toUpperCase());

        this.imagePath = json.get("imagePath").getAsString();
        this.animationPath = json.get("animationPath").getAsString();

        this.abilities = new ArrayList<>();
        for (JsonElement e : json.getAsJsonArray("abilities")) {
            abilities.add(e.getAsString());
        }
    }

    // === Metodi di stato ===
    public boolean isDoubleEngine() {
        return isDouble;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public boolean isBatteryRequired() {
        return requiresBattery;
    }

    public Direction getDirection() {
        return direction;
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

    // === Hook logici ===
    @Override
    public void added() {
        System.out.println("Engine added → power: " + enginePower + ", direction: " + direction);
        // TODO: aggiungi logica (es. registrare in nave, accendere luci, ecc.)
    }

    @Override
    public void removed() {
        System.out.println("Engine removed → clearing power.");
        // TODO: togli bonus o decrementa potenza
    }
/*
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
*/