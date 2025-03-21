package Model;

import java.util.ArrayList;
import java.util.List;

// Classe principale del giocatore
class Player {
    private final String name;
    private ShipBoard shipBoard;
    private int credits;
    private int junk;

    public Player(String name) {
        this.name = name;
        this.shipBoard = new ShipBoard();
        this.credits = 0;
        this.junk = 0;
    }
    public String getName(){ return name; }

    public ShipBoard getShipBoard() { return shipBoard; }

    public void deltaCredits(int delta) { credits += delta; }
    public int getCredits() { return credits; }

    public void addJunk() { junk ++; }
    public int getJunk() { return junk; }
}
