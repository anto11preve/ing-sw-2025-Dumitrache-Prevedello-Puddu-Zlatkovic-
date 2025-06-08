package Model.Ship.Components;

import com.google.gson.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader for spaceship components from a JSON file.
 * Delegates the creation of each component to the ComponentFactory.
 */
public class ComponentsLoader {

    /**
     * Loads a list of spaceship components from the given JSON path.
     *
     * @param jsonPath path to the JSON file containing the component definitions
     * @return list of SpaceshipComponent objects loaded from JSON
     */
    public static List<SpaceshipComponent> load(String jsonPath) {
        List<SpaceshipComponent> components = new ArrayList<>();

        try (FileReader reader = new FileReader(jsonPath)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elem : array) {
                JsonObject json = elem.getAsJsonObject();
                SpaceshipComponent comp = ComponentFactory.fromJson(json);
                components.add(comp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return components;
    }
}
