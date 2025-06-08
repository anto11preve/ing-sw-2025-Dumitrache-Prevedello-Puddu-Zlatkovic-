package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Exceptions.InvalidMethodParameters;

/**
 * Command for throwing dice during various game events.
 * Used for determining meteor impacts, combat results, and other random events.
 */
public class ThrowDicesCommand extends Command {
    
    /**
     * Constructs a new ThrowDicesCommand.
     *
     * @param playerName the name of the player throwing the dice
     * @param gameID the ID of the game session
     */
    public ThrowDicesCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the throw dices command by calling the controller's throwDices method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.throwDices(getPlayerName());
        } catch (InvalidCommand | InvalidParameters | InvalidMethodParameters e) {
            System.err.println("Failed to throw dices: " + e.getMessage());
        }
    }
}