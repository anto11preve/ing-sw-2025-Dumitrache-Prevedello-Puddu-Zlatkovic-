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
        //to do
        return true;
    }

    public void setActiveComponent(SpaceshipComponent component) {
        //to do
    }

    public int[2]

    getIndex(SpaceshipComponent goalComponent) {
        //to do
        return new int[2];
    }

    public void reserveComponent(SpaceshipComponent component) {
        //to do
    }

    public void removeReservedComponent(SpaceshipComponent component) {
        //to do
    }

    public List<SpaceshipComponent> getReservedComponents() {
        return reservedComponents;
    }

    public CondensedShip getCondensedShip() {
        return condensedShip;
    }

}
