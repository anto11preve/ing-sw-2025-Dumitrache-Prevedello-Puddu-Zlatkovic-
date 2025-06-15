package Model.Ship.Components;

import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Crewmates;
import com.google.gson.JsonObject;

/**
 * Represents a Cabin component on the spaceship. Cabins can host standard crew
 * members and may support brown or purple alien crew members depending on
 * their special abilities.
 */
public class Cabin extends SpaceshipComponent {
    private Crewmates occupants;
    private boolean canContainBrown;   // True if the cabin can host brown aliens
    private boolean canContainPurple;  // True if the cabin can host purple aliens

    // New fields to support aliens actually present in the cabin
    private boolean hasAlien = false;
    private String alienType = null; // "brown" or "purple"

    /**
     * Standard constructor for Cabin with explicit parameters.
     */
    public Cabin(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, Crewmates occupants) {
        super(Type, front, rear, left, right);
        this.occupants = Crewmates.EMPTY; // Default to empty even if parameter is passed
    }

    /**
     * Constructor to initialize a Cabin from a JSON object.
     * This is used by the ComponentFactory to load spaceship components from JSON configuration.
     */
    public Cabin(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );

        this.occupants = Crewmates.EMPTY; // No crew by default when loading from JSON

        // Parse abilities from JSON if present
        this.canContainBrown = json.has("abilities") && json.getAsJsonArray("abilities").toString().contains("can_host_brown");
        this.canContainPurple = json.has("abilities") && json.getAsJsonArray("abilities").toString().contains("can_host_purple");
    }

    public boolean getCanContainBrown() {
        return canContainBrown;
    }

    public boolean getCanContainPurple() {
        return canContainPurple;
    }

    public void setCanContainBrown(boolean canContainBrown) {
        this.canContainBrown = canContainBrown;
    }

    public void setCanContainPurple(boolean canContainPurple) {
        this.canContainPurple = canContainPurple;
    }

    public void setOccupants(Crewmates occupants) {
        this.occupants = occupants;
    }

    public Crewmates getOccupants() {
        return occupants;
    }

    // Added support for alien tracking
    public boolean hasAlien() {
        return hasAlien;
    }

    public String getAlienType() {
        return alienType;
    }

    public void setHasAlien(boolean hasAlien) {
        this.hasAlien = hasAlien;
    }

    public void setAlienType(String alienType) {
        this.alienType = alienType;
    }

    /**
     * Constructor used for testing or simplified instantiation
     */
    public Cabin(boolean hasAlien, String alienType) {
        super(Card.CABIN, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE, ConnectorType.NONE);
        this.hasAlien = hasAlien;
        this.alienType = alienType;
    }


    // Optional: These can be implemented for game logic events if needed
    public void added() {
        // Called when the component is added to the ship
    }

    public void removed() {
        // Called when the component is removed from the ship
    }
/*
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    */
}
