package Model.Ship.Components;

import Controller.Exceptions.InvalidContextualAction;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Ship.ShipBoard;
import com.google.gson.JsonObject;

/**
 * Represents a Battery Compartment component on the ship.
 * Provides energy used by other components like shields, cannons, or engines.
 */
public class BatteryCompartment extends SpaceshipComponent {
    private final int capacity;  // Maximum number of batteries the compartment can hold
    private int batteries;       // Current number of batteries available
    /**
     * Standard constructor for BatteryCompartment with explicit parameters.
     */
    public BatteryCompartment(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, int capacity) {
        super(Type, front, rear, left, right);
        this.capacity = capacity;
        this.batteries = capacity;
    }

    /**
     * Constructor that initializes a BatteryCompartment from a JSON object.
     * Used by the ComponentFactory to dynamically load components from configuration.
     */
    public BatteryCompartment(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString().toUpperCase()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString()),
                json.get("imagePath").getAsString()
        );

        if (json.has("capacity")) {
            this.capacity  = json.get("capacity").getAsInt();
            this.batteries = this.capacity;
        }else{
            throw new RuntimeException("Missing capacity in BatteryCompartment JSON configuration" +
                    " at " + json.get("imagePath").getAsString());
        }

    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("Battery Compartment Capacity: " + capacity);
        System.out.println("Current Batteries: " + batteries);
        System.out.println("==========================");
        System.out.printf("\n\n\n\n");
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBatteries() {
        return batteries;
    }

    public void setBatteries(int batteries) {
        this.batteries = batteries;
    }

    /**
     * Consumes one battery if available. Throws an exception if no batteries remain.
     */
    public void removeBattery() throws IllegalStateException {
        if (batteries > 0) {
            batteries--;
        } else {
            throw new IllegalStateException("No batteries left to remove");
        }
    }

    @Override
    public void added(){
        if(getShipBoard().getCondensedShip().getBatteryCompartments().contains(this)){
            throw new RuntimeException("Battery Compartment already added to the ship.");
        } else {
            getShipBoard().getCondensedShip().addBatteryCompartment(this);
        }
    }

    @Override
    public void removed() {
        if(!getShipBoard().getCondensedShip().getBatteryCompartments().contains(this)){
            throw new RuntimeException("Battery Compartment not found in the ship.");
        } else {
            getShipBoard().getCondensedShip().removeBatteryCompartment(this);
        }

    }
}
