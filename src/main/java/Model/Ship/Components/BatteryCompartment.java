package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;

public class BatteryCompartment extends SpaceshipComponent {
    private final int capacity;
    private int batteries;

    public BatteryCompartment(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, int capacity) {
        super(Type, front, rear, left, right);
        this.capacity = capacity;
        this.batteries = capacity;
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

    public void removeBattery() throws IllegalStateException{
        if (batteries > 0) {
            batteries--;
        } else {
            throw new IllegalStateException("No batteries left to remove");
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
