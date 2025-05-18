package Controller.OpenSpace;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.State;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.GamePhases.FlightPhase;

/**
 * Represents the state where a player must remove batteries from their ship to confirm
 * the use of double engines during the open space phase.
 *
 * <p>This state is reached after a player declares the intent to use a number of double engines,
 * and must now pay the cost in batteries.</p>
 */
public class OpenSpaceBatteryRemovalState extends State {
    /**
     * The context in which this state operates, providing access to the game controller.
     */
    private Context context;
    /**
     * The number of batteries that must be removed to confirm the use of double engines.
     */
    private int declaredPower;

    /**
     * Constructs an OpenSpaceBatteryRemovalState with the specified context and declared power.
     *
     * @param context      The context providing access to the current game context.
     * @param declaredPower The number of batteries that must be removed.
     */
    public OpenSpaceBatteryRemovalState(Context context, int declaredPower) {
        this.context = context;
        this.declaredPower = declaredPower;
    }


    /**
     * Called when a player uses an item during this state.
     * Only battery usage is handled, and the coordinates must point to a valid battery compartment.
     *
     * <p>Each call removes one battery from the specified compartment. When all batteries
     * required by the declared power have been removed, the player is removed from the open space phase,
     * and the game either proceeds to the next player or returns to the flight phase.</p>
     *
     * <p>If the item is not a battery, the coordinates are null, the declared power is negative,
     * or the targeted component is not a valid battery compartment, the method should crate an error.</p>
     *
     * @param playerName  the name of the player using the item
     * @param itemType    the type of item being used (must be {@code ItemType.BATTERIES})
     * @param coordinates the coordinates of the battery compartment to remove a battery from
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        int placesGained = declaredPower*2;

        if(itemType != ItemType.BATTERIES){
            return;
        }

        if(coordinates == null){
            return; // Handle the case where coordinates are null
        }

        if(declaredPower < 0){
            return; // Handle the case where the declared power is negative
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        //TODO: placesGained += player.getShipBoard().getBaseEnginePower();
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            return;

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        if(declaredPower == 0){

            // TODO: Aggiornare la posizione del giocatore sommando placesGained

            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){         //passati tutti
                controller.setState(new FlightPhase(controller));
            }
            else{       //manca qualcuno da gestire
                controller.setState(new OpenSpaceEngineDeclarationState(context));
            }
        }
        else{       //rimuovi altra batteria
            controller.setState(new OpenSpaceBatteryRemovalState(context, declaredPower));
        }
    }

}
