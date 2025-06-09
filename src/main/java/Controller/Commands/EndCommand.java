package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Exceptions.InvalidMethodParameters;

/**
 * Command for ending the current player's turn or action.
 * Used to signal completion of various game phases.
 */
public class EndCommand extends Command {
    
    /**
     * Constructs a new EndCommand.
     *
     * @param playerName the name of the player ending their action
     * @param gameID the ID of the game session
     */
    public EndCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the end command by calling the controller's end method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.end(getPlayerName());
        } catch (InvalidCommand | InvalidParameters | InvalidMethodParameters e) {
            System.err.println("Failed to end action: " + e.getMessage());
        }
    }
}