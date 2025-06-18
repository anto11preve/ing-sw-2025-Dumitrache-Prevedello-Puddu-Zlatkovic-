package Model.Ship;

public class Coordinates {
    private int i;
    private int j;

    public Coordinates(int i, int j) {
        this.i=i;
        this.j=j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
