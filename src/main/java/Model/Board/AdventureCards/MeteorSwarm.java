package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.List;

public class MeteorSwarm extends AdventureCard {
    private int lastMeteor;
    private final List<Meteor> meteors;

    public MeteorSwarm(int id, CardLevel level, List<Meteor> meteors) {
        super(id, level);
        lastMeteor = -1;
        this.meteors = meteors;
    }

    public Meteor getNextMeteor(){
        if(lastMeteor + 1 >= meteors.size()){
            return null;
        }

        lastMeteor++;
        
        return meteors.get(lastMeteor);
    }

    @Override
    public String getName() {
        return "Pioggia di Meteoriti";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
