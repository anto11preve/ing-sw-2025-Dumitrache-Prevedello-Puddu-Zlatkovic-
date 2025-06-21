package Model.Ship.Components;

import Model.Enums.AlienColor;
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
    private int canContainBrown;   // True if the cabin can host brown aliens
    private int canContainPurple;  // True if the cabin can host purple aliens

    // New fields to support aliens actually present in the cabin
    private boolean hasAlien = false; //TODO: serve?
    private String alienType = null; // "brown" or "purple" TODO: serve?

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
        this.canContainBrown = 0;
        this.canContainPurple = 0;
    }

    public boolean getCanContainBrown() {
        return canContainBrown>0;
    }

    public boolean getCanContainPurple() {
        return canContainPurple>0;
    }

    public void setCanContainBrown(int canContainBrown) {
        this.canContainBrown = canContainBrown;
    }

    public void setCanContainPurple(int canContainPurple) {
        this.canContainPurple = canContainPurple;
    }

    public void incrementCanContainBrown() {
        this.canContainBrown++;
    }
    public void incrementCanContainPurple() {
        this.canContainPurple++;
    }

    public void decrementCanContainBrown() {
        if (this.canContainBrown > 0) {
            this.canContainBrown--;
            if(this.occupants==Crewmates.BROWN_ALIEN) {

                if (this.canContainBrown == 0) {
                    this.hasAlien = false; // Reset alien presence if no brown support left
                    this.alienType = null; // Reset alien type
                    this.occupants = Crewmates.EMPTY;
                    this.getShipBoard().getCondensedShip().getAliens().setBrownAlien(false);
                }

            }
        } else {
            throw new IllegalArgumentException("Can't decrement canContainBrown below zero");
        }
    }

    public void decrementCanContainPurple() {
        if (this.canContainPurple > 0) {
            this.canContainPurple--;
            if(this.occupants==Crewmates.PURPLE_ALIEN) {

                if (this.canContainPurple == 0) {
                    this.hasAlien = false; // Reset alien presence if no purple support left
                    this.alienType = null; // Reset alien type
                    this.occupants = Crewmates.EMPTY;
                    this.getShipBoard().getCondensedShip().getAliens().setPurpleAlien(false);
                }

            }
        } else {
            throw new IllegalArgumentException("Can't decrement canContainPurple below zero");
        }
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



    @Override
    public void added() {
        if(getShipBoard().getCondensedShip().getCabins().contains(this)){
            throw new RuntimeException("Cabin already added to the ship.");
        } else {
            getShipBoard().getCondensedShip().addCabin(this);
        }
        for(AlienLifeSupport alienLifeSupport : getShipBoard().getCondensedShip().getAlienSupports()) {
            if(getShipBoard().areComponentsConnected(this, alienLifeSupport)) {
                if(alienLifeSupport.getColor() == AlienColor.BROWN){
                    this.incrementCanContainBrown();
                } else if(alienLifeSupport.getColor() == AlienColor.PURPLE) {
                    this.incrementCanContainPurple();
                }
            }
        }
    }

    @Override
    public void removed() {
        if (!getShipBoard().getCondensedShip().getCabins().contains(this)) {
            throw new RuntimeException("Cabin not found in the ship.");
        } else {
            getShipBoard().getCondensedShip().removeCabin(this);
        }
    }
}
