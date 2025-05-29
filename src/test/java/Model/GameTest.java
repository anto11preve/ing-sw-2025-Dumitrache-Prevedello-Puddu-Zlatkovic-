package Model;

import Model.Board.FlightBoard;
import Model.Ship.Components.SpaceshipComponent;
import Controller.Enums.MatchLevel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Game class, which holds the full state of a match.
 */
public class GameTest {

    @Test
    public void testConstructorInitializesFieldsCorrectly() {
        // Create sample players
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        List<Player> players = List.of(p1, p2);

        // Create an empty flight board and component pool
        FlightBoard board = new FlightBoard();
        List<SpaceshipComponent> components = new ArrayList<>();

        // Set match difficulty
        MatchLevel level = MatchLevel.TRIAL;

        // Create game instance
        Game game = new Game(players, board, level, components);

        // Assertions to verify the integrity of game initialization
        assertEquals(players, game.getPlayers(), "Players list should match input");
        assertEquals(board, game.getFlightBoard(), "FlightBoard should be correctly assigned");
        assertEquals(level, game.getLevel(), "MatchLevel should be TRIAL");
        assertEquals(components, game.getComponentsPool(), "Components pool should match input list");
    }

    @Test
    public void testPlayersAreSharedReferences() {
        // Setup
        Player player = new Player("TestPlayer");
        List<Player> players = List.of(player);
        FlightBoard board = new FlightBoard();
        List<SpaceshipComponent> components = new ArrayList<>();
        Game game = new Game(players, board, MatchLevel.LEVEL2, components);

        // Change player state externally
        player.deltaCredits(5);

        // Then the reference in the game should reflect the change
        assertEquals(5, game.getPlayers().get(0).getCredits(), "Player reference should reflect external state change");
    }
}
