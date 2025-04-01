package Model;

// Classe ShipBoard che rappresenta la nave del giocatore
class ShipBoard {
    private SpaceshipComponent[][] components;
    private SpaceshipComponent activeComponent;
    private SpaceshipComponent[] reservedComponents;
    private CondensedShip condensedShip;

    public ShipBoard() {
        this.components = new SpaceshipComponent[5][7];
        this.activeComponent = null;
        this.reservedComponents = new SpaceshipComponent[2];
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
        //to do
        return true;
    }

    public void setActiveComponent(SpaceshipComponent component) {
        activeComponent = component;
    }

    public int[] getIndex(SpaceshipComponent goalComponent) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (components[i][j] == goalComponent) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public boolean reserveComponent(SpaceshipComponent component) {
        for( int i = 0; i < reservedComponents.length; i++) {
            if (reservedComponents[i] == null) {
                reservedComponents[i] = component;
                return true;
            }
        }
        return false;
    }

    public void removeReservedComponent(SpaceshipComponent component) {
        for( int i = 0; i < reservedComponents.length; i++) {
            if (reservedComponents[i] == component) {
                reservedComponents[i] = null;
                return;
            }
        }
    }

    public SpaceshipComponent[] getReservedComponents() {
        return reservedComponents;
    }

    public CondensedShip getCondensedShip() {
        return condensedShip;
    }

}
