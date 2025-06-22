package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.Planet;
import Model.Enums.CardLevel;
import Model.Enums.Good;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Planets class which represents planet encounters.
 * Tests constructor, JSON parsing, iteration, and visualization functionality.
 */
public class PlanetsTest {

    /**
     * Tests the basic constructor with planets list.
     */
    @Test
    public void testConstructor() {
        List<Planet> planets = new ArrayList<>();
        List<Good> goods = new ArrayList<>();
        goods.add(Good.RED);
        goods.add(Good.YELLOW);
        planets.add(new Planet("Test Planet", goods));
        
        Planets card = new Planets(1, CardLevel.LEVEL_ONE, 2, planets);
        assertEquals(1, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals("Pianeti", card.getName());
        assertEquals("", card.getDescription());
        assertEquals(2, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
            assertEquals("Test Planet", p.getName());
            
            // Count the goods in the landing reward
            int goodsCount = 0;
            for (Good g : p.getLandingReward()) {
                goodsCount++;
            }
            assertEquals(2, goodsCount);
        }
        assertEquals(1, count);
    }

    /**
     * Tests JSON constructor with full planet and penalty data.
     */
    @Test
    public void testJsonConstructorWithFullData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 2);
        json.addProperty("level", "LEVEL_ONE");
        
        JsonArray planetsArray = new JsonArray();
        JsonObject planet = new JsonObject();
        planet.addProperty("name", "Test Planet");
        JsonArray goodsArray = new JsonArray();
        goodsArray.add(new JsonPrimitive("RED"));
        goodsArray.add(new JsonPrimitive("BLUE"));
        planet.add("goods", goodsArray);
        planetsArray.add(planet);
        json.add("planets", planetsArray);
        
        JsonObject landingPenalty = new JsonObject();
        landingPenalty.addProperty("value", 2);
        json.add("landingPenalty", landingPenalty);
        
        Planets card = new Planets(json);
        assertEquals(2, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals(2, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
            assertEquals("Test Planet", p.getName());
            
            // Count the goods in the landing reward
            int goodsCount = 0;
            for (Good g : p.getLandingReward()) {
                goodsCount++;
            }
            assertEquals(2, goodsCount);
        }
        assertEquals(1, count);
    }
    
