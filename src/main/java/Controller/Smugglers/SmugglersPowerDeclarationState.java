package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Model.Enums.Direction;
import Model.Player;
import Controller.State;

/**
 * This state handles the declaration of cannon power by players during the
 * "Smugglers" event. Players must declare how many cannons (and corresponding batteries)
 * they intend to use to surpass the smugglers' power threshold.
 */
public class SmugglersPowerDeclarationState extends State {
    /**
     * Context object that holds the game state and player information.
     */
    Context context;

    /**
     * Constructor to initialize the state with the current game context.
     *
     * @param context the shared game context
     */
    public SmugglersPowerDeclarationState(Context context) {
        this.context = context;
    }

    /**
     * Allows the current player to declare how many cannons they will use against the smugglers.
     * The declaration is only valid if the player declares {@link DoubleType#CANNONS}, has enough
     * cannon power and batteries, and hasn't already made a declaration.
     *
     * <p>If the total declared power (base + amount) is not enough to beat the smugglers' power,
     * the player is moved to {@link SmugglersBatteryRemovalState} to use batteries.</p>
     * <p>If the power is insufficient and the player is not already in the special players list,
     * the player is added to the losers. Once all players are handled, the state transitions to
     * {@link SmugglersGoodsRemovalState}.</p>
     *
     * @param playerName the name of the player making the declaration
     * @param doubleType the type of resource being declared (must be {@code CANNONS})
     * @param amount the number of cannons the player wants to use
     */
    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) {
        Controller controller = context.getController();
        if (doubleType != DoubleType.CANNONS) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid double type, expected CANNONS");
        }

        if (amount < 0) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid amount, must be non-negative");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not your turn to declare power");
        }
        if(player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons()*2 +
                player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons() < amount){
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough cannons to declare");
        }
        if(player.getShipBoard().getCondensedShip().getTotalBatteries() < amount){
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough batteries to declare");
        }

        int basePower = 0;
        basePower += player.getShipBoard().calculateFirepower(Direction.UP);
        basePower += player.getShipBoard().calculateFirepower(Direction.DOWN);
        basePower += player.getShipBoard().calculateFirepower(Direction.LEFT);
        basePower += player.getShipBoard().calculateFirepower(Direction.RIGHT);

        if(context.getPower() > (basePower + amount)){
            if(context.getSpecialPlayers().contains(player)){
                controller.getModel().setError(true);
                throw new InvalidContextualAction("Player already declared power");
            }
            context.addSpecialPlayer(player);
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){
                controller.getModel().setState(new SmugglersGoodsRemovalState(context));  //tutti i giocatori gestiti
                controller.getModel().setError(false);
            }else{
                controller.getModel().setState(new SmugglersPowerDeclarationState(context)); //manca qualcuno da gestire
                controller.getModel().setError(false);
            }
        }else{
            controller.getModel().setState(new SmugglersBatteryRemovalState(context, amount, basePower)); //rimuovi batteria
            controller.getModel().setError(false);
        }
    }
}
