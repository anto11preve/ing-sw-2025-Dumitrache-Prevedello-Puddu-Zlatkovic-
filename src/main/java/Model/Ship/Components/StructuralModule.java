package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Side;
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
                Card.valueOf(json.get("type").getAsString().toUpperCase()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString()),
                json.get("imagePath").getAsString()
        );
    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("==========================");
        System.out.printf("\n\n\n\n");
    }

    public void added(){

    }

    public void removed(){

    }

    public String[] renderSmall() {
        String[] righe = new String[3];
        righe[0] = String.format("╔═ %d ═╗", this.getConnectorAt(Side.FRONT).getNumero());
        String sx = (this.getConnectorAt(Side.LEFT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.LEFT).getNumero()) : "║");
        String dx = (this.getConnectorAt(Side.RIGHT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.RIGHT).getNumero()) : "║");
        righe[1] = String.format("%s STR %s", sx, dx);
        righe[2] = String.format("╚═ %d ═╝", this.getConnectorAt(Side.REAR).getNumero());
        return righe;
    }

    public String[] renderBig() {
        String[] righe = new String[5];

        // Riga superiore
        righe[0] = String.format("╔══  %s  ══╗",
                this.getConnectorAt(Side.FRONT).getNumero() > 0 ?
                        String.valueOf(this.getConnectorAt(Side.FRONT).getNumero()) : "═");

        righe[1] = "║  STRUC  ║";

        righe[2] = String.format("%s%s%s",
                (this.getConnectorAt(Side.LEFT).getNumero() > 0 ?
                        String.valueOf(this.getConnectorAt(Side.LEFT).getNumero()) : "║"),
                "    " + this.getOrientation().getFreccia(),
                "    " + (this.getConnectorAt(Side.RIGHT).getNumero() > 0 ?
                        String.valueOf(this.getConnectorAt(Side.RIGHT).getNumero()) : "║")
        );

        righe[3] = "║         ║";

        // Riga inferiore
        righe[4] = String.format("╚══  %s  ══╝",
                this.getConnectorAt(Side.REAR).getNumero() > 0 ?
                        String.valueOf(this.getConnectorAt(Side.REAR).getNumero()) : "═");

        return righe;
    }
}
