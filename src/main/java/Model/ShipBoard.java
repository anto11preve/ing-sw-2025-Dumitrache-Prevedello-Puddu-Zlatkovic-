package Model;

import java.util.ArrayList;
import java.util.List;

public class ShipBoard {
    private static final int ROWS = 5;
    private static final int COLS = 7;

    private SpaceshipComponent[][] components;
    private SpaceshipComponent activeComponent;
    private List<SpaceshipComponent> reservedComponents;
    private final CondensedShip condensedShip;

    public ShipBoard() {
        this.components = new SpaceshipComponent[ROWS][COLS];
        this.activeComponent = null;
        this.reservedComponents = new ArrayList<>();
        this.condensedShip = new CondensedShip();
    }

    public boolean addComponent(SpaceshipComponent component, int x, int y) {
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return false;
        if (components[x][y] == null && isConnectedToExistingComponent(component, x, y)) {
            components[x][y] = component;
            return true;
        }
        return false;
    }

    private boolean isConnectedToExistingComponent(SpaceshipComponent component, int x, int y) {
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS && components[nx][ny] != null) {
                ConnectorType neighborConnector = components[nx][ny].getConnectorType((dir[0] == -1 ? 1 : dir[0] == 1 ? 0 : dir[1] == -1 ? 3 : 2));
                ConnectorType currentConnector = component.getConnectorType((dir[0] == -1 ? 0 : dir[0] == 1 ? 1 : dir[1] == -1 ? 2 : 3));
                if (areConnectorsCompatible(currentConnector, neighborConnector)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean areConnectorsCompatible(ConnectorType a, ConnectorType b) {
        if (a == ConnectorType.NONE || b == ConnectorType.NONE) return false;
        if (a == ConnectorType.UNIVERSAL || b == ConnectorType.UNIVERSAL) return true;
        return a == b;
    }

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

    public SpaceshipComponent getComponent(int x, int y) {
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return null;
        return components[x][y];
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

    public boolean validateShip() {
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                SpaceshipComponent comp = components[x][y];
                if (comp == null) continue;

                int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
                for (int[] d : dirs) {
                    int nx = x + d[0];
                    int ny = y + d[1];
                    if (nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS && components[nx][ny] != null) {
                        ConnectorType thisConn = comp.getConnectorType(d[0] == -1 ? 0 : d[0] == 1 ? 1 : d[1] == -1 ? 2 : 3);
                        ConnectorType otherConn = components[nx][ny].getConnectorType(d[0] == -1 ? 1 : d[0] == 1 ? 0 : d[1] == -1 ? 3 : 2);
                        if (!areConnectorsCompatible(thisConn, otherConn)) {
                            System.out.printf("Errore: connettori incompatibili tra (%d,%d) e (%d,%d)\n", x, y, nx, ny);
                            return false;
                        }
                    }
                }

                Card card = comp.getType();

                if (card.hasEngine() && comp.getConnectorType(1) != ConnectorType.NONE) {
                    if (x + 1 < ROWS && components[x + 1][y] != null) {
                        System.out.printf("Errore: motore in (%d,%d) ha un modulo dietro\n", x, y);
                        return false;
                    }
                }

                if (card.hasCannon() && comp.getConnectorType(0) != ConnectorType.NONE) {
                    if (x - 1 >= 0 && components[x - 1][y] != null) {
                        System.out.printf("Errore: cannone in (%d,%d) bloccato da un modulo davanti\n", x, y);
                        return false;
                    }
                }

                if (card == Card.CABIN && comp.getConnectorType(0) == ConnectorType.NONE
                        && comp.getConnectorType(1) == ConnectorType.NONE
                        && comp.getConnectorType(2) == ConnectorType.NONE
                        && comp.getConnectorType(3) == ConnectorType.NONE) {
                    System.out.printf("Errore: CABINA in (%d,%d) non ha connessioni\n", x, y);
                    return false;
                }

                if (card == Card.ALIEN_LIFE_SUPPORT) {
                    boolean hasAdjacentCabin = false;
                    for (int[] d : dirs) {
                        int nx = x + d[0];
                        int ny = y + d[1];
                        if (nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS) {
                            SpaceshipComponent adj = components[nx][ny];
                            if (adj != null && adj.getType() == Card.CABIN) {
                                hasAdjacentCabin = true;
                                break;
                            }
                        }
                    }
                    if (!hasAdjacentCabin) {
                        System.out.printf("Errore: supporto alieno in (%d,%d) non adiacente a cabina\n", x, y);
                        return false;
                    }
                }

                if (card == Card.SHIELD_GENERATOR) {
                    if (comp.getConnectorType(0) == ConnectorType.NONE && comp.getConnectorType(1) == ConnectorType.NONE) {
                        System.out.printf("Errore: generatore di scudi in (%d,%d) non connesso anteriormente o posteriormente\n", x, y);
                        return false;
                    }
                }

                if (card == Card.SPECIAL_CARGO_HOLD) {
                    boolean hasCargoNearby = false;
                    for (int[] d : dirs) {
                        int nx = x + d[0];
                        int ny = y + d[1];
                        if (nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS) {
                            SpaceshipComponent adj = components[nx][ny];
                            if (adj != null && adj.getType() == Card.CARGO_HOLD) {
                                hasCargoNearby = true;
                                break;
                            }
                        }
                    }
                    if (!hasCargoNearby) {
                        System.out.printf("Errore: carico speciale in (%d,%d) non adiacente a carico normale\n", x, y);
                        return false;
                    }
                }
            }
        }
        System.out.println("Validazione nave completata con successo.");
        return true;
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
