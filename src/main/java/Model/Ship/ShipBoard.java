package Model.Ship;

import Model.Enums.ConnectorType;
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

    public ShipBoard() {
        this.components = new SpaceshipComponent[ROWS][COLS];
    }

    public boolean addComponent(SpaceshipComponent component, int row, int col) {
        if (!isValidPosition(row, col)) return false;
        if (components[row][col] != null) return false;
        if (!isConnectedToExistingComponents(component, row, col) && !isEmpty()) return false;
        components[row][col] = component;
        component.setShipBoard(this);
        return true;
    }

    public void rotateComponent(int row, int col) {
        if (isValidPosition(row, col) && components[row][col] != null) {
            components[row][col].rotate();
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    private boolean isEmpty() {
        for (SpaceshipComponent[] row : components) {
            for (SpaceshipComponent c : row) {
                if (c != null) return false;
            }
        }
        return true;
    }

    private boolean isConnectedToExistingComponents(SpaceshipComponent component, int row, int col) {
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
        switch (side) {
            case FRONT:
                return new int[]{-1, 0};
            case REAR:
                return new int[]{1, 0};
            case LEFT:
                return new int[]{0, -1};
            case RIGHT:
                return new int[]{0, 1};
            default:
                return new int[]{0, 0};
        }
    }

    private Side getOppositeSide(Side side) {
        switch (side) {
            case FRONT:
                return Side.REAR;
            case REAR:
                return Side.FRONT;
            case LEFT:
                return Side.RIGHT;
            case RIGHT:
                return Side.LEFT;
            default:
                return side;
        }
    }

    private boolean connectorsAreCompatible(ConnectorType a, ConnectorType b) {
        if (a == ConnectorType.NONE || b == ConnectorType.NONE) return false;
        if (a == ConnectorType.UNIVERSAL || b == ConnectorType.UNIVERSAL) return true;
        return a == b;
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

    public List<SpaceshipComponent> getAllComponents() {
        List<SpaceshipComponent> list = new ArrayList<>();
        for (SpaceshipComponent[] row : components) {
            for (SpaceshipComponent c : row) {
                if (c != null) list.add(c);
            }
        }
        return list;
    }

    public SpaceshipComponent getComponent(int row, int col) {
        if (!isValidPosition(row, col)) return null;
        return components[row][col];
    }

    public void printOccupiedMatrix() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
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
     */
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
                            isExposed = true;
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

