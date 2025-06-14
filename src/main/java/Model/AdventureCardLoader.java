package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Controller.Enums.MatchLevel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads adventure cards from JSON files based on the game level.
 * Uses an internal factory to construct specific card types from their JSON representation.
 */
public class AdventureCardLoader {

    /**
     * Loads a list of AdventureCardFilip from a JSON file using the specified resource path.
     * Cards are built dynamically using the internal factory.
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
                cards.add(InternalFactory.fromJson(obj));
            }

            return cards;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads cards based on the match level.
     * TRIAL uses adventure_cards_trial.json
     * LEVEL2 uses adventure_cards_level2.json
     *
     * @param level the difficulty level of the match
     * @return list of cards for the selected level
     */
    public static List<AdventureCardFilip> loadCardsForLevel(MatchLevel level) {
        return switch (level) {
            case TRIAL -> loadCards("adventure_cards_trial.json");
            case LEVEL2 -> loadCards("adventure_cards_level2.json");
        };
    }

    /**
     * Internal factory for constructing adventure cards based on their type.
     * Avoids exposing a separate AdventureCardFactory class.
     */
    private static class InternalFactory {
        public static AdventureCardFilip fromJson(JsonObject json) {
            String type = json.get("type").getAsString();

            return switch (type) {
                case "OpenSpace" -> new Model.Board.AdventureCards.OpenSpace(json);
                case "MeteorSwarm" -> new Model.Board.AdventureCards.MeteorSwarm(json);
                case "Stardust" -> new Model.Board.AdventureCards.Stardust(json);
                case "Planets" -> new Model.Board.AdventureCards.Planets(json);
                case "Pirates" -> new Model.Board.AdventureCards.Pirates(json);
                case "Slavers" -> new Model.Board.AdventureCards.Slavers(json);
                case "Smugglers" -> new Model.Board.AdventureCards.Smugglers(json);
                case "AbandonedShip" -> new Model.Board.AdventureCards.AbandonedShip(json);
                case "AbandonedStation" -> new Model.Board.AdventureCards.AbandonedStation(json);
                case "CombatZone" -> new Model.Board.AdventureCards.CombatZone(json);
                case "Epidemic" -> new Model.Board.AdventureCards.Epidemic(json);
                default -> throw new IllegalArgumentException("Unknown card type: " + type);
            };
        }
    }
}