    /**
     * Tests JSON constructor with minimal data (using defaults).
     */
    @Test
    public void testJsonConstructorWithMinimalData() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 3);
        json.addProperty("level", "LEVEL_ONE");
        
        Planets card = new Planets(json);
        assertEquals(3, card.getId());
        assertEquals(CardLevel.LEVEL_ONE, card.getLevel());
        assertEquals(0, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
        }
        assertEquals(0, count);
    }

    /**
     * Tests JSON constructor with multiple planets.
     */
    @Test
    public void testJsonConstructorWithMultiplePlanets() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 4);
        json.addProperty("level", "LEVEL_TWO");
        
        JsonArray planetsArray = new JsonArray();
        
        // First planet
        JsonObject planet1 = new JsonObject();
        planet1.addProperty("name", "Planet Alpha");
        JsonArray goods1 = new JsonArray();
        goods1.add(new JsonPrimitive("RED"));
        planet1.add("goods", goods1);
        planetsArray.add(planet1);
        
        // Second planet
        JsonObject planet2 = new JsonObject();
        planet2.addProperty("name", "Planet Beta");
        JsonArray goods2 = new JsonArray();
        goods2.add(new JsonPrimitive("BLUE"));
        goods2.add(new JsonPrimitive("GREEN"));
        goods2.add(new JsonPrimitive("YELLOW"));
        planet2.add("goods", goods2);
        planetsArray.add(planet2);
        
        json.add("planets", planetsArray);
        
        Planets card = new Planets(json);
        assertEquals(4, card.getId());
        
        int count = 0;
        for (Planet p : card) {
            count++;
            if (count == 1) {
                assertEquals("Planet Alpha", p.getName());
            } else if (count == 2) {
                assertEquals("Planet Beta", p.getName());
            }
        }
        assertEquals(2, count);
    }

    /**
     * Tests JSON constructor with only penalty data.
     */
    @Test
    public void testJsonConstructorWithPenaltyOnly() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 5);
        json.addProperty("level", "LEVEL_TWO");
        
        JsonObject landingPenalty = new JsonObject();
        landingPenalty.addProperty("value", 3);
        json.add("landingPenalty", landingPenalty);
        
        Planets card = new Planets(json);
        assertEquals(5, card.getId());
        assertEquals(3, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
        }
        assertEquals(0, count); // No planets
    }

    /**
     * Tests JSON constructor with planets but no penalty.
     */
    @Test
    public void testJsonConstructorWithPlanetsNoPenalty() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 6);
        json.addProperty("level", "LEVEL_TWO");
        
        JsonArray planetsArray = new JsonArray();
        JsonObject planet = new JsonObject();
        planet.addProperty("name", "Solo Planet");
        JsonArray goodsArray = new JsonArray();
        goodsArray.add(new JsonPrimitive("GREEN"));
        planet.add("goods", goodsArray);
        planetsArray.add(planet);
        json.add("planets", planetsArray);
        
        Planets card = new Planets(json);
        assertEquals(6, card.getId());
        assertEquals(0, card.getLandingPenalty().getAmount()); // Default penalty
        
        int count = 0;
        for (Planet p : card) {
            count++;
            assertEquals("Solo Planet", p.getName());
        }
        assertEquals(1, count);
    }

    /**
     * Tests constructor with empty planets list.
     */
    @Test
    public void testConstructorWithEmptyPlanets() {
        List<Planet> emptyPlanets = new ArrayList<>();
        
        Planets card = new Planets(7, CardLevel.LEVEL_ONE, 1, emptyPlanets);
        assertEquals(7, card.getId());
        assertEquals(1, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
        }
        assertEquals(0, count);
    }

    /**
     * Tests constructor with multiple planets.
     */
    @Test
    public void testConstructorWithMultiplePlanets() {
        List<Planet> planets = new ArrayList<>();
        planets.add(new Planet("Planet 1", Arrays.asList(Good.RED)));
        planets.add(new Planet("Planet 2", Arrays.asList(Good.BLUE, Good.GREEN)));
        planets.add(new Planet("Planet 3", Arrays.asList(Good.YELLOW)));
        
        Planets card = new Planets(8, CardLevel.LEVEL_TWO, 4, planets);
        assertEquals(8, card.getId());
        assertEquals(4, card.getLandingPenalty().getAmount());
        
        int count = 0;
        for (Planet p : card) {
            count++;
        }
        assertEquals(3, count);
    }

    /**
     * Tests the visualize method.
     */
    @Test
    public void testVisualize() {
        List<Planet> planets = new ArrayList<>();
        planets.add(new Planet("Test Planet", Arrays.asList(Good.RED, Good.BLUE)));
        
        Planets card = new Planets(9, CardLevel.LEVEL_TWO, 2, planets);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        card.visualize();

        System.setOut(originalOut);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Landing Penalty:"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("DaysPenalty"));
        assertTrue(output.contains("Planets:"));
        assertTrue(output.contains("Test Planet"));
        assertTrue(output.contains("RED"));
        assertTrue(output.contains("BLUE"));
    }

    /**
     * Tests the visualize method with no planets.
     */
    @Test
    public void testVisualizeWithNoPlanets() {
        List<Planet> emptyPlanets = new ArrayList<>();
        
        Planets card = new Planets(10, CardLevel.LEVEL_ONE, 1, emptyPlanets);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        card.visualize();

        System.setOut(originalOut);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Landing Penalty:"));
        assertTrue(output.contains("Planets:"));
        assertTrue(output.contains("(no planets)"));
    }

    /**
     * Tests iterator functionality.
     */
    @Test
    public void testIterator() {
        List<Planet> planets = new ArrayList<>();
        planets.add(new Planet("First", Arrays.asList(Good.RED)));
        planets.add(new Planet("Second", Arrays.asList(Good.BLUE)));
        
        Planets card = new Planets(11, CardLevel.LEVEL_ONE, 1, planets);
        
        int count = 0;
        for (Planet p : card) {
            count++;
            if (count == 1) {
                assertEquals("First", p.getName());
            } else if (count == 2) {
                assertEquals("Second", p.getName());
            }
        }
        assertEquals(2, count);
    }
}