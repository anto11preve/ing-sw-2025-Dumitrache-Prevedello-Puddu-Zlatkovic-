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

    public boolean addGood(Good good, int index) {
        if (good == Good.RED && !isSpecial) {
            return false;
        }
        if (index >= 0 && index < goods.length) {
            if (goods[index] == null) {
                goods[index] = good;
                return true;
            } else {
                // Slot is already occupied
                return false;
            }
        }
    }

    public void removeGood(int index) {
        if (index >= 0 && index < goods.length) {
            goods[index] = null;
        }
    }

    /*
    public void added() {
        //to do
    }
    public void removed() {
        //to do
    }
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    */
}
