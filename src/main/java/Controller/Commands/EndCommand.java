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
     */
    public EndCommand(String playerName) {
        super(playerName);
    }
    
    /**
     * Executes the end command by calling the controller's end method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters, InvalidMethodParameters{
        controller.end(getPlayerName());
    }
}