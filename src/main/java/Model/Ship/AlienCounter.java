package Model.Ship;

import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;

import java.util.List;

/**
 * AlienCounter keeps track of alien crew presence on the ship.
 * It can be used to count the total number of alien crew members,
 * and also track whether at least one brown or purple alien is present.
 */
public class AlienCounter {
    private boolean brownAlien;
    private boolean purpleAlien;

    public AlienCounter() {
        this.brownAlien = false;
        this.purpleAlien = false;
    }

    /**
     * Counts the number of alien crew members on the ship.
     * Also updates internal flags for brown and purple alien presence.
     *
     * @param components list of all components on the ship
     * @return total number of alien crew
     */
    public int count(List<SpaceshipComponent> components) {
        int alienCount = 0;

        for (SpaceshipComponent c : components) {
            if (c instanceof Cabin cabin && cabin.hasAlien()) {
                alienCount += 2;

                if ("brown".equalsIgnoreCase(cabin.getAlienType())) {
                    this.brownAlien = true;
                } else if ("purple".equalsIgnoreCase(cabin.getAlienType())) {
                    this.purpleAlien = true;
                }
            }
        }

        return alienCount;
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
