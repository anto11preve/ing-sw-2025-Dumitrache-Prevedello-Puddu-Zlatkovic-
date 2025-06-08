package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for finishing ship building and choosing starting position.
 * Completes the building phase for a player.
 */
public class FinishBuildingCommand extends Command {
    /** The desired starting position on the flight track */
    private final int position;
    
    /**
     * Constructs a new FinishBuildingCommand.
     *
     * @param playerName the name of the player finishing building
     * @param gameID the ID of the game session
     * @param position the desired starting position
     */
    public FinishBuildingCommand(String playerName, int gameID, int position) {
        super(playerName, gameID);
        this.position = position;
    }
    
    /**
     * Executes the finish building command by calling the controller's finishBuilding method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {
        controller.finishBuilding(getPlayerName(), position);
    }
    
    /**
     * Gets the starting position.
     *
     * @return the starting position
     */
    public int getPosition() {
        return position;
    }
}