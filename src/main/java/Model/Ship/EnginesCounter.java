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
    public void incrementSingleEngines() {this.singleEngines++;}
    public void decrementSingleEngines() {this.singleEngines--;}

    public int getDoubleEngines() {
        return doubleEngines;
    }
    public void setDoubleEngines(int doubleEngines) {
        this.doubleEngines = doubleEngines;
    }
    public void incrementDoubleEngines() {this.doubleEngines++;}
    public void decrementDoubleEngines() {this.doubleEngines--;}
}
