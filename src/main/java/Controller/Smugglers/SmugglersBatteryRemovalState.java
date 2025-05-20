package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.RewardType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Slavers.SlaversBatteryRemovalState;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;

/**
 * This state handles the removal of batteries from a player's ship during the
 * "Smugglers" event. After declaring a cannon power, players must now use a corresponding
 * number of batteries to actually activate that power.
 */
public class SmugglersBatteryRemovalState extends State{
    /**
     * Context object that holds the game state and players information.
     */
    private Context context;
    /**
     * The declared power of the player.
     */
    private int declaredPower;
    /**
     * The actual power of the player.
     */
    private int actualPower;

    /**
     * Constructor to initialize the state with the current game context and power values.
     *
     * @param context       the shared game context
     * @param declaredPower the declared power of the player
     * @param actualPower   the actual power of the player
     */
    public SmugglersBatteryRemovalState(Context context, int declaredPower, int actualPower) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.actualPower = actualPower;
    }

    /**
     * Handles the use of a battery component by the current player. If the battery
     * is valid and the player is allowed to act, one battery is removed, increasing
     * the actual power.
     *
     * <p>Once all declared batteries are removed, the state transitions depending
     * on whether the total power exceeds, equals, or is less than the smugglers' power.</p>
     *
     * @param playerName the name of the player using the battery
     * @param itemType must be {@link ItemType#BATTERIES}
     * @param coordinates the coordinates of the battery compartment
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        if(itemType != ItemType.BATTERIES){
            throw new IllegalArgumentException("Item type must be BATTERIES");
        }

        if(coordinates == null){
            throw new IllegalArgumentException("Invalid coordinates");
        }

        if(declaredPower <0){
            throw new IllegalArgumentException("Invalid declared power");
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            throw new IllegalArgumentException("It's not your turn");

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            throw new InvalidContextualAction("Not a valid battery compartment");

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        actualPower++;
        if(declaredPower == 0){
            if(actualPower > context.getPower()){
                controller.setState(new SmugglersLandState(context));
            } else if(actualPower == context.getPower()){
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new SmugglersGoodsRemovalState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SmugglersPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }
            else{
                context.removePlayer(player);
                context.addSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new SmugglersGoodsRemovalState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SmugglersPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }

        }
        else{       //rimuovi altra batteria
            controller.setState(new SlaversBatteryRemovalState(context, declaredPower, actualPower));
        }
    }

    /**
     * Allows a player to claim a reward directly if their base power alone exceeds
     * the smugglers' threshold.
     *
     * @param playerName the name of the player
     * @param rewardType must be {@link RewardType#CREDITS}
     */
    @Override
    public void getReward(String playerName, RewardType rewardType){
        if(rewardType != RewardType.CREDITS){
            throw new IllegalArgumentException("Reward type must be CREDITS");
        }

        if(declaredPower < 0){
            throw new IllegalArgumentException("Invalid declared power");
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            throw new IllegalArgumentException("It's not your turn");
        if(actualPower > context.getPower() && declaredPower == 0){
            controller.setState(new SmugglersLandState(context));
        }

    }
}
