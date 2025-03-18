package Model;

import java.util.HashMap;
import java.util.Map;

//we're missing the 4 piles of cards
class Flightboard {
    private AdventureCard[] adventureCards;
    //private int currentCard;
    private Map<Player, Integer> playerPositions;

    public Flightboard() {
        this.adventureCards = new AdventureCard[12];
        //this.currentCard=0;
        this.playerPositions = new HashMap<>();
    }

    //public Model.AdventureCard nextAdventure() { }
    public void updatePosition(Player player, int position) {
        playerPositions.put(player, position);
    }

    public Player[] getTurnOrder() {
        return playerPositions.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toArray(Player[]::new);
    }
}
