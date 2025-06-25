package Model.Ship;

import Model.Enums.Crewmates;
import Model.Enums.Direction;
import Model.Enums.Good;
import Model.Ship.Components.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CondensedShip implements Serializable {
    private final List<Cabin> cabins;
    private final List<BatteryCompartment> batteryCompartments;
    private final List<CargoHold> cargoHolds;
    private final List<Cannon> cannons;
    private final List<Engine> enginesList;
    private final List<AlienLifeSupport> alienSupports;
    private final EnginesCounter engines;
    private final AlienCounter aliens;
    private final ShieldCounter shields;

    public CondensedShip() {
        this.cabins = new ArrayList<>();
        this.batteryCompartments = new ArrayList<>();
        this.cargoHolds = new ArrayList<>();
        this.cannons = new ArrayList<>();
        this.alienSupports = new ArrayList<>();
        this.enginesList = new ArrayList<>();
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

    public int getTotalHumans() {
        int totalHumans = 0;

        for (Cabin cabin : cabins) {
            Crewmates crewmates = cabin.getOccupants();
            if(crewmates == Crewmates.SINGLE_HUMAN) {
                totalHumans += 1;
            } else if (crewmates == Crewmates.DOUBLE_HUMAN) {
                totalHumans += 2;
            }
        }

        return totalHumans;
    }

    public DoubleCannonsCounter getTotalDoubleCannons() {
        int frontCannons = 0;
        int otherCannons = 0;
        for (Cannon cannon : cannons) {

            if (cannon.isDouble()) {
                if(cannon.getOrientation()== Direction.UP){
                    frontCannons++;
                } else {
                    otherCannons++;
                }
            }
        }
        return new DoubleCannonsCounter(frontCannons, otherCannons);
    }

    public GoodCounter goodToDiscard(int num){
        List<Good> allGoods = cargoHolds.stream()
                .flatMap(cargo -> Arrays.stream(cargo.getGoods()))
                .collect(Collectors.toList());
        GoodCounter counter = new GoodCounter();
        for(Good g : allGoods){
            if(num== 0)
                return counter;
            if(g == Good.RED){
                counter.addGood(g);
                num--;
            }
        }
        if(num > 0){
            for(Good g : allGoods){
                if(num== 0)
                    return counter;
                if(g == Good.YELLOW){
                    counter.addGood(g);
                    num--;
                }
            }
        }
        if(num > 0){
            for(Good g : allGoods){
                if(num== 0)
                    return counter;
                if(g == Good.GREEN){
                    counter.addGood(g);
                    num--;
                }
            }
        }
        if(num > 0){
            for(Good g : allGoods){
                if(num== 0)
                    return counter;
                if(g == Good.BLUE){
                    counter.addGood(g);
                    num--;
                }
            }
        }
        return counter;
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
    public void addBatteryCompartment(BatteryCompartment batteryCompartment) { batteryCompartments.add(batteryCompartment); }
    public void removeBatteryCompartment(BatteryCompartment batteryCompartment) { batteryCompartments.remove(batteryCompartment); }

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

    public List<AlienLifeSupport> getAlienSupports() { return alienSupports; }
    public void addAlienSupport(AlienLifeSupport alienSupport) { alienSupports.add(alienSupport); }
    public void removeAlienSupport(AlienLifeSupport alienSupport) { alienSupports.remove(alienSupport); }

    public List<Engine> getEnginesList() {
        return enginesList;
    }
    public void addEngine(Engine engine) {
        enginesList.add(engine);
    }
    public void removeEngine(Engine engine) {
        enginesList.remove(engine);
    }

    public EnginesCounter getEngines() {
        return engines;
    }

    public ShieldCounter getShields() {
        return shields;
    }

    public AlienCounter getAliens() {return aliens;}

    public boolean canContainBrown(){
        for (Cabin cabin : cabins) {
            if (cabin.getCanContainBrown()) {
                return true;
            }
        }
        return false;
    }

    public boolean canContainPurple(){
        for (Cabin cabin : cabins) {
            if (cabin.getCanContainPurple()) {
                return true;
            }
        }
        return false;
    }

    public double getBasePower(){
        double power = 0;
        for(Cannon cannon : this.getCannons()){
            if(!cannon.isDouble()){
                if(cannon.getOrientation()== Direction.UP){
                    power++;
                } else {
                    power+=0.5;
                }
            }
        }
        if(power>0){
            if(this.getAliens().hasPurpleAlien()){
                power+=2;
            }
        }
        return power;
    }

    public double getMaxPower(){
        double power = getBasePower();
        DoubleCannonsCounter doubleCannons = this.getTotalDoubleCannons();
        power += doubleCannons.getFrontCannons() * 2;
        power += doubleCannons.getOtherCannons();

        return power;
    }

    public double getBaseThrust(){
        double thrust = 0;
        thrust += this.getEngines().getSingleEngines();
        if(thrust>0){
            if(this.getAliens().hasBrownAlien()){
                thrust += 2;
            }
        }
        return thrust;
    }

    public double getMaxThrust(){
        double thrust = getBaseThrust();
        thrust += this.getEngines().getDoubleEngines() * 2;
        return thrust;
    }


}
