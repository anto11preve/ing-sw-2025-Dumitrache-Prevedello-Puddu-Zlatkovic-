package Model;

import java.util.ArrayList;
import java.util.List;

// Classe principale del giocatore
class Player {
    private final String name;
    private ShipBoard shipBoard;
    private List<Cabin> cabins;
    private List<BatteryCompartment> batteryCompartments;
    private List<CargoHold> cargoHolds;
    private int credits;
    private int position;
    private int junk;

    public Player(String name) {
        this.name = name;
        this.shipBoard = new ShipBoard();
        this.cabins = new ArrayList<>();
        this.batteryCompartments = new ArrayList<>();
        this.cargoHolds = new ArrayList<>();
        this.credits = 0;
        this.position = -1;
        this.junk = 0;
    }
    public String getName(){ return name; }
    public void move(int steps) {
        position += steps;
        if (position < 0) position = (position + 24) % 24;
        else if (position >= 24) position %= 24;
    }

    public int getPosition() { return position; }

    public void payCredits(int amount) throws IllegalArgumentException {
        if (credits >= amount) credits -= amount;
        else throw new IllegalArgumentException("Not enough credits");
    }

    public void receiveCredits(int amount) { credits += amount; }

    public int getCredits() { return credits; }

    public Crewmates getPeople(int index) {
        if (index >= 0 && index < cabins.size()) {
            Cabin cabin = cabins.get(index);
            return cabin.getOccupants();
        } else {
            throw new IndexOutOfBoundsException("Invalid cabin index");
        }
    }

    public int getBatteries(int index) {
        if (index >= 0 && index < batteryCompartments.size()) {
            BatteryCompartment batteryCompartment = batteryCompartments.get(index);
            return batteryCompartment.getBatteries();
        } else {
            throw new IndexOutOfBoundsException("Invalid battery compartment index");
        }
    }

    public void useBatteries(int num) {
        //to do
    }

    public Good[] getGood(int index) {
        if (index >= 0 && index < cargoHolds.size()) {
            CargoHold cargoHold = cargoHolds.get(index);
            return cargoHold.getGoods();
        } else {
            throw new IndexOutOfBoundsException("Invalid cargo hold index");
        }
    }

    public void setGood(Good[] goods) {
        //to do
    }

    public void quitGame() {
        //to do
    }

    public void addJunk() { junk ++; }

    public int getJunk() { return junk; }
}
