package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Factories.AdventureCardFactory;
import Model.Enums.CardLevel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Controller.Enums.MatchLevel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Loads and builds adventure cards from a unified JSON file.
 * Supports test injection for unit testing.
 */
public class AdventureCardLoader {

    private static List<AdventureCardFilip> testCards = null;

    /**
     * Injects test cards for testing purposes.
     * @param cards list of mock or test adventure cards
     */
    public static void setTestCards(List<AdventureCardFilip> cards) {
        testCards = cards;
    }

    /**
     * Clears test cards so normal loading is restored.
     */
    public static void clearTestCards() {
        testCards = null;
    }

    /**
     * Loads all adventure cards from a JSON file using the specified resource path.
     * Cards are built dynamically using AdventureCardFactory.
     *
     * @param resourcePath path to the JSON file (must be in resources folder)
     * @return list of parsed AdventureCardFilip objects
     */
    public static List<AdventureCardFilip> loadCards(String resourcePath) {
        try {
            InputStream inputStream = AdventureCardLoader.class.getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + resourcePath);
            }

            InputStreamReader reader = new InputStreamReader(inputStream);
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            List<AdventureCardFilip> cards = new ArrayList<>();

            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                cards.add(AdventureCardFactory.fromJson(obj));
            }

            return cards;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads and filters cards based on the match level from a single JSON file.
     * Builds the deck according to the official Galaxy Trucker rules.
     * Uses testCards if injected.
     *
     * @param level the difficulty level of the match
     * @return list of cards for the selected level
     */
    public static List<AdventureCardFilip> loadAdventureCards(MatchLevel level) {
        if (testCards != null) return testCards;
        
        List<AdventureCardFilip> allCards = loadCards("adventure_cards.json");
        
        if (allCards == null) {
            return new ArrayList<>();
        }

        return switch (level) {
            case TRIAL -> allCards.stream()
                    .filter(card -> card.getLevel() == CardLevel.LEARNER)
                    .collect(Collectors.toList());

            case LEVEL2 -> {
                List<AdventureCardFilip> level1Cards = allCards.stream()
                        .filter(card -> card.getLevel() == CardLevel.LEVEL_ONE)
                        .collect(Collectors.toList());
                        
                List<AdventureCardFilip> level2Cards = allCards.stream()
                        .filter(card -> card.getLevel() == CardLevel.LEVEL_TWO)
                        .collect(Collectors.toList());

                List<AdventureCardFilip> combined = new ArrayList<>();
                combined.addAll(level1Cards);
                combined.addAll(level2Cards);
                //Collections.shuffle(combined); //TODO: riaggiungere lo shuffle
                yield combined;
            }
        };
    }
}
