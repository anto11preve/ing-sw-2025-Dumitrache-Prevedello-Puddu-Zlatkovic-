package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for voluntarily leaving the race during flight phase.
 * Players can abandon the race to limit losses.
 */
public class LeaveRaceCommand extends Command {
    
    /**
     * Constructs a new LeaveRaceCommand.
     *
     * @param playerName the name of the player leaving the race
     * @param gameID the ID of the game session
     */
    public LeaveRaceCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the leave race command by calling the controller's leaveRace method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.leaveRace(getPlayerName());
        } catch (InvalidCommand | InvalidParameters e) {
            System.err.println("Failed to leave race: " + e.getMessage());
        }
    }
}