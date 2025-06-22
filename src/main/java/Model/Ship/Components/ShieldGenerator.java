package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import com.google.gson.JsonObject;

/**
 * Represents a Shield Generator component in Galaxy Trucker.
 * Shield Generators provide directional defense, often requiring battery power.
 */
public class ShieldGenerator extends SpaceshipComponent {

    /**
     * Constructor with explicit parameters.
     */
    public ShieldGenerator(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, Direction direction) {
        super(type, front, rear, left, right);
    }


    /**
     * Constructor to initialize a ShieldGenerator from a JSON object.
     * Used by ComponentFactory for dynamic component creation.
     */
    public ShieldGenerator(JsonObject json) {
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

    @Override
    public void added(){
        switch(orientation) {
            case UP:
                getShipBoard().getCondensedShip().getShields().incrementNorthShields();
                getShipBoard().getCondensedShip().getShields().incrementEastShields();
                break;
            case RIGHT:
                getShipBoard().getCondensedShip().getShields().incrementSouthShields();
                getShipBoard().getCondensedShip().getShields().incrementEastShields();
                break;
            case DOWN:
                getShipBoard().getCondensedShip().getShields().incrementSouthShields();
                getShipBoard().getCondensedShip().getShields().incrementWestShields();
                break;
            case LEFT:
                getShipBoard().getCondensedShip().getShields().incrementNorthShields();
                getShipBoard().getCondensedShip().getShields().incrementWestShields();
                break;
        }
    }

    @Override
    public void removed() {
        switch(orientation) {
            case UP:
                getShipBoard().getCondensedShip().getShields().decrementNorthShields();
                getShipBoard().getCondensedShip().getShields().decrementEastShields();
                break;
            case RIGHT:
                getShipBoard().getCondensedShip().getShields().decrementSouthShields();
                getShipBoard().getCondensedShip().getShields().decrementEastShields();
                break;
            case DOWN:
                getShipBoard().getCondensedShip().getShields().decrementSouthShields();
                getShipBoard().getCondensedShip().getShields().decrementWestShields();
                break;
            case LEFT:
                getShipBoard().getCondensedShip().getShields().decrementNorthShields();
                getShipBoard().getCondensedShip().getShields().decrementWestShields();
                break;
        }
    }
}
