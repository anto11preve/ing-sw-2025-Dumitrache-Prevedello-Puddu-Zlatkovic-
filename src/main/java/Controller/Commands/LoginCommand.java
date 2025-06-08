// LoginCommand.java
package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for handling player login to a game session.
 * Allows a player to join an existing game by providing their name.
 */
public class LoginCommand extends Command {

    /**
     * Constructs a new LoginCommand.
     *
     * @param playerName the name of the player attempting to login
     * @param gameID the ID of the game session to join
     */
    public LoginCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }

    /**
     * Executes the login command by calling the controller's login method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {
        //TODO: login attualmente è l'unica exception che viene gestita nel controller ma lo faremo gestire al thread che lo esegue
        controller.login(getPlayerName());
    }
}
