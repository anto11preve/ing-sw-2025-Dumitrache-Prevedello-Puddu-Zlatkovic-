package Model.Ship;

public class EnginesCounter {
    private int singleEngines;
    private int doubleEngines;

    public EnginesCounter() {
        this.singleEngines = 0;
        this.doubleEngines = 0;
    }

    public int getSingleEngines() {
        return singleEngines;
    }
    public void setSingleEngines(int singleEngines) {
        this.singleEngines = singleEngines;
    }
    public int getDoubleEngines() {
        return doubleEngines;
    }
    public void setDoubleEngines(int doubleEngines) {
        this.doubleEngines = doubleEngines;
    }
}
