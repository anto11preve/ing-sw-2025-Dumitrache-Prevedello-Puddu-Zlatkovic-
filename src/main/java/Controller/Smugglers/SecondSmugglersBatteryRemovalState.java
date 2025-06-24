package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

import java.util.List;

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
        this.setPlayerInTurn(context.getSpecialPlayers().getFirst());
    }

    /**
     * Allows the player to remove a battery from a valid battery compartment as part of the penalty if they have no more goods.
     *
     * @param playerName the name of the player using the item
     * @param itemType the type of item to be used (must be BATTERIES)
     * @param coordinates the coordinates of the battery compartment
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.BATTERIES){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid item type, expected BATTERIES");
        }
        if(coordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid coordinates");
        }
        if(amount <= 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid amount, must be non negative");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getSpecialPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn");
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component)) {   //non è un Battery
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not a valid battery compartment");
        }

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        amount--;
        if(amount == 0){
            context.removeSpecialPlayer(player);
            if(context.getSpecialPlayers().isEmpty()){
                controller.getModel().setState(new FlightPhase(controller));
                controller.getModel().setError(false);
            } else {
                controller.getModel().setState(new SmugglersGoodsRemovalState(context));
                controller.getModel().setError(false);
            }
        } else {
            controller.getModel().setState(new SecondSmugglersBatteryRemovalState(context, amount));
            controller.getModel().setError(false);
        }
    }
    @Override
    public List<String> getAvailableCommands(){
        return List.of(
            "UseItem"
        );
    }
}
