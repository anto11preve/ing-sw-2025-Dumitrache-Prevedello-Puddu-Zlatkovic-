package Model.Ship;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the AlienCounter class which tracks alien presence on the ship.
 * Tests alien flag management and crew counting functionality.
 */
public class AlienCounterTest {

    /**
     * Tests the constructor to ensure a new AlienCounter is properly initialized:
     * - No aliens should be present initially
     * - Both brown and purple alien flags should be false
     */
    @Test
    public void testConstructor() {
        AlienCounter counter = new AlienCounter();
        assertFalse(counter.hasBrownAlien());
        assertFalse(counter.hasPurpleAlien());
    }
    
    /**
     * Tests setting alien presence flags:
     * - Each alien type can be set independently
     * - Setting one alien type should not affect the other
     * - Flags can be toggled between true and false
     */
    @Test
    public void testSetAliens() {
        AlienCounter counter = new AlienCounter();
        
        counter.setBrownAlien(true);
        assertTrue(counter.hasBrownAlien());
        assertFalse(counter.hasPurpleAlien());
        
        counter.setPurpleAlien(true);
        assertTrue(counter.hasBrownAlien());
        assertTrue(counter.hasPurpleAlien());
        
        counter.setBrownAlien(false);
        assertFalse(counter.hasBrownAlien());
        assertTrue(counter.hasPurpleAlien());
    }
    
    /**
     * Tests counting aliens and their impact on crew capacity:
     * - Each alien type should be detected in cabins
     * - Empty cabins should not affect the count
     * - The total count should reflect the crew capacity used by aliens
     * - Alien presence flags should be updated based on the count
     */
    @Test
    public void testCount() {
        AlienCounter counter = new AlienCounter();
        List<SpaceshipComponent> components = new ArrayList<>();
        
        // Create cabins with aliens using the simplified constructor
        Cabin brownCabin = new Cabin(true, "brown");
        Cabin purpleCabin = new Cabin(true, "purple");
        
        // Create a cabin without aliens
        Cabin emptyCabin = new Cabin(Card.CABIN, 
                                    ConnectorType.UNIVERSAL, 
                                    ConnectorType.UNIVERSAL, 
                                    ConnectorType.UNIVERSAL, 
                                    ConnectorType.UNIVERSAL,
                                    Crewmates.EMPTY);
        
        components.add(brownCabin);
        components.add(purpleCabin);
        components.add(emptyCabin);
        
        // Count should return 4 (2 aliens × 2 crew each)
        assertEquals(4, counter.count(components));
        
        // Flags should be set
        assertTrue(counter.hasBrownAlien());
        assertTrue(counter.hasPurpleAlien());
    }
}