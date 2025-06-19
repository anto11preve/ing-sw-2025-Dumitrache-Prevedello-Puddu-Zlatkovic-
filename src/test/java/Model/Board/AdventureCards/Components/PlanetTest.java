package Model.Board.AdventureCards.Components;

import Model.Enums.Good;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanetTest {

    @Test
    public void testConstructor() {
        List<Good> goods = new ArrayList<>();
        goods.add(Good.RED);
        goods.add(Good.BLUE);
        
        Planet planet = new Planet("Test Planet", goods);
        assertEquals("Test Planet", planet.getName());
        
        // Count the goods in the landing reward and check if specific goods are present
        int goodsCount = 0;
        boolean hasRed = false;
        boolean hasBlue = false;
        
        for (Good g : planet.getLandingReward()) {
            goodsCount++;
            if (g == Good.RED) hasRed = true;
            if (g == Good.BLUE) hasBlue = true;
        }
        
        assertEquals(2, goodsCount);
        assertTrue(hasRed);
        assertTrue(hasBlue);
    }
}