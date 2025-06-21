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


    /**
     * Calculates the Manhattan distance between this coordinate and another coordinate.
     *
     * @param other the other coordinate to calculate the distance to
     * @return the Manhattan distance
     */
    public int manhattanDistance(Coordinates other) {
        //calculate manhattan distance
        return Math.abs(this.i - other.i) + Math.abs(this.j - other.j);
    }

    /*

     */
}
