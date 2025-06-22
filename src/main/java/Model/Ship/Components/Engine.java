package Model.Ship.Components;

import Model.Enums.Side;
import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;

/**
 * Class representing an engine module for the spaceship.
 * Supports loading from JSON and logical management of installation/removal.
 * Handles double engine activation via battery.
 */
public class Engine extends SpaceshipComponent {

    private final boolean isDouble;
    private final int baseEnginePower;
    private boolean hasAlien;

    public Engine(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.baseEnginePower = isDouble ? 2 : 1;
        this.hasAlien = false;
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
        this.hasAlien = false;
    }

    public boolean isDoubleEngine() {
        return isDouble;
    }

    /**
     * Returns the base engine power (without alien bonus).
     */
    public int getEnginePower() {
        return baseEnginePower;
    }

    public boolean hasAlien() {
        return hasAlien;
    }

    public void setAlien(boolean hasAlien) {
        this.hasAlien = hasAlien;
    }

    @Override
    public void added(){
        if(getShipBoard().getCondensedShip().getEnginesList().contains(this)){
            throw new RuntimeException("Cargo Hold already added to the ship.");
        } else {
            getShipBoard().getCondensedShip().getEnginesList().add(this);
            if(isDouble){
                getShipBoard().getCondensedShip().getEngines().incrementSingleEngines();
            } else {
                getShipBoard().getCondensedShip().getEngines().incrementDoubleEngines();
            }
        }
    }

    @Override
    public void removed() {
        if(!getShipBoard().getCondensedShip().getEnginesList().contains(this)){
            throw new RuntimeException("Cargo Hold not found in the ship.");
        } else {
            getShipBoard().getCondensedShip().getEnginesList().remove(this);
            if(isDouble){
                getShipBoard().getCondensedShip().getEngines().decrementSingleEngines();
            } else {
                getShipBoard().getCondensedShip().getEngines().decrementDoubleEngines();
            }
        }

    }

    public String[] renderSmall() {
        String[] righe = new String[3];
        righe[0] = String.format("╔═ %d ═╗", this.getConnectorAt(Side.FRONT).getNumero());
        String sx = (this.getConnectorAt(Side.LEFT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.LEFT).getNumero()) : "║");
        String dx = (this.getConnectorAt(Side.RIGHT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.RIGHT).getNumero()) : "║");
        if (!this.isDouble) {
            righe[1] = String.format("%s E1%s %s", sx, this.getOrientation().getFreccia(), dx);
        } else {
            righe[1] = String.format("%s E2%s %s", sx, this.getOrientation().getFreccia(), dx);
        }
        righe[2] = String.format("╚═ %d ═╝", this.getConnectorAt(Side.REAR).getNumero());
        return righe;
    }

    public void renderBig() {
        // Riga superiore
        System.out.printf("╔══  %d  ══╗\n", this.getConnectorAt(Side.FRONT).getNumero());

        System.out.print("║  ENGIN  ║\n");

        System.out.printf("%s%s%s\n",
                (this.getConnectorAt(Side.LEFT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.LEFT).getNumero()) : "║"),
                "    "+
                        (this.getOrientation().getFreccia()),
                "    "+
                        (this.getConnectorAt(Side.RIGHT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.RIGHT).getNumero()) : "║")
        );

        if (!this.isDouble) {
            System.out.print("║  SINGL  ║\n");
        } else {
            System.out.print("║  DOUBL  ║\n");
        }

        // Riga inferiore
        System.out.printf("╚══  %d  ══╝\n", this.getConnectorAt(Side.REAR).getNumero());
    }
}
