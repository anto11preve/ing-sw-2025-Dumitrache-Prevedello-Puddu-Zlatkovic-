package Controller.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.RewardType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
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
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.BATTERIES){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid item type, expected BATTERIES");
        }

        if(declaredPower < 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared power cannot be negative");
        }

        if(coordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Coordinates cannot be null");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not the player's turn");
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component)) {   //non è un Battery
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Invalid component type, expected BatteryCompartment");
        }

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        actualPower++;
        if(declaredPower == 0){
            if(actualPower > context.getPower()){
                controller.getModel().setState(new PiratesRewardState(context));
                controller.getModel().setError(false);
            } else if(actualPower == context.getPower()){
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.getModel().setState(new PiratesCannonShotsState(context)); //tutti i giocatori gestiti
                    controller.getModel().setError(false);
                }
                else{       //manca qualcuno da gestire
                    controller.getModel().setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
                    controller.getModel().setError(false);
                }
            }
            else{
                context.removePlayer(player);
                context.addSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.getModel().setState(new PiratesCannonShotsState(context)); //tutti i giocatori gestiti
                    controller.getModel().setError(false);
                }
                else{       //manca qualcuno da gestire
                    controller.getModel().setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
                    controller.getModel().setError(false);
                }
            }

        }
        else{       //rimuovi altra batteria
            controller.getModel().setState(new SlaversBatteryRemovalState(context, declaredPower, actualPower));
            controller.getModel().setError(false);
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
        Controller controller = context.getController();
        if(rewardType != RewardType.CREDITS){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid reward type, expected CREDITS");
        }
        if(declaredPower < 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared power cannot be negative");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not the player's turn");
        }

        if(actualPower > context.getPower() && declaredPower == 0){
            controller.getModel().setState(new PiratesRewardState(context));
            controller.getModel().setError(false);
        }

    }
}
