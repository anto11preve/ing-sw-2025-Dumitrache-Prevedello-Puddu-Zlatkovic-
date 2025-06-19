package Model.Ship;

import Model.Enums.AlienColor;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;
import Model.Ship.Components.AlienLifeSupport;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.Cannon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CondensedShipTest {

    @Test
    public void testConstructor() {
        CondensedShip ship = new CondensedShip();
        
        assertTrue(ship.getCabins().isEmpty());
        assertTrue(ship.getBatteryCompartments().isEmpty());
        assertTrue(ship.getCargoHolds().isEmpty());
        assertTrue(ship.getCannons().isEmpty());
        assertTrue(ship.getAlienSupports().isEmpty());
        assertEquals(0, ship.getEngines().getSingleEngines());
        assertEquals(0, ship.getEngines().getDoubleEngines());
        assertEquals(0, ship.getShields().getNorthShields());
        assertEquals(0, ship.getShields().getEastShields());
        assertEquals(0, ship.getShields().getSouthShields());
        assertEquals(0, ship.getShields().getWestShields());
        assertFalse(ship.getAliens().hasPurpleAlien());
        assertFalse(ship.getAliens().hasBrownAlien());
    }
    
    @Test
    public void testAddComponents() {
        CondensedShip ship = new CondensedShip();
        
        // Add a cabin
        Cabin cabin = new Cabin(Card.CABIN, 
                               ConnectorType.UNIVERSAL, 
                               ConnectorType.UNIVERSAL, 
                               ConnectorType.UNIVERSAL, 
                               ConnectorType.UNIVERSAL,
                               Crewmates.EMPTY);
        cabin.setOccupants(Crewmates.DOUBLE_HUMAN);
        ship.addCabin(cabin);
        assertEquals(1, ship.getCabins().size());
        
        // Add a battery compartment
        BatteryCompartment battery = new BatteryCompartment(Card.BATTERY_COMPARTMENT, 
                                                          ConnectorType.UNIVERSAL, 
                                                          ConnectorType.UNIVERSAL, 
                                                          ConnectorType.UNIVERSAL, 
                                                          ConnectorType.UNIVERSAL,
                                                          2);
        battery.setBatteries(2);
        ship.addBatteryCompartment(battery);
        assertEquals(1, ship.getBatteryCompartments().size());
        
        // Add a cannon
        Cannon cannon = new Cannon(Card.CANNON, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL, 
                                  ConnectorType.UNIVERSAL,
                                  false);
        ship.addCannon(cannon);
        assertEquals(1, ship.getCannons().size());
        
        // Add alien support
        AlienLifeSupport alienSupport = new AlienLifeSupport(Card.ALIEN_LIFE_SUPPORT, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL,
                                                           AlienColor.PURPLE);
        ship.addAlienSupport(alienSupport);
        assertEquals(1, ship.getAlienSupports().size());
    }
    
    @Test
    public void testGetTotalCrew() {
        CondensedShip ship = new CondensedShip();
        
        // Add a cabin with double human
        Cabin cabin1 = new Cabin(Card.CABIN, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL,
                                Crewmates.EMPTY);
        cabin1.setOccupants(Crewmates.DOUBLE_HUMAN);
        ship.addCabin(cabin1);
        
        // Add a cabin with single human
        Cabin cabin2 = new Cabin(Card.CABIN, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL,
                                Crewmates.EMPTY);
        cabin2.setOccupants(Crewmates.SINGLE_HUMAN);
        ship.addCabin(cabin2);
        
        // Add an empty cabin
        Cabin cabin3 = new Cabin(Card.CABIN, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL,
                                Crewmates.EMPTY);
        cabin3.setOccupants(Crewmates.EMPTY);
        ship.addCabin(cabin3);
        
        // Total crew should be 2 + 1 + 0 = 3
        assertEquals(3, ship.getTotalCrew());
    }
    
    @Test
    public void testGetTotalBatteries() {
        CondensedShip ship = new CondensedShip();
        
        // Add a battery compartment with 2 batteries
        BatteryCompartment battery1 = new BatteryCompartment(Card.BATTERY_COMPARTMENT, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL,
                                                           2);
        battery1.setBatteries(2);
        ship.addBatteryCompartment(battery1);
        
        // Add a battery compartment with 3 batteries
        BatteryCompartment battery2 = new BatteryCompartment(Card.BATTERY_COMPARTMENT, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL, 
                                                           ConnectorType.UNIVERSAL,
                                                           3);
        battery2.setBatteries(3);
        ship.addBatteryCompartment(battery2);
        
        // Total batteries should be 2 + 3 = 5
        assertEquals(5, ship.getTotalBatteries());
    }
    
    @Test
    public void testAlienSupport() {
        CondensedShip ship = new CondensedShip();
        
        // Add a cabin that can contain purple aliens
        Cabin cabin1 = new Cabin(Card.CABIN, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL,
                                Crewmates.EMPTY);
        cabin1.setCanContainPurple(true);
        ship.addCabin(cabin1);
        
        // Add a cabin that can contain brown aliens
        Cabin cabin2 = new Cabin(Card.CABIN, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL, 
                                ConnectorType.UNIVERSAL,
                                Crewmates.EMPTY);
        cabin2.setCanContainBrown(true);
        ship.addCabin(cabin2);
        
        // Ship should be able to contain both types of aliens
        assertTrue(ship.canContainPurple());
        assertTrue(ship.canContainBrown());
        
        // Set aliens
        ship.getAliens().setPurpleAlien(true);
        ship.getAliens().setBrownAlien(true);
        
        assertTrue(ship.getAliens().hasPurpleAlien());
        assertTrue(ship.getAliens().hasBrownAlien());
    }
}