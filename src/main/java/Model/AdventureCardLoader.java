package Model;

import Model.Board.AdventureCards.AdventureCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class AdventureCardLoader {

    public static List<AdventureCard> loadCards(String resourcePath) {
        try {
            InputStream inputStream = AdventureCardLoader.class.getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File non trovato: " + resourcePath);
            }

            InputStreamReader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<List<AdventureCard>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
