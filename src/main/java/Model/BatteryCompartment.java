package Model;

class BatteryCompartment extends SpaceshipComponent {
    private int capacity;
    private int batteries;

    public int getCapacity() {
        return capacity;
    }

    public int getBatteries() {
        return batteries;
    }

    public void removeBattery() throws IllegalStateException{
        if (batteries > 0) {
            batteries--;
        } else {
            throw new IllegalStateException("No batteries left to remove");
        }
    }
}
