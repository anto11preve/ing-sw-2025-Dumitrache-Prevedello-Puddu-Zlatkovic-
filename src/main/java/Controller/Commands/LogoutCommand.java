// LogoutCommand.java
package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import View.Client.Actions.Action;
import View.Client.ClientState;

import java.util.List;
import java.util.Map;

/**
 * Command for handling player logout from a game session.
 * Removes the player from the current game.
 */
public class LogoutCommand extends Command {

    /**
     * Constructs a new LogoutCommand.
     *
     * @param playerName the name of the player attempting to logout
     */
    public LogoutCommand(String playerName) {
        super(playerName);
    }

    /**
     * Executes the logout command by calling the controller's logout method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {
        controller.setQueuedAction(new Action() {
            @Override
            public ClientState execute(ClientState state) {
                return state.net_Leave(LogoutCommand.this.getPlayerName());
            }
        });
        controller.logout(getPlayerName());
    }

    public static CommandConstructor getConstructor() {
        return new CommandConstructor() {
            @Override
            public Command create(String username, Map<String, String> args) throws IllegalArgumentException {

                return new LogoutCommand(username);
            }

            @Override
            public List<String> getArguments() {
                return List.of();
            }
        };
    }
}
