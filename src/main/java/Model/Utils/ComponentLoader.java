package Model.Utils;

import Model.Ship.Components.ComponentFactory;
import Model.Ship.Components.SpaceshipComponent;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to load spaceship components from a JSON file.
 * Parses spaceship_components.json and creates components using the ComponentFactory.
 */
public class ComponentLoader {

    private static final String COMPONENTS_PATH = "src/main/resources/spaceship_components.json";

    /**
     * Loads all spaceship components from JSON.
     * @return List of SpaceshipComponent objects parsed from file.
     */
    public static List<SpaceshipComponent> loadAllComponents() {
        List<SpaceshipComponent> components = new ArrayList<>();
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(COMPONENTS_PATH));
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                SpaceshipComponent component = ComponentFactory.fromJson(jsonObject);
                if (component != null) {
                    components.add(component);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Components file not found: " + COMPONENTS_PATH);
        } catch (Exception e) {
            System.err.println("Error loading components: " + e.getMessage());
        }
        return components;
    }
}
