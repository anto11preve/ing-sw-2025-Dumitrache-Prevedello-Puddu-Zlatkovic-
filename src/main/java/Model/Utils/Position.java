package Model.Utils;

import java.util.Objects;

/**
 * Represents a position (x, y) on the ship grid for Galaxy Trucker.
 * Includes utilities for adjacency, bounds checking, and Manhattan distance.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a new position with coordinates (x, y).
     * @param x Row coordinate
     * @param y Column coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns a new Position moved by dx and dy from the current position.
     * @param dx Change in x
     * @param dy Change in y
     * @return New Position after applying offset
     */
    public Position offset(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    /**
     * Checks if this position is adjacent (orthogonally) to another position.
     * @param other The other position
     * @return true if adjacent
     */
    public boolean isAdjacent(Position other) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        return (dx + dy) == 1;
    }

    /**
     * Returns the Manhattan distance between this position and another.
     * @param other The other position
     * @return Manhattan distance
     */
    public int manhattanDistance(Position other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    /**
     * Checks if this position is within the given bounds.
     * @param rows Max number of rows (exclusive)
     * @param cols Max number of columns (exclusive)
     * @return true if inside bounds
     */
    public boolean isWithinBounds(int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
