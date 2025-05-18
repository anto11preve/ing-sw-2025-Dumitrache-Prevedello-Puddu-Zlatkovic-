package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
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
        if (doubleType != DoubleType.CANNONS) {
            return;
        }

        if (amount < 0) {
            return; // Handle the case where amount is negative
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }
        if(player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons()*2 +
                player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons() < amount){
            return; // Not enough cannons to declare
        }
        if(player.getShipBoard().getCondensedShip().getTotalBatteries() < amount){
            return; // Not enough batteries to declare
        }

        int basePower = 69; //da sistemare
        ///TODO: int basePower = player.getBasePower();

        if(context.getPower() > (basePower + amount)){
            if(context.getSpecialPlayers().contains(player)){
                return; // Player already declared power
            }
            context.addSpecialPlayer(player);
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){
                controller.setState(new SmugglersGoodsRemovalState(context));  //tutti i giocatori gestiti
            }else{
                controller.setState(new SmugglersPowerDeclarationState(context)); //manca qualcuno da gestire
            }
        }else{
            controller.setState(new SmugglersBatteryRemovalState(context, amount, basePower)); //rimuovi batteria
        }
    }
}
