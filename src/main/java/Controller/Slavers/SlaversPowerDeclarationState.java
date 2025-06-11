package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Enums.Direction;
import Model.Player;

/**
 * This state handles the declaration of cannon power by players during the "Slavers" encounter.
 * Players may attempt to use double cannons and batteries to surpass the slavers' power threshold.
 * If successful, they avoid penalties or get rewards; otherwise, they proceed to a crew removal.
 */
public class SlaversPowerDeclarationState extends State {
    /**
     * Context object that holds the game state and player information.
     */
    Context context;

    /**
     * Constructor to initialize the state with the current game context.
     *
     * @param context the shared game context
     */
    public SlaversPowerDeclarationState(Context context) {
        this.context = context;
    }

    /**
     * Allows a player to declare the number of double cannons they wish to use.
     * This declaration is only valid if:
     * - The declaration type is {@code DoubleType.CANNONS}
     * - The player has enough double cannons and batteries
     * - The player is the current player in the turn order
     *
     * If the player's total power (base + declared cannons) exceeds the slavers' power,
     * they are added to the list of special players (effectively marked as handled),
     * and the state either loops or transitions to {@link SlaversCrewRemovalState}.
     *
     * Otherwise, the player proceeds to {@link SlaversBatteryRemovalState} to spend the declared batteries.
     *
     * @param playerName the name of the player declaring power
     * @param doubleType the type of declaration (should be CANNONS)
     * @param amount     the number of double cannons declared
     */
    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if (doubleType != DoubleType.CANNONS) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid double type, expected CANNONS");
        }

        if (amount < 0) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid amount, must be non-negative");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn to declare power");
        }

        if(player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons()*2 +
                player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons() < amount){
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough double cannons to declare");
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
                throw new InvalidParameters("Player already declared power");
            }
            context.addSpecialPlayer(player);
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){
                controller.getModel().setState(new SlaversCrewRemovalState(context));  //tutti i giocatori gestiti
                controller.getModel().setError(false);
            }else{
                controller.getModel().setState(new SlaversPowerDeclarationState(context)); //manca qualcuno da gestire
                controller.getModel().setError(false);
            }
        }else{
            controller.getModel().setState(new SlaversBatteryRemovalState(context, amount, basePower)); //usa batterie
            controller.getModel().setError(false);
        }
    }
}
