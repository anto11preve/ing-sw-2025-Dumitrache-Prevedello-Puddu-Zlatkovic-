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

public class AlienCounterTest {

    @Test
    public void testConstructor() {
        AlienCounter counter = new AlienCounter();
        assertFalse(counter.hasBrownAlien());
        assertFalse(counter.hasPurpleAlien());
    }
    
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