package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Good;

public class CargoHold extends SpaceshipComponent {
    private final int capacity;
    private Good[] goods;
    private boolean isSpecial;

    public CargoHold(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, int capacity, boolean isSpecial) {
        super(Type, front, rear, left, right);
        this.capacity = capacity;
        this.goods = new Good[capacity];
        this.isSpecial = isSpecial;
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
