package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for skipping available rewards.
 * Players can choose to skip rewards to avoid penalties or save time.
 */
public class SkipRewardCommand extends Command {
    
    /**
     * Constructs a new SkipRewardCommand.
     *
     * @param playerName the name of the player skipping the reward
     * @param gameID the ID of the game session
     */
    public SkipRewardCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the skip reward command by calling the controller's skipReward method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.skipReward(getPlayerName());
        } catch (InvalidCommand | InvalidParameters e) {
            System.err.println("Failed to skip reward: " + e.getMessage());
        }
    }
}