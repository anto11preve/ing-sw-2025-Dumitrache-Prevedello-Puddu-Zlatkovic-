package Controller.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.RewardType;
import Controller.Slavers.SlaversBatteryRemovalState;
import Controller.Slavers.SlaversCrewRemovalState;
import Controller.Slavers.SlaversPowerDeclarationState;
import Controller.Slavers.SlaversRewardsState;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;

/**
 * Represents the state where a player removes batteries to power up weapons
 * during the pirates encounter phase.
 *
 * <p>This state occurs after a player declares an intent to use a certain amount of power
 * against pirates. The player must now remove batteries to match or exceed the pirates' power.</p>
 */
public class PiratesBatteryRemovalState extends State{
    /**
     * The context of the game, which contains information about the current state and players.
     */
    private Context context;
    /**
     * The declared power of the player, which is the amount of power they intend to use.
     */
    private int declaredPower;
    /**
     * The actual power of the player, which is the amount of power they have used.
     */
    private int actualPower;

    /**
     * Constructs a new PiratesBatteryRemovalState.
     *
     * @param context The context of the game.
     * @param declaredPower The declared power of the player.
     * @param actualPower The actual power of the player.
     */
    public PiratesBatteryRemovalState(Context context, int declaredPower, int actualPower) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.actualPower = actualPower;
    }

    /**
     * Called when a player attempts to use an item. This state handles the use of batteries only.
     * One battery is removed from the specified coordinates and added to the player's power.
     *
     * <p>After all declared batteries have been removed, the player's total power is compared to
     * the pirates' power. Depending on the result, the player may earn a reward, be safely removed
     * from the context, or be added to the list of players to be hit by pirates.</p>
     *
     * @param playerName  the name of the player using the item
     * @param itemType    the type of item being used (must be {@code ItemType.BATTERIES})
     * @param coordinates the coordinates of the battery compartment
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        if(itemType != ItemType.BATTERIES){
            return;
        }

        if(declaredPower < 0){
            return; // Handle the case where the declared power is negative
        }

        if(coordinates == null){
            return; // Handle the case where the coordinates are null
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            return;

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        actualPower++;
        if(declaredPower == 0){
            if(actualPower > context.getPower()){
                controller.setState(new PiratesRewardState(context));
            } else if(actualPower == context.getPower()){
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new PiratesCannonShotsState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }
            else{
                context.removePlayer(player);
                context.addSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new PiratesCannonShotsState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }

        }
        else{       //rimuovi altra batteria
            controller.setState(new SlaversBatteryRemovalState(context, declaredPower, actualPower));
        }
    }

    /**
     * Called when a player attempts to claim a reward directly using only base power (without additional batteries).
     *
     * @param playerName  the name of the player claiming the reward
     * @param rewardType  the type of reward being claimed (must be {@code RewardType.CREDITS})
     */
    @Override
    public void getReward(String playerName, RewardType rewardType){
        if(rewardType != RewardType.CREDITS){
            return;
        }
        if(declaredPower < 0){
            return; // Handle the case where the declared power is negative
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        if(actualPower > context.getPower() && declaredPower == 0){
            controller.setState(new PiratesRewardState(context));
        }

    }
}
