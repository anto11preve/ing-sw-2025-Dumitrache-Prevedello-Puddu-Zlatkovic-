package Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import Controller.Enums.MatchLevel;
import Model.Board.AdventureCards.AdventureCardFilip;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class AdventureCardLoader {

    public static List<AdventureCardFilip> loadCards(String resourcePath) {
        try {
            InputStream inputStream = AdventureCardLoader.class.getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File non trovato: " + resourcePath);
            }

            InputStreamReader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<List<AdventureCardFilip>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<AdventureCardFilip> loadCardsForLevel(MatchLevel level) {
        return switch (level) {
            case TRIAL -> loadCards("adventure_cards_trial.json");
            case LEVEL2 -> loadCards("adventure_cards_level2.json");
        };
    }
}
