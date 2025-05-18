package Model.Ship;

import Model.Enums.ConnectorType;
import Model.Exceptions.InvalidMethodParameters;
import Model.Enums.Direction;
import Model.Enums.Side;
import Model.Enums.Card;
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

    /**
     * Overloaded constructor to support variable size ships (for different levels).
     */
    public ShipBoard(int rows, int cols) {
        this.components = new SpaceshipComponent[rows][cols];
        this.activeComponent = null;
        this.reservedComponents = new ArrayList<>();
        this.condensedShip = new CondensedShip();
    }
    /**
     * Places a spaceship component on the board at the specified coordinates.
     * Ensures the placement is within board bounds, the position is unoccupied,
     * and the component is properly connected to existing components via compatible connectors.
     *
     * @param component The spaceship component to add.
     * @param coordinates The position on the board where the component should be placed.
     * @throws InvalidMethodParameters if the position is invalid, already occupied, or not connected properly.
     */
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

    /**
     * Removes a spaceship component from the board at the given coordinates.
     * Translates logical coordinates into grid indices and clears the position if it contains a component.
     * Throws an exception if coordinates are out of bounds.
     *
     * @param coordinates the location from which to remove the component
     * @throws InvalidMethodParameters if the coordinates are invalid
     */
    public void removeComponent(Coordinates coordinates) throws InvalidMethodParameters {
        int x = coordinates.getX()-4;
        int y = coordinates.getY()-5;

        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) throw new InvalidMethodParameters("Invalid coordinates out of bounds");
        if (components[y][x] != null) {
            components[y][x] = null;
        }
    }

    /**
     * Checks whether the given row and column indices are within the bounds of the ship grid.
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return true if the indices are within bounds, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    /**
     * Checks whether the ship board is entirely empty (contains no components).
     *
     * @return true if all cells in the board are null, false otherwise
     */
    public boolean isEmpty() {
        for (SpaceshipComponent[] row : components) {
            for (SpaceshipComponent c : row) {
                if (c != null) return false;
            }
        }
        return true;
    }


    /**
     * Checks if the given component can be connected to any existing component on the board
     * by verifying adjacent connectors for compatibility.
     *
     * @param component the component to place
     * @param row the target row in the board grid
     * @param col the target column in the board grid
     * @return true if there is at least one compatible adjacent connector, false otherwise
     */
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

    /**
     * Returns the relative coordinate offset for a given side of a component.
     * Used to find neighboring positions in the grid.
     *
     * @param side the side of the component (FRONT, REAR, LEFT, RIGHT)
     * @return an array containing the offset as [rowDelta, colDelta]
     */
    private int[] getOffset(Side side) {
        return switch (side) {
            case FRONT -> new int[]{-1, 0};
            case REAR -> new int[]{1, 0};
            case LEFT -> new int[]{0, -1};
            case RIGHT -> new int[]{0, 1};
        };
    }

    /**
     * Returns the opposite side of the given side.
     * Used to determine the side of a neighboring component for connection checks.
     *
     * @param side the original side
     * @return the side opposite to the input
     */
    private Side getOppositeSide(Side side) {
        return switch (side) {
            case FRONT -> Side.REAR;
            case REAR -> Side.FRONT;
            case LEFT -> Side.RIGHT;
            case RIGHT -> Side.LEFT;
        };
    }

    /**
     * Determines if two connectors are compatible with each other.
     * Universal connectors are compatible with any type, NONE is not compatible with anything.
     *
     * @param a the first connector
     * @param b the second connector
     * @return true if connectors are compatible, false otherwise
     */
    private boolean connectorsAreCompatible(ConnectorType a, ConnectorType b) {
        if (a == ConnectorType.NONE || b == ConnectorType.NONE) return false;
        if (a == ConnectorType.UNIVERSAL || b == ConnectorType.UNIVERSAL) return true;
        return a == b;
    }

    /**
     * Sets the active component currently being manipulated or selected.
     *
     * @param component the component to mark as active
     */
    public void setActiveComponent(SpaceshipComponent component) {
        this.activeComponent = component;
    }
    /**
     * Retrieves the currently active component.
     *
     * @return the active component instance, or null if none is set
     */
    public SpaceshipComponent getActiveComponent() {
        return activeComponent;
    }

    /**
     * Checks whether the ship is still structurally connected after potential damage.
     * Uses depth-first search to ensure all components are connected starting from the first one found.
     *
     * @return true if all components are reachable and the ship is whole, false otherwise
     */
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

    /**
     * Performs a depth-first search to count connected components starting from a given cell.
     *
     * @param row the row to start from
     * @param col the column to start from
     * @param visited a matrix to mark visited cells
     * @return the total number of connected components found
     */
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

    /**
     * Calculates the rectangular boundary of the ship on the grid.
     * Iterates through all cells to find the minimum and maximum rows and columns
     * where components are placed. Returns null if the ship is empty.
     *
     * @return an array of four integers: [minRow, maxRow, minCol, maxCol], or null if empty
     */
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

    /**
     * Returns a list of all components currently placed on the ship board.
     * Iterates through the grid and collects all non-null components.
     *
     * @return a list of all spaceship components on the board
     */
    public List<SpaceshipComponent> getAllComponents() {
        List<SpaceshipComponent> list = new ArrayList<>();
        for (SpaceshipComponent[] row : components) {
            for (SpaceshipComponent c : row) {
                if (c != null) list.add(c);
            }
        }
        return list;
    }

    /**
     * Retrieves the component located at the given coordinates.
     * Converts logical coordinates to grid indices and returns the component if within bounds.
     *
     * @param coordinates the logical board coordinates
     * @return the component at the specified location, or null if out of bounds or empty
     */
    public SpaceshipComponent getComponent(Coordinates coordinates) {
        int x = coordinates.getX()-4;
        int y = coordinates.getY()-5;
        if (x < 0 || x >= ROWS || y < 0 || y >= COLS) return null;
        return components[y][x];
    }

    /**
     * Searches for the specified component in the grid and returns its coordinates.
     * If not found, returns (-1, -1).
     *
     * @param goalComponent the component to locate on the board
     * @return the coordinates of the component or (-1, -1) if not found
     */
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

    /**
     * Prints a visual matrix of the ship grid to the console.
     * Displays '[X]' for occupied positions and '[ ]' for empty ones
     * within the active ship boundaries.
     */
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
     * Calculates the total firepower of the ship.
     * Considers all cannon components and their orientation relative to the ship's forward direction.
     *
     * @param shipForward the direction the ship is facing
     * @return the sum of effective power from all cannons
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
    /**
     * Calculates the total thrust produced by all engines correctly oriented to the rear of the ship.
     * Only engines that face the specified rear direction contribute their engine power.
     *
     * @param shipRear the direction that represents the rear of the ship
     * @return the total thrust value from all correctly oriented engines
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
    /**
     * Determines if the component at the specified position is protected by a shield
     * from the direction corresponding to the incoming side.
     * Converts the side to a direction and checks adjacent shield generators facing that direction.
     *
     * @param pos the position of the component being checked
     * @param incomingSide the side from which the attack is coming
     * @return true if the component is protected by a shield, false otherwise
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
    /**
     * Applies damage to the component at the given position.
     * If the component is not protected by a shield in the incoming direction, it is removed (set to null).
     *
     * @param pos the position to apply damage to
     * @param incomingSide the side from which the damage is incoming
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
     * Validates the structure and rules compliance of the current ship configuration.
     * Checks for connector compatibility, blocked engines or cannons, disconnected cabins,
     * and special component requirements like alien life support adjacency.
     *
     * @return true if the ship structure is valid according to game rules, false otherwise
     */
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
                        Side side = d[0] == -1 ? Side.FRONT :
                                d[0] == 1 ? Side.REAR :
                                        d[1] == -1 ? Side.LEFT : Side.RIGHT;
                        ConnectorType thisConn = comp.getConnectorAt(side);
                        Side oppositeSide = getOppositeSide(side);
                        ConnectorType otherConn = components[nx][ny].getConnectorAt(oppositeSide);
                        if (!connectorsAreCompatible(thisConn, otherConn)) {
                            System.out.printf("Errore: connettori incompatibili tra (%d,%d) e (%d,%d)\n", x, y, nx, ny);
                            return false;
                        }
                    }
                }

                Card card = comp.getType();

                if (card.hasEngine() && comp.getConnectorAt(Side.REAR) != ConnectorType.NONE) {
                    if (x + 1 < ROWS && components[x + 1][y] != null) {
                        System.out.printf("Errore: motore in (%d,%d) ha un modulo dietro\n", x, y);
                        return false;
                    }
                }

                if (card.hasCannon() && comp.getConnectorAt(Side.FRONT) != ConnectorType.NONE) {
                    if (x - 1 >= 0 && components[x - 1][y] != null) {
                        System.out.printf("Errore: cannone in (%d,%d) bloccato da un modulo davanti\n", x, y);
                        return false;
                    }
                }

                if (card == Card.CABIN && comp.getConnectorAt(Side.FRONT) == ConnectorType.NONE
                        && comp.getConnectorAt(Side.REAR) == ConnectorType.NONE
                        && comp.getConnectorAt(Side.LEFT) == ConnectorType.NONE
                        && comp.getConnectorAt(Side.RIGHT) == ConnectorType.NONE) {
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
                    if (comp.getConnectorAt(Side.FRONT) == ConnectorType.NONE && comp.getConnectorAt(Side.REAR) == ConnectorType.NONE) {
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


    /**
     * Identifies all spaceship components on the board with exposed connectors.
     * A connector is considered exposed if it is adjacent to an empty cell, out-of-bounds space,
     * or incompatible connector. The method uses flood-fill to distinguish interior voids
     * from actual exposure to external space.
     *
     * @return a map linking each exposed component position to a list of its exposed sides
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
                        isExposed = true;
                    } else if (components[adjRow][adjCol] == null) {
                        isExposed = true;
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
}


