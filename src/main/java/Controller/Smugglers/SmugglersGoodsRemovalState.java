package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Ship.GoodCounter;
import Controller.GamePhases.FlightPhase;
import Controller.State;

/**
 * This state manages the forced removal of goods from a player's ship during
 * the "Smugglers" event, if they failed to surpass the smugglers' power threshold.
 *
 * <p>Players must discard a number of goods equal to the required amount. If they lack
 * sufficient goods but have batteries, a secondary penalty is applied. Otherwise,
 * they suffer no additional effect.</p>
 */
public class SmugglersGoodsRemovalState extends State{
    /**
     * Context object that holds the game state and player information.
     */
    private Context context;
    /**
     * The good counter that tracks the number of goods to be removed.
     */
    private GoodCounter goodCounter;
    /**
     * The amount of goods to be removed.
     */
    private int amount;

    /**
     * Constructor to initialize the state with the current game context.
     *
     * @param context the shared game context
     */
    public SmugglersGoodsRemovalState(Context context) {
        this.context = context;
        this.amount = context.getRequiredGoods();
    }

    /**
     * Constructor to initialize the state with the current game context and a specific amount.
     *
     * @param context the shared game context
     * @param amount  the number of goods yet to be removed
     */
    public SmugglersGoodsRemovalState(Context context, int amount) {
        this.context = context;
        this.amount = amount;
    }



    /**
     * Handles the removal of a good from the player's cargo hold. If the player lacks
     * sufficient goods, but has batteries, they will instead face battery removal.
     * Otherwise, they are skipped with no penalty.
     *
     * <p>After a good is removed, the state updates based on whether the required
     * number has been discarded.</p>
     *
     * @param name the name of the player discarding the good
     * @param oldCoordinates the coordinates of the cargo hold from which the good is removed
     * @param newCoordinates unused in this context
     * @param oldIndex the index of the good in the cargo hold
     * @param newIndex unused in this context
     */
    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        if(oldCoordinates == null){
            return; // Handle the case where coordinates are null
        }

        if(oldIndex < 0){
            return; // Handle the case where the index is invalid
        }

        GoodCounter goodCounter = player.getShipBoard().getCondensedShip().goodToDiscard(amount);
        if(goodCounter.getRed() + goodCounter.getBlue() + goodCounter.getGreen() + goodCounter.getYellow() == 0){       //non ha abbastanza goods da scartare
            if(player.getShipBoard().getCondensedShip().getTotalBatteries() > 0){   //se almeno ha delle batterie
                controller.setState(new SecondSmugglersBatteryRemovalState(context, amount));
            } else {    //se no non gli succede niente
                context.removeSpecialPlayer(player);
                if(context.getSpecialPlayers().isEmpty()){
                    controller.setState(new FlightPhase(controller));
                } else {
                    controller.setState(new SmugglersGoodsRemovalState(context));
                }
            }
            return;
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(oldCoordinates);

        if(component == null || !player.getShipBoard().getCondensedShip().getCargoHolds().contains(component)) {
            return; // Handle the case where the components are not cargo holds
        }
        CargoHold cargoHold = (CargoHold) component;
        Good selectedGood = cargoHold.getGoods()[oldIndex];
        if(selectedGood == null) {
            return; // Handle the case where the good is not found
        }
        boolean done = goodCounter.removeGood(selectedGood);
        if(!done) {
            return; // Need to remove another type of good
        }
        cargoHold.removeGood(oldIndex);
        amount--;
        if(amount == 0){
            context.removeSpecialPlayer(player);
            if(context.getSpecialPlayers().isEmpty()){
                controller.setState(new FlightPhase(controller));
            } else {
                controller.setState(new SmugglersGoodsRemovalState(context));
            }
        } else {
                controller.setState(new SmugglersGoodsRemovalState(context, amount));
        }
    }
}
