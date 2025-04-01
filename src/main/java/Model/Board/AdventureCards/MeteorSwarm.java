package Model.Board.AdventureCards;

import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.List;

public class MeteorSwarm extends AdventureCard{
    private final List<Meteor> meteors;

    public MeteorSwarm(int id, CardLevel level) {
        super(id, level);
        this.meteors = new ArrayList<>();
    }

    public List<Meteor> getMeteors() {
        return meteors;
    }
}
