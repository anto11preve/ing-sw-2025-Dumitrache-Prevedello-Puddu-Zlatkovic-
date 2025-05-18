package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.State;
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
                controller.setState(new SlaversCrewRemovalState(context));  //tutti i giocatori gestiti
            }else{
                controller.setState(new SlaversPowerDeclarationState(context)); //manca qualcuno da gestire
            }
        }else{
            controller.setState(new SlaversBatteryRemovalState(context, amount, basePower)); //usa batterie
        }
    }
}
