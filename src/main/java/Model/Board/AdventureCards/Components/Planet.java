package Model.Board.AdventureCards.Components;

import Model.Board.AdventureCards.Rewards.Goods;
import Model.Enums.Good;

import java.util.List;

public class Planet {
    private String name;
    private final Goods landingReward;
    private boolean occupied;

    public Planet(String name, List<Good> goods) {
        this.name = name;
        this.landingReward = new Goods(goods);
        this.occupied = false;
    }

    public final String getName() {
        return name;
    }

    public final boolean isOccupied() {
        return occupied;
    }

    public final boolean setOccupied() {
        if(occupied)
            return false;

        occupied = true;
        return true;
    }

    public final Goods getLandingReward() {
        return landingReward;
    }
}
