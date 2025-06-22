package Model;

import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.CardDeck;
import Model.Board.FlightBoard;
import Model.Enums.Card;
import Model.Enums.CardLevel;
import Controller.Enums.MatchLevel;
import Model.ComponentLoader;
import Controller.State;
import Model.Enums.Side;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a game session of Galaxy Trucker.
 * Manages players, the tile pool, the flight board, and overall game state.
 */
public class Game {
    private final List<Player> players;
    private final MatchLevel level;
    private final SpaceshipComponent[] tiles;
    private final FlightBoard flightBoard;
    private State state;
    private boolean error = false;


    public static void main(String[] args) {

        Game testGame = new Game(MatchLevel.TRIAL);
        Game testGame2 = new Game(MatchLevel.LEVEL2);

        SpaceshipComponent[] tiless = ComponentLoader.loadComponents();

        for (SpaceshipComponent tile : tiless) {
            tile.visualize();
        }

    }

    /**
     * Constructs a new Game instance based only on the match level.
     * Automatically loads components and adventure cards.
     *
     * @param level match difficulty level
     */
    public Game(MatchLevel level) {
        if (level == null)
            throw new IllegalArgumentException("Match level must not be null");

        this.players = new ArrayList<>();
        this.level = level;
        this.tiles = ComponentLoader.loadComponents();

        List<AdventureCardFilip> cards = AdventureCardLoader.loadAdventureCards(level);
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("Failed to load adventure cards");
        }

        if (level == MatchLevel.TRIAL) {
            List<AdventureCardFilip> learnerCards = cards.stream()
                    .filter(c -> c.getLevel() == CardLevel.LEARNER)
                    .collect(Collectors.toList());
            
            if (learnerCards.size() < 8) {
                throw new IllegalStateException("Not enough LEARNER cards available. Found: " + learnerCards.size());
            }
            
            // Take only the first 8 cards
            learnerCards = learnerCards.subList(0, 8);
            CardDeck hiddenDeck = new CardDeck(learnerCards);
            this.flightBoard = new FlightBoard(hiddenDeck);
        } else {
            List<AdventureCardFilip> level1Cards = cards.stream()
                    .filter(c -> c.getLevel() == CardLevel.LEVEL_ONE)
                    .collect(Collectors.toList());
            List<AdventureCardFilip> level2Cards = cards.stream()
                    .filter(c -> c.getLevel() == CardLevel.LEVEL_TWO)
                    .collect(Collectors.toList());
            
            if (level1Cards.size() < 8 || level2Cards.size() < 8) {
                throw new IllegalStateException(String.format(
                    "Not enough cards for LEVEL2 game. Found: LEVEL_ONE=%d, LEVEL_TWO=%d", 
                    level1Cards.size(), level2Cards.size()));
            }

            List<CardDeck> pickableDecks = new ArrayList<>();

            // Creates 4 lists of AdventureCardFilip, each containg 1 card of level1 and 2 of level2
            List<AdventureCardFilip> peekable1 = level1Cards.subList(0, 1);
            peekable1.addAll(level2Cards.subList(0, 2));
            CardDeck peekableDeck1 = new CardDeck(peekable1);
            pickableDecks.add(peekableDeck1);

            List<AdventureCardFilip> peekable2 = level1Cards.subList(1, 2);
            peekable2.addAll(level2Cards.subList(2, 4));
            CardDeck peekableDeck2 = new CardDeck(peekable2);
            pickableDecks.add(peekableDeck2);

            List<AdventureCardFilip> peekable3 = level1Cards.subList(2, 3);
            peekable3.addAll(level2Cards.subList(4, 6));
            CardDeck peekableDeck3 = new CardDeck(peekable3);
            pickableDecks.add(peekableDeck3);


            List<AdventureCardFilip> hidden = level1Cards.subList(3, 4);
            hidden.addAll(level2Cards.subList(6, 8));
            CardDeck hiddenDeck = new CardDeck(hidden);



            this.flightBoard = new FlightBoard(hiddenDeck, pickableDecks);
        }
    }

    public SpaceshipComponent[] getTiles() {
        return this.tiles;
    }

    public void addPlayer(String name) {
        this.players.add(new Player(name));
    }

    public void removePlayer(String name) {
        players.removeIf(p -> p.getName().equals(name));
    }

    public Player getPlayer(String name) {
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public int rollDice() {
        return (int)(Math.random() * 6 + 1);
    }

    public SpaceshipComponent pickComponent(int index) {
        if (index < 0 || index >= tiles.length || tiles[index] == null)
            throw new IndexOutOfBoundsException("Invalid component index");
        SpaceshipComponent picked = tiles[index];
        tiles[index] = null;
        return picked;
    }

    public void addComponent(SpaceshipComponent component) {
        if (component == null)
            throw new IllegalArgumentException("Component cannot be null");
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                tiles[i] = component;
                return;
            }
        }
        throw new IllegalStateException("No space left to add the component");
    }

    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent> visible = new ArrayList<>();
        for (SpaceshipComponent c : tiles) {
            if (c != null && c.isVisible()) visible.add(c);
        }
        return visible;
    }

    public FlightBoard getFlightBoard() {
        return this.flightBoard;
    }

    public MatchLevel getLevel() {
        return this.level;
    }

    public void setState(State phase) {
        this.state = phase;
    }

    public State getState() {
        return state;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
