package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for defending against attacks during combat.
 * Used when players choose to defend rather than attack.
 */
public class DefendCommand extends Command {
    
    /**
     * Constructs a new DefendCommand.
     *
     * @param playerName the name of the player defending
     * @param gameID the ID of the game session
     */
    public DefendCommand(String playerName, int gameID) {
        super(playerName, gameID);
    }
    
    /**
     * Executes the defend command by calling the controller's defend method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.defend(getPlayerName());
        } catch (InvalidCommand | InvalidParameters e) {
            System.err.println("Failed to defend: " + e.getMessage());
        }
    }
}