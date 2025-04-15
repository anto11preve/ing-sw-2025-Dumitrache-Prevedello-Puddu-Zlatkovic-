package Model.Ship;

public class AlienCounter {
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

    public boolean hasGreenAlien() {
        return purpleAlien;
    }
    public void setGreenAlien(boolean purpleAlien) {
        this.purpleAlien = purpleAlien;
    }
}
