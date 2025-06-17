package Model.Utils;

import Model.Enums.CardLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardLevelMapperTest {

    @Test
    public void testMapJsonLevelToCardLevel() {
        // Test all valid mappings
        assertEquals(CardLevel.LEARNER, CardLevelMapper.mapJsonLevelToCardLevel("TRIAL"));
        assertEquals(CardLevel.LEVEL_ONE, CardLevelMapper.mapJsonLevelToCardLevel("LEVEL1"));
        assertEquals(CardLevel.LEVEL_TWO, CardLevelMapper.mapJsonLevelToCardLevel("LEVEL2"));
        assertEquals(CardLevel.LEVEL_THREE, CardLevelMapper.mapJsonLevelToCardLevel("LEVEL3"));
        
        // Test invalid mapping
        assertThrows(IllegalArgumentException.class, () -> CardLevelMapper.mapJsonLevelToCardLevel("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> CardLevelMapper.mapJsonLevelToCardLevel(""));
        assertThrows(NullPointerException.class, () -> CardLevelMapper.mapJsonLevelToCardLevel(null));
    }
}