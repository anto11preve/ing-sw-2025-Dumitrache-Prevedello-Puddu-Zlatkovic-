package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for reserving a component during ship building phase.
 * Players can reserve up to 2 components for later use (Level 2 only).
 */
public class ReserveComponentCommand extends Command {
    
    /**
     * Constructs a new ReserveComponentCommand.
     *
     * @param playerName the name of the player reserving the component
     */
    public ReserveComponentCommand(String playerName) {
        super(playerName);
    }
    
    /**
     * Executes the reserve component command by calling the controller's reserveComponent method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters {
        controller.reserveComponent(getPlayerName());
    }
}