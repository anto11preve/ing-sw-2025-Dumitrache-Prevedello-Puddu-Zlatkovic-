package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Good;
import Model.Enums.Side;
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

        this.capacity = json.has("cargoCapacity") ? json.get("cargoCapacity").getAsInt() : 2; // Default to 2 if unspecified
        this.goods = new Good[capacity];
        this.isSpecial = json.has("isSpecial") && json.get("isSpecial").getAsBoolean();
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

    public String[] renderSmall() {
        String[] righe = new String[3];
        righe[0] = String.format("╔═ %d ═╗", this.getConnectorAt(Side.FRONT).getNumero());
        String sx = (this.getConnectorAt(Side.LEFT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.LEFT).getNumero()) : "║");
        String dx = (this.getConnectorAt(Side.RIGHT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.RIGHT).getNumero()) : "║");
        righe[1] = String.format("%s CAR %s", sx, dx);
        righe[2] = String.format("╚═ %d ═╝", this.getConnectorAt(Side.REAR).getNumero());
        return righe;
    }

    public void renderBig() {
        // Riga superiore
        System.out.printf("╔══  %d  ══╗\n", this.getConnectorAt(Side.FRONT).getNumero());

        System.out.print("║  BATRY  ║\n");

        System.out.printf("%s%s%s\n",
                (this.getConnectorAt(Side.LEFT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.LEFT).getNumero()) : "║"),
                "    "+
                        (this.getOrientation().getFreccia()),
                "    "+
                        (this.getConnectorAt(Side.RIGHT).getNumero() > 0 ? String.valueOf(this.getConnectorAt(Side.RIGHT).getNumero()) : "║")
        );

        System.out.print("║  ");
        switch(this.getCapacity()){
            case 1:
                System.out.print("  ");
                for (Good good : this.getGoods()) {
                    if (good == null) {
                        System.out.print("☐ ");
                    } else {
                        // Prendo la prima lettera del nome dell'enum
                        System.out.print(good.name().charAt(0) + " ");
                    }
                };
                System.out.print("  ");
                break;
            case 2:
                System.out.print(" ");
                for (Good good : this.getGoods()) {
                    if (good == null) {
                        System.out.print("☐ ");
                    } else {
                        // Prendo la prima lettera del nome dell'enum
                        System.out.print(good.name().charAt(0) + " ");
                    }
                }
                System.out.print(" ");
                break;
            case 3:
                System.out.print(" ");
                for (Good good : this.getGoods()) {
                    if (good == null) {
                        System.out.print("☐ ");
                    } else {
                        // Prendo la prima lettera del nome dell'enum
                        System.out.print(good.name().charAt(0) + " ");
                    }
                }
                break;
            default:
                System.out.printf("║ %d slots ║\n", this.getCapacity());
        }
        System.out.print(" ║\n");

        // Riga inferiore
        System.out.printf("╚══  %d  ══╝\n", this.getConnectorAt(Side.REAR).getNumero());
    }
}
