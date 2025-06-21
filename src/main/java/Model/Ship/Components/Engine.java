package Model.Ship.Components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an engine module for the spaceship.
 * Supports loading from JSON and logical management of installation/removal.
 * Handles double engine activation via battery.
 */
public class Engine extends SpaceshipComponent {

    private final boolean isDouble;
    private final int baseEnginePower;
    //private boolean hasAlien;

    public Engine(Card type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(type, front, rear, left, right);
        this.isDouble = isDouble;
        this.baseEnginePower = isDouble ? 2 : 1; //TODO: serve?
        //this.hasAlien = false;
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
        this.baseEnginePower = isDouble ? 2 : 1;
    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("Engine is Double: " + isDouble);
        System.out.println("==========================");
        System.out.printf("\n\n\n\n");
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
}
