package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import com.google.gson.JsonObject;

/**
 * Represents a Structural Module component in Galaxy Trucker.
 * Structural modules serve to expand ship connectivity but do not serve active functions.
 */
public class StructuralModule extends SpaceshipComponent {

    /**
     * Standard constructor with explicit connector types.
     */
    public StructuralModule(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right) {
        super(Type, front, rear, left, right);
    }

    /**
     * Constructor to initialize a StructuralModule from a JSON object.
     * Used by the ComponentFactory for dynamic loading of components.
     */
    public StructuralModule(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );
    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("Structural Module Orientation: " + orientation);
        System.out.println("==========================");
        System.out.printf("\n\n\n\n");
    }

    public void added(){

    }

    public void removed(){

    }
}
