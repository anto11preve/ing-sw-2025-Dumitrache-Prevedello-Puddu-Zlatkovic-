package Model;

import Model.Ship.Components.SpaceshipComponent;
import Model.Factories.ComponentFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads and builds spaceship components from a JSON configuration file.
 */
public class ComponentsLoader {

    /**
     * Reads all components defined in the given JSON resource and instantiates them.
     * @param resourcePath path to JSON file (in resources folder)
     * @return list of SpaceshipComponent instances
     */
    public static List<SpaceshipComponent> loadComponents(String resourcePath) {
        try (InputStream is = ComponentsLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Component JSON not found: " + resourcePath);
            }
            JsonArray array = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonArray();
            List<SpaceshipComponent> components = new ArrayList<>();
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                components.add(ComponentFactory.fromJson(obj));
            }
            return components;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load components from " + resourcePath, e);
        }
    }
}
