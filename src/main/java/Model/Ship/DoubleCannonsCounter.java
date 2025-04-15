package Model.Ship;

public class DoubleCannonsCounter {
    int  frontCannons;
    int  otherCannons;

    public DoubleCannonsCounter() {
        this.frontCannons = 0;
        this.otherCannons = 0;
    }

    public DoubleCannonsCounter(int frontCannons, int otherCannons) {
        this.frontCannons = frontCannons;
        this.otherCannons = otherCannons;
    }

    public int getFrontCannons() {
        return frontCannons;
    }

    public int getOtherCannons() {
        return otherCannons;
    }



}
