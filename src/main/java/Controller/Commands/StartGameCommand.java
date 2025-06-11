//TODO: new game creation shouldn't be done by the controller directly, since a new controller must be created for each game.

// StartGameCommand.java
package Controller.Commands;

import Controller.Controller;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for creating a new game session.
 * Only the first player can create a new game with specified difficulty level.
 */
public class StartGameCommand extends Command {
    /** The difficulty level for the new game */
    private final MatchLevel level;

    /**
     * Constructs a new StartGameCommand.
     *
     * @param playerName the name of the player creating the game
     * @param gameID the ID for the new game session
     * @param level the difficulty level of the game
     */
    public StartGameCommand(String playerName, int gameID, MatchLevel level) {
        super(playerName, gameID);
        this.level = level;
    }

    /**
     * Executes the new game command by calling the controller's startGame method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {
        controller.startGame(getPlayerName());
    }

    /**
     * Gets the match level for this game.
     *
     * @return the match level
     */
    public MatchLevel getLevel() {
        return level;
    }
}