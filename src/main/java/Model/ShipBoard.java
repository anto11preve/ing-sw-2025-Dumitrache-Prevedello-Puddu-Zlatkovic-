package Model;

import java.util.ArrayList;
import java.util.List;

// Classe ShipBoard che rappresenta la nave del giocatore
public class ShipBoard {
    private static final int ROWS = 5;
    private static final int COLS = 7;

    private SpaceshipComponent[][] components;
    private SpaceshipComponent activeComponent;
    private List<SpaceshipComponent> reservedComponents;
    private CondensedShip condensedShip;

    public ShipBoard() {
        this.components = new SpaceshipComponent[ROWS][COLS];
        this.activeComponent = null;
        this.reservedComponents = new ArrayList<>();
        this.condensedShip = new CondensedShip();
    }

    /**
     * Aggiunge un componente alla posizione specificata, se libera.
     */
    public boolean addComponent(SpaceshipComponent component, int x, int y) {
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return false;
        if (components[x][y] == null) {
            components[x][y] = component;
            return true;
        }
        return false;
    }

    /**
     * Rimuove un componente e aggiunge spazzatura al giocatore.
     */
    public void removeComponent(int x, int y, Player player) {
        if (x >= 0 && x < ROWS && y >= 0 && y < COLS && components[x][y] != null) {
            components[x][y] = null;
            player.addJunk();
        }
    }

    public SpaceshipComponent getComponent(int x, int y) {
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return null;
        return components[x][y];
    }

    /**
     * Controlla che tutti i componenti siano connessi tra loro.
     */
    public boolean checkIntegrity() {
        boolean[][] visited = new boolean[ROWS][COLS];
        int total = 0;
        int startX = -1, startY = -1;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (components[i][j] != null) {
                    total++;
                    if (startX == -1) {
                        startX = i;
                        startY = j;
                    }
                }
            }
        }

        if (total == 0) return true;

        int visitedCount = dfs(startX, startY, visited);
        return visitedCount == total;
    }

    private int dfs(int x, int y, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= ROWS || y >= COLS) return 0;
        if (visited[x][y] || components[x][y] == null) return 0;

        visited[x][y] = true;
        int count = 1;
        count += dfs(x + 1, y, visited);
        count += dfs(x - 1, y, visited);
        count += dfs(x, y + 1, visited);
        count += dfs(x, y - 1, visited);
        return count;
    }

    public void setActiveComponent(SpaceshipComponent component) {
        this.activeComponent = component;
    }

    public int[] getIndex(SpaceshipComponent goalComponent) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (components[i][j] == goalComponent) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }


    public int[] getShipBoundaries() {
        int minRow = ROWS, maxRow = -1, minCol = COLS, maxCol = -1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (components[i][j] != null) {
                    if (i < minRow) minRow = i;
                    if (i > maxRow) maxRow = i;
                    if (j < minCol) minCol = j;
                    if (j > maxCol) maxCol = j;
                }
            }
        }
        if (maxRow == -1) return null;
        return new int[]{minRow, maxRow, minCol, maxCol};
    }


    public void printOccupiedMatrix() {
        int[] bounds = getShipBoundaries();
        if (bounds == null) {
            System.out.println("Nave vuota.");
            return;
        }
        int minRow = bounds[0], maxRow = bounds[1], minCol = bounds[2], maxCol = bounds[3];
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                System.out.print(components[i][j] != null ? "[X]" : "[ ]");
            }
            System.out.println();
        }
    }

    public void reserveComponent(SpaceshipComponent component) {
        reservedComponents.add(component);
    }

    public void removeReservedComponent(SpaceshipComponent component) {
        reservedComponents.remove(component);
    }

    public List<SpaceshipComponent> getReservedComponents() {
        return reservedComponents;
    }

    public CondensedShip getCondensedShip() {
        return condensedShip;
    }
}
