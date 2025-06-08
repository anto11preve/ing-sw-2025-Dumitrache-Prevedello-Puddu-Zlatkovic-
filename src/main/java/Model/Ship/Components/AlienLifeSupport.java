package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.AlienColor;
import com.google.gson.JsonObject;

/**
 * Represents an Alien Life Support component.
 * Supports specific alien crew members (brown or purple), enhancing nearby cabins.
 */
public class AlienLifeSupport extends SpaceshipComponent {
    private final AlienColor color; // Indicates which alien color this life support supports

    /**
     * Constructor with explicit parameters.
     */
    public AlienLifeSupport(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, AlienColor color) {
        super(Type, front, rear, left, right);
        this.color = color;
    }

    /**
     * Constructor to initialize an AlienLifeSupport from a JSON object.
     * Used by ComponentFactory to dynamically instantiate the component.
     */
    public AlienLifeSupport(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );

        this.color = AlienColor.valueOf(json.get("alienColor").getAsString().toUpperCase());
    }

    /**
     * Returns the color of alien this module supports.
     */
    public AlienColor getColor() {
        return color;
    }

    /*
    public void added() {
        // to do
    }
    public void removed() {
        // to do
    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    */
}
