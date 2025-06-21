package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Good;
import Model.Ship.ShipBoard;
import com.google.gson.JsonObject;

/**
 * Represents a CargoHold component used to store goods in the ship.
 * Special cargo holds can contain red (dangerous) goods, while standard holds cannot.
 */
public class CargoHold extends SpaceshipComponent {
    private final int capacity;        // Number of slots available in the hold
    private Good[] goods;              // Goods currently stored in the cargo hold
    private boolean isSpecial;         // True if this cargo hold can carry red goods

    /**
     * Constructor using explicit parameters.
     */
    public CargoHold(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, int capacity, boolean isSpecial) {
        super(Type, front, rear, left, right);
        this.capacity = capacity;
        this.goods = new Good[capacity];
        this.isSpecial = isSpecial;
    }

    /**
     * Constructor that initializes a CargoHold from a JSON object.
     * Used by the ComponentFactory to dynamically load components from configuration.
     */
    public CargoHold(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );

        this.capacity = json.get("cargoCapacity").getAsInt();
        this.isSpecial = json.get("isSpecial").getAsBoolean();
        this.goods = new Good[this.capacity];
    }

    @Override
    public void visualize() {
        super.visualize();
        System.out.println("Cargo Hold Capacity: " + capacity);
        System.out.println("Cargo Hold Special: " + isSpecial);
        System.out.println("Goods in Cargo Hold:");
        for (int i = 0; i < goods.length; i++) {}
        for (Good good : goods) {}
        System.out.println("==========================");
        System.out.printf("\n\n\n\n");
    }

    public int getCapacity() {
        return capacity;
    }

    public Good[] getGoods() {
        return goods;
    }

    /**
     * Adds a good to the first available slot in the cargo hold.
     * @param good the good to add
     * @return true if successfully added, false otherwise (e.g., no space or red good in normal hold)
     */
    public boolean addGood(Good good) {
        if (good == Good.RED && !isSpecial) {
            return false;
        }
        for (int i = 0; i < goods.length; i++) {
            if (goods[i] == null) {
                goods[i] = good;
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a good to a specific index in the cargo hold.
     * @param good the good to add
     * @param index the index at which to add the good
     * @return true if added successfully, false if the index is invalid, occupied, or not allowed
     */
    public boolean addGoodAt(Good good, int index) {
        if (good == Good.RED && !isSpecial) {
            return false;
        }
        if (index < 0 || index >= goods.length || goods[index] != null) {
            return false;
        }
        goods[index] = good;
        return true;
    }

    /**
     * Removes the good at the specified index.
     * @param index the index of the good to remove
     */
    public void removeGood(int index) {
        if (index >= 0 && index < goods.length) {
            goods[index] = null;
        }
    }

    @Override
    public void added(){
        if(getShipBoard().getCondensedShip().getCargoHolds().contains(this)){
            throw new RuntimeException("Cargo Hold already added to the ship.");
        } else {
            getShipBoard().getCondensedShip().addCargoHold(this);
        }
    }

    @Override
    public void removed() {
        if(!getShipBoard().getCondensedShip().getCargoHolds().contains(this)){
            throw new RuntimeException("Cargo Hold not found in the ship.");
        } else {
            getShipBoard().getCondensedShip().removeCargoHold(this);
        }

    }
}
