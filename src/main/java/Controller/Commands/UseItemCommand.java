package Controller.Commands;

import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Exceptions.InvalidMethodParameters;
import Model.Ship.Coordinates;

/**
 * Command for using items like batteries or crew during adventure resolution.
 * Handles activation of shields, double cannons, and crew sacrifices.
 */
public class UseItemCommand extends Command {
    /** The type of item being used */
    private final ItemType itemType;
    /** The coordinates where the item is used */
    private final Coordinates coordinates;
    /** The amount of items to use */
    private final int amount;
    
    /**
     * Constructs a new UseItemCommand.
     *
     * @param playerName the name of the player using the item
     * @param gameID the ID of the game session
     * @param itemType the type of item to use
     * @param coordinates the coordinates where to use the item
     * @param amount the amount of items to use
     */
    public UseItemCommand(String playerName, int gameID, ItemType itemType, 
                         Coordinates coordinates, int amount) {
        super(playerName, gameID);
        this.itemType = itemType;
        this.coordinates = coordinates;
        this.amount = amount;
    }
    
    /**
     * Executes the use item command by calling the controller's useItem method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.useItem(getPlayerName(), itemType, coordinates);
        } catch (InvalidCommand | InvalidParameters | InvalidMethodParameters e) {
            System.err.println("Failed to use item: " + e.getMessage());
        }
    }
    
    /**
     * Gets the item type.
     *
     * @return the item type
     */
    public ItemType getItemType() {
        return itemType;
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
     * Gets the amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }
}