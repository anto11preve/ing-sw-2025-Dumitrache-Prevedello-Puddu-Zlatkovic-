package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Factories.AdventureCardFactory;
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
 */
public class AdventureCardLoader {

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
     *
     * @param level the difficulty level of the match
     * @return list of cards for the selected level
     */
    public static List<AdventureCardFilip> loadCardsForLevel(MatchLevel level) {
        List<AdventureCardFilip> allCards = loadCards("adventure_cards.json");

        return switch (level) {
            case TRIAL -> allCards.stream()
                    .filter(card -> card.getLevel().toString().equals("TRIAL"))
                    .collect(Collectors.toList());

            case LEVEL2 -> {
                List<AdventureCardFilip> level1 = allCards.stream()
                        .filter(card -> card.getLevel().toString().equals("LEVEL1"))
                        .limit(4)
                        .collect(Collectors.toList());
                List<AdventureCardFilip> level2 = allCards.stream()
                        .filter(card -> card.getLevel().toString().equals("LEVEL2"))
                        .limit(4)
                        .collect(Collectors.toList());
                List<AdventureCardFilip> level3 = allCards.stream()
                        .filter(card -> card.getLevel().toString().equals("LEVEL3"))
                        .limit(4)
                        .collect(Collectors.toList());

                List<AdventureCardFilip> combined = new ArrayList<>();
                combined.addAll(level1);
                combined.addAll(level2);
                combined.addAll(level3);
                Collections.shuffle(combined);
                yield combined;
            }
        };
    }
}
