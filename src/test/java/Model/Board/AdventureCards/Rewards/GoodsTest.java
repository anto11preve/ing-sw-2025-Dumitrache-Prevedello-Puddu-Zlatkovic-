package Model.Board.AdventureCards.Rewards;

import Model.Enums.Good;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoodsTest {

    @Test
    public void testConstructor() {
        List<Good> goodsList = new ArrayList<>();
        goodsList.add(Good.RED);
        goodsList.add(Good.BLUE);
        
        Goods goods = new Goods(goodsList);
        
        // Count the goods and check if specific goods are present
        int count = 0;
        boolean hasRed = false;
        boolean hasBlue = false;
        
        for (Good g : goods) {
            count++;
            if (g == Good.RED) hasRed = true;
            if (g == Good.BLUE) hasBlue = true;
        }
        
        assertEquals(2, count);
        assertTrue(hasRed);
        assertTrue(hasBlue);
    }
    
    @Test
    public void testEmptyList() {
        Goods goods = new Goods(new ArrayList<>());
        
        // Check if the iterator returns any elements
        int count = 0;
        for (Good g : goods) {
            count++;
        }
        
        assertEquals(0, count);
    }
    
    @Test
    public void testNullList() {
        assertThrows(NullPointerException.class, () -> new Goods(null));
    }
    
    @Test
    public void testAllGoodTypes() {
        List<Good> goodsList = new ArrayList<>();
        goodsList.add(Good.RED);
        goodsList.add(Good.BLUE);
        goodsList.add(Good.GREEN);
        goodsList.add(Good.YELLOW);
        
        Goods goods = new Goods(goodsList);
        
        // Count the goods and check if all types are present
        int count = 0;
        boolean hasRed = false;
        boolean hasBlue = false;
        boolean hasGreen = false;
        boolean hasYellow = false;
        
        for (Good g : goods) {
            count++;
            if (g == Good.RED) hasRed = true;
            if (g == Good.BLUE) hasBlue = true;
            if (g == Good.GREEN) hasGreen = true;
            if (g == Good.YELLOW) hasYellow = true;
        }
        
        assertEquals(4, count);
        assertTrue(hasRed);
        assertTrue(hasBlue);
        assertTrue(hasGreen);
        assertTrue(hasYellow);
    }
}