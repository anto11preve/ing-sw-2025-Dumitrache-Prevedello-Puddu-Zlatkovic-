package Model;

import java.util.ArrayList;
import java.util.List;

// Classe ShipBoard che rappresenta la nave del giocatore
class ShipBoard {
    private SpaceshipComponent[][] components;
    private SpaceshipComponent activeComponent;
    private List<SpaceshipComponent> reservedComponents;
    private CondensedShip condensedShip;

    public ShipBoard() {
        this.components = new SpaceshipComponent[5][7];
        this.activeComponent = null;
        this.reservedComponents = new ArrayList<>();
        this.condensedShip = new CondensedShip();
    }

    public boolean addComponent(SpaceshipComponent component, int x, int y) {
        if (components[x][y] == null) {
            components[x][y] = component;
            return true;
        }
        return false;
    }

    public void removeComponent(int x, int y, Player player) {
        components[x][y] = null;
        player.addJunk();
    }

    public SpaceshipComponent getComponent(int x, int y) {
        return components[x][y];
    }

    public boolean checkIntegrity() {
        boolean[][] visited = new boolean[5][7];
        int total = 0;
        int startX = -1, startY = -1;

        // Trova componente iniziale e conta i totali
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (components[i][j] != null) {
                    total++;
                    if (startX == -1) {
                        startX = i;
                        startY = j;
                    }
                }
            }
        }

        if (total == 0) return true; // nessun modulo

        int visitedCount = dfs(startX, startY, visited);
        return visitedCount == total;
    }

    private int dfs(int x, int y, boolean[][] visited) {
        if (x < 0 || y < 0 || x >= 5 || y >= 7) return 0;
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (components[i][j] == goalComponent) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
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
