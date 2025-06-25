package Model.Ship;

import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;

import java.io.Serializable;
import java.util.List;

/**
 * AlienCounter keeps track of alien crew presence on the ship.
 * It can be used to count the total number of alien crew members,
 * and also track whether at least one brown or purple alien is present.
 */
public class AlienCounter implements Serializable {
    private boolean brownAlien;
    private boolean purpleAlien;

    public AlienCounter() {
        this.brownAlien = false;
        this.purpleAlien = false;
    }

    public boolean hasBrownAlien() {
        return brownAlien;
    }

    public void setBrownAlien(boolean brownAlien) {
        this.brownAlien = brownAlien;
    }

    public boolean hasPurpleAlien() {
        return purpleAlien;
    }

    public void setPurpleAlien(boolean purpleAlien) {
        this.purpleAlien = purpleAlien;
    }
}
