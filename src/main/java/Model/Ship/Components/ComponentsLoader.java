package Model.Ship.Components;

import com.google.gson.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ComponentsLoader {

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
