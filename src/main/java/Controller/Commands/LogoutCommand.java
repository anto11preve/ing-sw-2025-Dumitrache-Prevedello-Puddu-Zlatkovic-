// LogoutCommand.java
package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for handling player logout from a game session.
 * Removes the player from the current game.
 */
public class LogoutCommand extends Command {

    /**
     * Constructs a new LogoutCommand.
     *
     * @param playerName the name of the player attempting to logout
     * @param gameID the ID of the game session to leave
     */
    public LogoutCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }

    /**
     * Executes the logout command by calling the controller's logout method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {

        controller.logout(getPlayerName());
    }
}
