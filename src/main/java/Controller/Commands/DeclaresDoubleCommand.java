package Controller.Commands;

import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Model.Exceptions.InvalidMethodParameters;

/**
 * Command for declaring the use of double components (engines or cannons).
 * Players declare how many double components they want to activate.
 */
public class DeclaresDoubleCommand extends Command {
    /** The type of double component being declared */
    private final DoubleType doubleType;
    /** The amount of double components to activate */
    private final int amount;
    
    /**
     * Constructs a new DeclaresDoubleCommand.
     *
     * @param playerName the name of the player making the declaration
     * @param doubleType the type of double component
     * @param amount the amount to declare
     */
    public DeclaresDoubleCommand(String playerName, DoubleType doubleType, int amount) {
        super(playerName);
        this.doubleType = doubleType;
        this.amount = amount;
    }
    
    /**
     * Executes the declares double command by calling the controller's declaresDouble method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters, InvalidMethodParameters, InvalidContextualAction {
        controller.declaresDouble(getPlayerName(), doubleType, amount);
    }
    
    /**
     * Gets the double type.
     *
     * @return the double type
     */
    public DoubleType getDoubleType() {
        return doubleType;
    }
    
    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }
}