package Model.Ship;

import Model.Enums.Crewmates;
import Model.Enums.Direction;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.CargoHold;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta una versione compatta della nave, usata per riepiloghi e statistiche.
 */
public class CondensedShip {

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

    /**
     * Ritorna il numero totale di batterie disponibili.
     */
    public int getTotalBatteries() {
        int totalBatteries = 0;
        for (BatteryCompartment batteryCompartment : batteryCompartments) {
            totalBatteries += batteryCompartment.getBatteries();
        }
        return totalBatteries;
    }

    /**
     * Ritorna il numero totale di membri dell'equipaggio, tenendo conto delle varie configurazioni.
     */
    public int getTotalCrew() {
        int totalCrew = 0;

        for (Cabin cabin : cabins) {
            Crewmates crewmates = cabin.getOccupants();
            switch (crewmates) {
                case EMPTY -> totalCrew += 0;
                case DOUBLE_HUMAN -> totalCrew += 2;
                default -> totalCrew += 1;
            }
        }

        return totalCrew;
    }

    /**
     * Ritorna il numero di cannoni doppi orientati verso il fronte della nave (Direction.UP) e quelli orientati altrove.
     */
    public DoubleCannonsCounter getTotalDoubleCannons() {
        int frontCannons = 0;
        int otherCannons = 0;
        for (Cannon cannon : cannons) {
            if (cannon.isDouble()) {
                if (cannon.getOrientation() == Direction.UP) {
                    frontCannons++;
                } else {
                    otherCannons++;
                }
            }
        }
        return new DoubleCannonsCounter(frontCannons, otherCannons);
    }

    // === Getter e aggiunta/rimozione per ciascuna categoria di componente ===

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

    public AlienCounter getAliens() {
        return aliens;
    }
}

