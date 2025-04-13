package Model.Ship;

import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.CargoHold;

import java.util.ArrayList;
import java.util.List;

class CondensedShip {
    private List<Cabin> cabins;
    private List<BatteryCompartment> batteryCompartments;
    private List<CargoHold> cargoHolds;
    private int[] engines;  //contiene al primo indice i motori singoli, il secondo quelli doppi
    private int[] aliens;   //contiene al primo indice alieni marroni, il secondo quelli viola
    private List<Cannon> cannons;
    private int[] shields;  //contiene il numero di scudi che proteggono i versanti Nord(0), Ovest(1), Sud(2), Est(3)

    public CondensedShip() {
        this.cabins = new ArrayList<>();
        this.batteryCompartments = new ArrayList<>();
        this.cargoHolds = new ArrayList<>();
        this.engines = new int[2];
        this.aliens = new int[2];
        this.cannons = new ArrayList<>();
        this.shields = new int[4];
    }

    public void addCabin(Cabin cabin) {
        cabins.add(cabin);
    }
    public List<Cabin> getCabins() {
        return cabins;
    }
    public void removeCabin(Cabin cabin) {
        cabins.remove(cabin);
    }

    public void addBatteryCompartment(BatteryCompartment batteryCompartment) {
        batteryCompartments.add(batteryCompartment);
    }
    public List<BatteryCompartment> getBatteryCompartments() {
        return batteryCompartments;
    }
    public void removeBatteryCompartment(BatteryCompartment batteryCompartment) {
        batteryCompartments.remove(batteryCompartment);
    }

    public void addCargoHold(CargoHold cargoHold) {
        cargoHolds.add(cargoHold);
    }
    public List<CargoHold> getCargoHolds() {
        return cargoHolds;
    }
    public void removeCargoHold(CargoHold cargoHold) {
        cargoHolds.remove(cargoHold);
    }

    public List<Cannon> getCannons() {
        return cannons;
    }
    public void addCannon(Cannon cannon) {
        cannons.add(cannon);
    }
    public void removeCannon(Cannon cannon) {
        cannons.remove(cannon);
    }

    public int[] getEngines() {
        return engines;
    }
    public void setEngine(int index, int value) {
        engines[index] = value;
    }

    public int[] getShields() {
        return shields;
    }
    public void setShield(int index, int value) {
        shields[index] = value;
    }

}
