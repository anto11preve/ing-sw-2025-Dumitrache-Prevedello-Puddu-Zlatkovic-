package Model.Ship;

import java.util.ArrayList;
import java.util.List;
import Model.Ship.Components.*;
import Model.Enums.*;
import Model.Utils.Position;

/**
 * Represents the spaceship board as a 5x7 grid.
 * Manages placement, connections, validation, and power calculations.
 */
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

    /**
     * Adds a component to the board at position (x, y) if the placement is valid.
     */
    public boolean addComponent(SpaceshipComponent component, int x, int y) {
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return false;
        if (components[x][y] == null && isConnectedToExistingComponent(component, x, y)) {
            components[x][y] = component;
            return true;
        }
        return false;
    }

    /**
     * Checks whether a component is connected to an existing component with compatible connectors.
     */
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

    /**
     * Determines if two connectors are compatible.
     */
    private boolean areConnectorsCompatible(ConnectorType a, ConnectorType b) {
        if (a == ConnectorType.NONE || b == ConnectorType.NONE) return false;
        if (a == ConnectorType.UNIVERSAL || b == ConnectorType.UNIVERSAL) return true;
        return a == b;
    }

    /**
     * Performs a DFS traversal to check if all components are part of a single connected structure.
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

    /**
     * Returns a list of all components on the ship.
     */
    public List<SpaceshipComponent> getAllComponents() {
        List<SpaceshipComponent> result = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (components[i][j] != null) {
                    result.add(components[i][j]);
                }
            }
        }
        return result;
    }

    /**
     * Returns a list of exposed component positions (with no neighbors on any side).
     */
    public List<Position> getExposedComponentPositions() {
        List<Position> exposed = new ArrayList<>();
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                if (components[x][y] != null) {
                    boolean exposedFlag = false;
                    int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
                    for (int[] d : dirs) {
                        int nx = x + d[0];
                        int ny = y + d[1];
                        if (nx < 0 || nx >= ROWS || ny < 0 || ny >= COLS || components[nx][ny] == null) {
                            exposedFlag = true;
                            break;
                        }
                    }
                    if (exposedFlag) {
                        exposed.add(new Position(x, y));
                    }
                }
            }
        }
        return exposed;
    }

    /**
     * Destroys a component at a given position.
     */
    public void destroyComponentAt(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (x >= 0 && x < ROWS && y >= 0 && y < COLS) {
            components[x][y] = null;
        }
    }

    /**
     * Checks if a component at a given position is protected by a shield generator facing the direction.
     */
    public boolean hasActiveShieldFacing(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        for (int i = 0; i < dirs.length; i++) {
            int nx = x + dirs[i][0];
            int ny = y + dirs[i][1];
            if (nx >= 0 && nx < ROWS && ny >= 0 && ny < COLS) {
                SpaceshipComponent c = components[nx][ny];
                if (c instanceof ShieldGenerator sg && sg.getDirection() == directions[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    // The rest of the previously present methods remain unchanged below...

    public void printOccupiedMatrix() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(components[i][j] != null ? "[X]" : "[ ]");
            }
            System.out.println();
        }
    }

    public boolean validateShip() {
        return checkIntegrity();
    }

    public int calculateEnginePower() {
        int total = 0;
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                SpaceshipComponent c = components[x][y];
                if (c instanceof Engine engine && !engine.requiresBattery()) {
                    total += engine.getEnginePower();
                }
                if (c instanceof Cabin cabin && cabin.hasEngineAlien()) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public int calculateFirePower() {
        int total = 0;
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                SpaceshipComponent c = components[x][y];
                if (c instanceof Cannon cannon && !cannon.requiresBattery()) {
                    Direction dir = cannon.getDirection();
                    int basePower = cannon.getCannonStrength();
                    if (dir == Direction.UP) {
                        total += basePower;
                    } else if (dir == Direction.SIDE) {
                        total += basePower / 2;
                    }
                }
                if (c instanceof Cabin cabin && cabin.hasCannonAlien()) {
                    total += 2;
                }
            }
        }
        return total;
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
}
