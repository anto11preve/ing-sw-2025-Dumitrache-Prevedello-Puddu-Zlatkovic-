package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

/**
 * This state manages the penalty phase for players who failed to match the smugglers' power
 * and also lack sufficient goods to discard. These players must now discard a number of batteries instead.
 */
public class SecondSmugglersBatteryRemovalState extends State{
    /**
     * Context object that holds the game state and player information.
     */
    private Context context;
    /**
     * The amount of batteries to be removed.
     */
    private int amount;

    /**
     * Constructor to initialize the state with the current game context and a specific amount.
     *
     * @param context the shared game context
     * @param amount  the number of batteries yet to be removed
     */
    public SecondSmugglersBatteryRemovalState(Context context, int amount) {
        this.context = context;
        this.amount = amount;
    }

    /**
     * Allows the player to remove a battery from a valid battery compartment as part of the penalty if they have no more goods.
     *
     * @param playerName the name of the player using the item
     * @param itemType the type of item to be used (must be BATTERIES)
     * @param coordinates the coordinates of the battery compartment
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        if(itemType != ItemType.BATTERIES){
            return;
        }
        if(coordinates == null){
            return; // Handle the case where coordinates are null
        }
        if(amount <= 0){
            return; // Handle the case where no batteries are to remove
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
        if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            return;
        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        amount--;
        if(amount == 0){
            context.removeSpecialPlayer(player);
            if(context.getSpecialPlayers().isEmpty()){
                controller.setState(new FlightPhase());
            } else {
                controller.setState(new SmugglersGoodsRemovalState(context));
            }
        } else {
            controller.setState(new SecondSmugglersBatteryRemovalState(context, amount));
        }
    }
}
