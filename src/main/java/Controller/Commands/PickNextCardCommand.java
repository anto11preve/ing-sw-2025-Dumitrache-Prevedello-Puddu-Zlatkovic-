package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for picking the next adventure card during flight phase.
 * The leader draws and resolves the next card from the adventure deck.
 */
public class PickNextCardCommand extends Command {
    
    /**
     * Constructs a new PickNextCardCommand.
     *
     * @param playerName the name of the player (leader) picking the card
     * @param gameID the ID of the game session
     */
    public PickNextCardCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the pick next card command by calling the controller's pickNextCard method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.pickNextCard(getPlayerName());
        } catch (InvalidCommand | InvalidParameters | InvalidContextualAction e) {
            System.err.println("Failed to pick next card: " + e.getMessage());
        }
    }
}