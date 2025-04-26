package Model.Ship;

import Model.Enums.Crewmates;
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
    private List<Cannon> cannons;
    private EnginesCounter engines;
    private AlienCounter aliens;
    private ShieldCounter shields;

    public CondensedShip() {
        this.cabins = new ArrayList<>();
        this.batteryCompartments = new ArrayList<>();
        this.cargoHolds = new ArrayList<>();
        this.cannons = new ArrayList<>();
        this.engines = new EnginesCounter();
        this.aliens = new AlienCounter();
        this.shields = new ShieldCounter();
    }

    public int getTotalBatteries() {
        int totalBatteries = 0;

        for (BatteryCompartment batteryCompartment : batteryCompartments) {
            totalBatteries += batteryCompartment.getBatteries();
        }

        return totalBatteries;
    }
    public int getTotalCrew() {
        int totalCrew = 0;

        for (Cabin cabin : cabins) {
            Crewmates crewmates = cabin.getOccupants();
            if(crewmates == Crewmates.EMPTY) {
                totalCrew += 0;
            } else if (crewmates == Crewmates.DOUBLE_HUMAN) {
                totalCrew += 2;
            } else {
                totalCrew += 1;
            }
        }

        return totalCrew;
    }

    public DoubleCannonsCounter getTotalDoubleCannons() {
        int frontCannons = 0;
        int otherCannons = 0;
        for (Cannon cannon : cannons) {

            if (cannon.doubleCannon()) {
                if(cannon.getOrientation()==0){
                    frontCannons++;
                } else {
                    otherCannons++;
                }
            }
        }
        return new DoubleCannonsCounter(frontCannons, otherCannons);
    }


    public List<Cabin> getCabins() {
        return cabins;
    }
    public void addCabin(Cabin cabin) {
        cabins.add(cabin);
    }
    public void removeCabin(Cabin cabin) {
        cabins.remove(cabin);
    }

    public List<BatteryCompartment> getBatteryCompartments() {
        return batteryCompartments;
    }
    public void addBatteryCompartment(BatteryCompartment batteryCompartment) {
        batteryCompartments.add(batteryCompartment);
    }
    public void removeBatteryCompartment(BatteryCompartment batteryCompartment) {
        batteryCompartments.remove(batteryCompartment);
    }

    public List<CargoHold> getCargoHolds() {
        return cargoHolds;
    }
    public void addCargoHold(CargoHold cargoHold) {
        cargoHolds.add(cargoHold);
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

    public EnginesCounter getEngines() {
        return engines;
    }

    public ShieldCounter getShields() {
        return shields;
    }

    public AlienCounter getAliens() {return aliens;}

}
