package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Model.Ship.Coordinates;

/**
 * Command for moving goods between cargo holds.
 * Used during cargo management phases.
 */
public class MoveGoodsCommand extends Command {
    /** The coordinates of the source cargo hold */
    private final Coordinates oldCoordinates;
    /** The coordinates of the destination cargo hold */
    private final Coordinates newCoordinates;
    /** The index of the good in the source cargo hold */
    private final int oldIndex;
    /** The target index in the destination cargo hold */
    private final int newIndex;
    
    /**
     * Constructs a new MoveGoodsCommand.
     *
     * @param playerName the name of the player moving goods
     * @param gameID the ID of the game session
     * @param oldCoordinates the source coordinates
     * @param newCoordinates the destination coordinates
     * @param oldIndex the source index
     * @param newIndex the destination index
     */
    public MoveGoodsCommand(String playerName, int gameID, Coordinates oldCoordinates, 
                           Coordinates newCoordinates, int oldIndex, int newIndex) {
        super(playerName);
        this.oldCoordinates = oldCoordinates;
        this.newCoordinates = newCoordinates;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
    }
    
    /**
     * Executes the move goods command by calling the controller's moveGood method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.moveGood(getPlayerName(), oldCoordinates, newCoordinates, oldIndex, newIndex);
        } catch (InvalidCommand | InvalidParameters | InvalidContextualAction e) {
            System.err.println("Failed to move goods: " + e.getMessage());
        }
    }
    
    /**
     * Gets the old coordinates.
     *
     * @return the old coordinates
     */
    public Coordinates getOldCoordinates() {
        return oldCoordinates;
    }
    
    /**
     * Gets the new coordinates.
     *
     * @return the new coordinates
     */
    public Coordinates getNewCoordinates() {
        return newCoordinates;
    }
    
    /**
     * Gets the old index.
     *
     * @return the old index
     */
    public int getOldIndex() {
        return oldIndex;
    }
    
    /**
     * Gets the new index.
     *
     * @return the new index
     */
    public int getNewIndex() {
        return newIndex;
    }
}