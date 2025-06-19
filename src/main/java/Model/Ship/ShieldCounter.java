package Model.Ship;

public class ShieldCounter {
    private int northShields;
    private int eastShields;
    private int southShields;
    private int westShields;

    public ShieldCounter() {
        this.northShields = 0;
        this.eastShields = 0;
        this.southShields = 0;
        this.westShields = 0;
    }

    public int getNorthShields() {
        return northShields;
    }
    public void setNorthShields(int northShields) {
        this.northShields = northShields;
    }
    public void incrementNorthShields() {this.northShields++;}
    public void decrementNorthShields() {this.northShields--;}

    public int getEastShields() {
        return eastShields;
    }
    public void setEastShields(int eastShields) {
        this.eastShields = eastShields;
    }
    public void incrementEastShields() {this.eastShields++;}
    public void decrementEastShields() {this.eastShields--;}

    public int getSouthShields() {
        return southShields;
    }
    public void setSouthShields(int southShields) {
        this.southShields = southShields;
    }
    public void incrementSouthShields() {this.southShields++;}
    public void decrementSouthShields() {this.southShields--;}

    public int getWestShields() {
        return westShields;
    }
    public void setWestShields(int westShields) {
        this.westShields = westShields;
    }
    public void incrementWestShields() {this.westShields++;}
    public void decrementWestShields() {this.westShields--;}
}
