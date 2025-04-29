package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.CardLevel;

import java.util.Iterator;
import java.util.List;

public class MeteorSwarm extends AdventureCardFilip implements Iterable<Meteor> {
    private final List<Meteor> meteors;

    public MeteorSwarm(int id, CardLevel level, List<Meteor> meteors) {
        super(id, level);

        this.meteors = meteors;
    }

    @Override
    public String getName() {
        return "Pioggia di Meteoriti";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Iterator<Meteor> iterator() {
        return meteors.iterator();
    }
}
