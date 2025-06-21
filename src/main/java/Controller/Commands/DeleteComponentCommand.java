package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Exceptions.InvalidMethodParameters;
import Model.Ship.Coordinates;

/**
 * Command for deleting components from ship during damage resolution.
 * Used when components are destroyed by combat, meteors, or penalties.
 */
public class DeleteComponentCommand extends Command {
    /** The coordinates of the component to delete */
    private final Coordinates coordinates;
    
    /**
     * Constructs a new DeleteComponentCommand.
     *
     * @param playerName the name of the player whose component is being deleted
     * @param gameID the ID of the game session
     * @param coordinates the coordinates of the component to delete
     */
    public DeleteComponentCommand(String playerName, int gameID, Coordinates coordinates) {
        super(playerName, gameID);
        this.coordinates = coordinates;
    }
    
    /**
     * Executes the delete component command by calling the controller's deleteComponent method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters, InvalidMethodParameters {
        controller.deleteComponent(getPlayerName(), coordinates);
    }
    
    /**
     * Gets the coordinates of the component to delete.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
}