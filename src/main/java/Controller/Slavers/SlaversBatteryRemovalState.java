package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.RewardType;
import Controller.State;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

/**
 * This state handles the battery removal process for a player attempting to boost their power
 * during the "Slavers" encounter. Players use batteries to reach or exceed the slavers' power level.
 * If the player succeeds, they receive rewards. If not, they may face crew penalties (if they don't at least match the enemy's power).
 */
public class SlaversBatteryRemovalState extends State{
    /**
     * Context object that holds the game state and player information.
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
    public SlaversBatteryRemovalState(Context context, int declaredPower, int actualPower) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.actualPower = actualPower;
    }

    /**
     * Allows the player to use a battery from a specified {@link BatteryCompartment} on their ship.
     * If the component is valid and contains a battery, it removes one unit, decreases the remaining
     * power to declare, and increases the total declared power.
     *
     * Once all declared batteries are used:
     * - If the total power exceeds the slavers' threshold, move to {@link SlaversRewardsState}.
     * - If it matches the threshold, the player is removed and the next player proceeds.
     * - If it's still lower, the player fails and may face consequences in {@link SlaversCrewRemovalState}.
     *
     * @param playerName  the name of the current player
     * @param itemType    the type of item being used (must be {@code BATTERIES})
     * @param coordinates the coordinates of the component to activate
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        if(itemType != ItemType.BATTERIES){
            throw new IllegalArgumentException("Item type must be BATTERIES");
        }

        if(declaredPower < 0){
            throw new IllegalArgumentException("Declared power cannot be negative");
        }

        if(coordinates == null){
            throw new IllegalArgumentException("Coordinates are null");
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            throw new IllegalArgumentException("It's not your turn");

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            throw new IllegalArgumentException("Component is not a battery compartment");

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        actualPower++;
        if(declaredPower == 0){
            if(actualPower > context.getPower()){
                controller.setState(new SlaversRewardsState(context));
            } else if(actualPower == context.getPower()){
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new SlaversCrewRemovalState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SlaversPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }
            else{
                context.removePlayer(player);
                context.addSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new SlaversCrewRemovalState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SlaversPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }

        }
        else{       //rimuovi altra batteria
            controller.setState(new SlaversBatteryRemovalState(context, declaredPower, actualPower));
        }
    }

    /**
     * Allows the player to directly claim a reward (credits only), typically when their
     * base power alone exceeds the slavers' strength without needing batteries.
     *
     * If the power condition is satisfied and no batteries were required, transition to
     * {@link SlaversRewardsState}.
     *
     * @param playerName the name of the player
     * @param rewardType the type of reward requested (must be {@code CREDITS})
     */
    @Override
    public void getReward(String playerName, RewardType rewardType){
        if(rewardType != RewardType.CREDITS){
            throw new IllegalArgumentException("Reward type must be CREDITS");
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            throw new IllegalArgumentException("It's not your turn");

        if(actualPower > context.getPower() && declaredPower == 0){
            controller.setState(new SlaversRewardsState(context));
        }

    }
}
