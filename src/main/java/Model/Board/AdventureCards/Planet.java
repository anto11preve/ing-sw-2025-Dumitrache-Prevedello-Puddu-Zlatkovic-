package Model.Board.AdventureCards;

import Model.Enums.Good;
import Model.Player;

import java.util.List;

class Planet {
    private String color;
    private List<Good> availableGoods;
    private boolean occupied;

    public Planet(String color, List<Good> availableGoods) {
        this.color = color;
        this.availableGoods = availableGoods;
        this.occupied = false;
    }

    public String getColor() {
        return color;
    }

    public void land(Player player) { //where do we use player?
        if (occupied) {
            //to do
        } else {
            occupied = true;
        }
    }

    public List<Good> getAvailableGoods() {
        return availableGoods;
    }

    public void getGoods(Player player) {
        //to do
    }
}
