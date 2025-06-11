package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Model.Ship.Coordinates;

/**
 * Command for getting a specific good and placing it in a cargo hold.
 * Used during cargo collection phases from planets or rewards.
 */
public class GetGoodCommand extends Command {
    /** The index of the good to get */
    private final int goodIndex;
    /** The coordinates of the target cargo hold */
    private final Coordinates coordinates;
    /** The index within the cargo hold to place the good */
    private final int cargoHoldIndex;
    
    /**
     * Constructs a new GetGoodCommand.
     *
     * @param playerName the name of the player getting the good
     * @param gameID the ID of the game session
     * @param goodIndex the index of the good to get
     * @param coordinates the coordinates of the cargo hold
     * @param cargoHoldIndex the index within the cargo hold
     */
    public GetGoodCommand(String playerName, int gameID, int goodIndex, 
                         Coordinates coordinates, int cargoHoldIndex) {
        super(playerName, gameID);
        this.goodIndex = goodIndex;
        this.coordinates = coordinates;
        this.cargoHoldIndex = cargoHoldIndex;
    }
    
    /**
     * Executes the get good command by calling the controller's getGood method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters, InvalidContextualAction {
        controller.getGood(getPlayerName(), goodIndex, coordinates, cargoHoldIndex);
    }
    
    /**
     * Gets the good index.
     *
     * @return the good index
     */
    public int getGoodIndex() {
        return goodIndex;
    }
    
    /**
     * Gets the coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    
    /**
     * Gets the cargo hold index.
     *
     * @return the cargo hold index
     */
    public int getCargoHoldIndex() {
        return cargoHoldIndex;
    }
}