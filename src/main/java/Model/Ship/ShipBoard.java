package Model.Ship;

import Model.Enums.ConnectorType;
import Model.Exceptions.InvalidMethodParameters;
import Model.Enums.Direction;
import Model.Enums.Side;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.Engine;
import Model.Ship.Components.ShieldGenerator;
import Model.Ship.Components.SpaceshipComponent;
import Model.Utils.Position;
import Model.Utils.DirectionSideUtils;


import java.util.*;


/**
 * Full-featured ShipBoard for Galaxy Trucker.
 * Manages placement, connections, rotations, integrity, firepower, thrust, shields, damage, and exposed connectors.
 */
public class ShipBoard {
    private static final int ROWS = 5;
    private static final int COLS = 7;

    private final SpaceshipComponent[][] components;
    private SpaceshipComponent activeComponent;
    private final List<SpaceshipComponent> reservedComponents;
    private final CondensedShip condensedShip;

    public ShipBoard() {
        this.components = new SpaceshipComponent[ROWS][COLS];
        this.activeComponent = null;
        this.reservedComponents = new ArrayList<>();
        this.condensedShip = new CondensedShip();
    }

    public void addComponent(SpaceshipComponent component, Coordinates coordinates) throws InvalidMethodParameters {
        int x = coordinates.getX()-4;
        int y = coordinates.getY()-5;

        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) throw new InvalidMethodParameters("Invalid coordinates out of bounds");
        if (components[y][x] != null) throw new InvalidMethodParameters("Position already occupied");
        if (isConnectedToExistingComponents(component, x, y)) {
            components[y][x] = component;
        }else{
            throw new InvalidMethodParameters("Component not connected to existing components");
        }
    }

    public void removeComponent(Coordinates coordinates) throws InvalidMethodParameters {
        int x = coordinates.getX()-4;
        int y = coordinates.getY()-5;

        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) throw new InvalidMethodParameters("Invalid coordinates out of bounds");
        if (components[y][x] != null) {
            components[y][x] = null;
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public boolean isEmpty() {
        for (SpaceshipComponent[] row : components) {
            for (SpaceshipComponent c : row) {
                if (c != null) return false;
            }
        }
        return true;
    }


    public boolean isConnectedToExistingComponents(SpaceshipComponent component, int row, int col) {
        for (Side side : Side.values()) {
            int[] offset = getOffset(side);
            int adjRow = row + offset[0];
            int adjCol = col + offset[1];
            if (isValidPosition(adjRow, adjCol) && components[adjRow][adjCol] != null) {
                SpaceshipComponent neighbor = components[adjRow][adjCol];
                if (connectorsAreCompatible(
                        component.getConnectorAt(side),
                        neighbor.getConnectorAt(getOppositeSide(side))
                )) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] getOffset(Side side) {
        return switch (side) {
            case FRONT -> new int[]{-1, 0};
            case REAR -> new int[]{1, 0};
            case LEFT -> new int[]{0, -1};
            case RIGHT -> new int[]{0, 1};
        };
    }

    private Side getOppositeSide(Side side) {
        return switch (side) {
            case FRONT -> Side.REAR;
            case REAR -> Side.FRONT;
            case LEFT -> Side.RIGHT;
            case RIGHT -> Side.LEFT;
        };
    }

    private boolean connectorsAreCompatible(ConnectorType a, ConnectorType b) {
        if (a == ConnectorType.NONE || b == ConnectorType.NONE) return false;
        if (a == ConnectorType.UNIVERSAL || b == ConnectorType.UNIVERSAL) return true;
        return a == b;
    }

    public void setActiveComponent(SpaceshipComponent component) {
        this.activeComponent = component;
    }
    public SpaceshipComponent getActiveComponent() {
        return activeComponent;
    }

    public boolean checkIntegrity() {
        boolean[][] visited = new boolean[ROWS][COLS];
        int totalComponents = 0;
        int startRow = -1, startCol = -1;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (components[i][j] != null) {
                    totalComponents++;
                    if (startRow == -1) {
                        startRow = i;
                        startCol = j;
                    }
                }
            }
        }

        if (totalComponents == 0) return true;

        int visitedComponents = dfs(startRow, startCol, visited);
        return visitedComponents == totalComponents;
    }

    private int dfs(int row, int col, boolean[][] visited) {
        if (!isValidPosition(row, col)) return 0;
        if (visited[row][col] || components[row][col] == null) return 0;

        visited[row][col] = true;
        int count = 1;
        count += dfs(row + 1, col, visited);
        count += dfs(row - 1, col, visited);
        count += dfs(row, col + 1, visited);
        count += dfs(row, col - 1, visited);
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

    public List<SpaceshipComponent> getAllComponents() {
        List<SpaceshipComponent> list = new ArrayList<>();
        for (SpaceshipComponent[] row : components) {
            for (SpaceshipComponent c : row) {
                if (c != null) list.add(c);
            }
        }
        return list;
    }

    public SpaceshipComponent getComponent(Coordinates coordinates) {
        int x = coordinates.getX()-4;
        int y = coordinates.getY()-5;
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return null;
        return components[y][x];
    }

    public Coordinates getIndex(SpaceshipComponent goalComponent) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (components[i][j] == goalComponent) {
                    return new Coordinates(j+4, i+5);
                }
            }
        }
        return new Coordinates(-1, -1);
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

    /**
     * Calculates total firepower considering orientation and cannon type.
     */
    public int calculateFirepower(Direction shipForward) {
        int firepower = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                SpaceshipComponent component = components[row][col];
                if (component instanceof Cannon cannon) {
                    firepower += cannon.getEffectivePower(shipForward);
                }
            }
        }
        return firepower;
    }

    /**
     * Calculates total thrust considering engine orientation.
     */
    public int calculateThrust(Direction shipRear) {
        int thrust = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                SpaceshipComponent component = components[row][col];
                if (component instanceof Engine engine) {
                    if (engine.isCorrectlyOriented(shipRear)) {
                        thrust += engine.getEnginePower();
                    }
                }
            }
        }
        return thrust;
    }


    /**
     * Checks if a component at a given position is protected by an active shield facing a direction.
     * Accepts incoming side (FRONT, REAR, LEFT, RIGHT) and converts it to Direction internally.
     */
    public boolean isProtectedByShield(Position pos, Side incomingSide) {
        int row = pos.getX();
        int col = pos.getY();
        if (!isValidPosition(row, col)) return false;

        Direction incomingDirection = DirectionSideUtils.convertSideToDirection(incomingSide);

        for (Side side : Side.values()) {
            int[] offset = getOffset(side);
            int adjRow = row + offset[0];
            int adjCol = col + offset[1];
            if (isValidPosition(adjRow, adjCol)) {
                SpaceshipComponent neighbor = components[adjRow][adjCol];
                if (neighbor instanceof ShieldGenerator shield) {
                    Model.Enums.Direction shieldDir = shield.getDirection();
                    if (shieldDir == incomingDirection) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Applies damage to a given position if it is not protected by a shield.
     */
    public void applyDamage(Position pos, Side incomingSide) {
        int row = pos.getX();
        int col = pos.getY();
        if (!isValidPosition(row, col)) return;

        if (components[row][col] != null) {
            if (!isProtectedByShield(pos, incomingSide)) {
                components[row][col] = null;
            }
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

    public void reserveComponent(SpaceshipComponent component) throws InvalidMethodParameters {

        if (reservedComponents.size()<3) {
            reservedComponents.add(component);
        }
        else {
            throw new InvalidMethodParameters("There are already 2 reserved components");
        }
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




/**
 * Checks if there is at least one Engine with an alien onboard.
 *
 * @return true if any Engine has an alien.
 */
public boolean hasEngineAlien() {
    for (SpaceshipComponent[] row : components) {
        for (SpaceshipComponent comp : row) {
            if (comp instanceof Engine engine && engine.hasAlien()) {
                return true;
            }
        }
    }
    return false;
}

/**
 * Checks if there is at least one Cannon with an alien onboard.
 *
 * @return true if any Cannon has an alien.
 */
public boolean hasCannonAlien() {
    for (SpaceshipComponent[] row : components) {
        for (SpaceshipComponent comp : row) {
            if (comp instanceof Cannon cannon && cannon.hasAlien()) {
                return true;
            }
        }
    }
    return false;
}


/**
 * Returns a map of exposed connectors for each component.
 * Each entry maps a Position to a list of exposed Sides.
 */
public Map<Position, List<Side>> getExposedConnectors() {
    Map<Position, List<Side>> exposedConnectors = new HashMap<>();
    boolean[][] externalReachable = new boolean[ROWS][COLS];

    // Step 1: flood fill from borders to mark reachable empty spaces
    Queue<Position> queue = new LinkedList<>();
    for (int i = 0; i < ROWS; i++) {
        if (components[i][0] == null) queue.add(new Position(i, 0));
        if (components[i][COLS - 1] == null) queue.add(new Position(i, COLS - 1));
    }
    for (int j = 0; j < COLS; j++) {
        if (components[0][j] == null) queue.add(new Position(0, j));
        if (components[ROWS - 1][j] == null) queue.add(new Position(ROWS - 1, j));
    }

    while (!queue.isEmpty()) {
        Position pos = queue.poll();
        int row = pos.getX();
        int col = pos.getY();
        if (!isValidPosition(row, col)) continue;
        if (externalReachable[row][col]) continue;
        externalReachable[row][col] = true;

        // Add adjacent empty cells
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            int newRow = row + d[0];
            int newCol = col + d[1];
            if (isValidPosition(newRow, newCol) && components[newRow][newCol] == null) {
                queue.add(new Position(newRow, newCol));
            }
        }
    }

    // Step 2: for each component, check exposed connectors
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS; col++) {
            SpaceshipComponent comp = components[row][col];
            if (comp == null) continue;
            List<Side> exposed = new ArrayList<>();

            for (Side side : Side.values()) {
                ConnectorType connector = comp.getConnectorAt(side);
                if (connector == ConnectorType.NONE) continue;

                int[] offset = getOffset(side);
                int adjRow = row + offset[0];
                int adjCol = col + offset[1];

                boolean isExposed = false;
                if (!isValidPosition(adjRow, adjCol)) {
                    isExposed = true; // out of bounds = exposed
                } else if (components[adjRow][adjCol] == null) {
                    if (!externalReachable[adjRow][adjCol]) {
                        // It's an internal hole → counts as exposed
                        isExposed = true;
                    } else {
                        // It's connected to exterior → not an internal hole
                        isExposed = true   //qua va un false???????
                    }
                } else {
                    ConnectorType neighborConnector = components[adjRow][adjCol].getConnectorAt(getOppositeSide(side));
                    isExposed = !connectorsAreCompatible(connector, neighborConnector);
                }

                if (isExposed) {
                    exposed.add(side);
                }
            }

            if (!exposed.isEmpty()) {
                exposedConnectors.put(new Position(row, col), exposed);
            }
        }
    }

    return exposedConnectors;
}

public void resolveStardust() {
    Map<Position, List<Side>> exposedConnectors = getExposedConnectors();
    for (Position pos : exposedConnectors.keySet()) {
        int row = pos.getX();
        int col = pos.getY();
        components[row][col] = null;
    }
}
}
