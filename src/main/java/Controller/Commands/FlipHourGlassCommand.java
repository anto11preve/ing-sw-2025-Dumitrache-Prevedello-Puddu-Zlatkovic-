package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for flipping the hourglass timer during building phase.
 * Advances the timer to the next phase (Level 2 only).
 */
public class FlipHourGlassCommand extends Command {
    
    /**
     * Constructs a new FlipHourGlassCommand.
     *
     * @param playerName the name of the player flipping the hourglass
     * @param gameID the ID of the game session
     */
    public FlipHourGlassCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the flip hourglass command by calling the controller's flipHourGlass method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {
        controller.flipHourGlass(getPlayerName());
    }
}