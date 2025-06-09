package Model;

import Controller.Enums.MatchLevel;
import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Game;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private List<Player> players;
    private List<SpaceshipComponent> componentPool;
    private List<AdventureCardFilip> adventureCards;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Alice"));
        players.add(new Player("Bob"));

        componentPool = new ArrayList<>();
        SpaceshipComponent dummyComponent = new SpaceshipComponent() {
            @Override
            public boolean isVisible() {
                return true;
            }
        };

        for (int i = 0; i < 5; i++) {
            componentPool.add(dummyComponent);
        }

        adventureCards = new ArrayList<>();

        game = new Game(players, MatchLevel.TRIAL, componentPool, adventureCards);
    }

    @Test
    void testAddPlayer() {
        game.addPlayer("Charlie");
        assertNotNull(game.getPlayer("Charlie"));
        assertEquals(3, game.getPlayers().size());
    }

    @Test
    void testRemovePlayer() {
        game.removePlayer("Bob");
        assertNull(game.getPlayer("Bob"));
        assertEquals(1, game.getPlayers().size());
    }

    @Test
    void testGetPlayer() {
        Player p = game.getPlayer("Alice");
        assertNotNull(p);
        assertEquals("Alice", p.getName());
    }

    @Test
    void testGetPlayers() {
        List<Player> list = game.getPlayers();
        assertEquals(2, list.size());
    }

    @Test
    void testRollDice() {
        for (int i = 0; i < 100; i++) {
            int roll = game.rollDice();
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    void testPickComponent() {
        SpaceshipComponent picked = game.pickComponent(0);
        assertNotNull(picked);
        assertEquals(4, game.viewVisibleComponents().size());
    }

    @Test
    void testPickComponentInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> game.pickComponent(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> game.pickComponent(100));
    }

    @Test
    void testAddComponent() {
        SpaceshipComponent dummy = new SpaceshipComponent() {
            @Override
            public boolean isVisible() {
                return true;
            }
        };
        game.addComponent(dummy);
        assertEquals(6, game.viewVisibleComponents().size());
    }

    @Test
    void testViewVisibleComponents() {
        List<SpaceshipComponent> visible = game.viewVisibleComponents();
        assertEquals(5, visible.size());
    }

    @Test
    void testGetFlightBoard() {
        assertNotNull(game.getFlightboard());
    }

    @Test
    void testConstructorWithInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Game(null, MatchLevel.TRIAL, componentPool, adventureCards));
        assertThrows(IllegalArgumentException.class, () -> new Game(new ArrayList<>(), MatchLevel.TRIAL, componentPool, adventureCards));
        assertThrows(IllegalArgumentException.class, () -> new Game(players, null, componentPool, adventureCards));
        assertThrows(IllegalArgumentException.class, () -> new Game(players, MatchLevel.TRIAL, null, adventureCards));
        assertThrows(IllegalArgumentException.class, () -> new Game(players, MatchLevel.TRIAL, new ArrayList<>(), adventureCards));
    }
}
