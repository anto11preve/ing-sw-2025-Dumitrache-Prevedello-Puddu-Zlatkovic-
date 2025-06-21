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
     */
    public SkipRewardCommand(String playerName) {
        super(playerName);
    }
    
    /**
     * Executes the skip reward command by calling the controller's skipReward method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters{
        controller.skipReward(getPlayerName());
    }
}