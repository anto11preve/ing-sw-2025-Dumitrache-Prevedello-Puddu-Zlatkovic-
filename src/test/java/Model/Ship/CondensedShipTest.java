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

/**
 * Tests for the CondensedShip class which maintains a simplified representation of the ship's
 * components and their properties for game mechanics like crew count, batteries, and alien support.
 */
public class CondensedShipTest {

    /**
     * Tests the constructor to ensure a new CondensedShip is properly initialized:
     * - All component lists should be empty
     * - All counters should be initialized to zero
     * - No aliens should be present
     */
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
    
    /**
     * Tests adding different types of components to the CondensedShip:
     * - Adding a cabin should update the cabins list
     * - Adding a battery compartment should update the battery compartments list
     * - Adding a cannon should update the cannons list
     * - Adding alien life support should update the alien supports list
     * - Each list should correctly track the number of components added
     */
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
    
    /**
     * Tests calculating the total crew count from all cabins:
     * - Double human cabins should count as 2 crew
     * - Single human cabins should count as 1 crew
     * - Empty cabins should count as 0 crew
     * - The total should be the sum of all cabin occupants
     */
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
    
    /**
     * Tests calculating the total batteries from all battery compartments:
     * - Each battery compartment can hold a specific number of batteries
     * - The total should be the sum of all batteries across all compartments
     */
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
    
    /**
     * Tests alien support functionality:
     * - Cabins can be configured to support different alien types
     * - The ship should track which alien types can be supported
     * - The ship should track which aliens are currently on board
     */
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