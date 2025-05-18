package Controller.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Slavers.SlaversBatteryRemovalState;
import Controller.Slavers.SlaversCrewRemovalState;
import Controller.Slavers.SlaversPowerDeclarationState;
import Model.Player;
import Controller.State;

/**
 * Represents the state where a player declares how much cannon power
 * (converted from batteries) they want to use to face the pirates.
 *
 * <p>This state is part of the pirates encounter during the flight phase of the game.
 * Players must declare an amount of power to try to match or exceed the pirates' strength.
 * The player's ship must have enough batteries and cannons to support the declared amount.</p>
 */
public class PiratesPowerDeclarationState extends State {
    /**
     * The context in which this state operates, providing access to the game controller.
     */
    Context context;

    /**
     * Constructs a PiratesPowerDeclarationState with the specified context.
     *
     * @param context The context providing access to the current game context.
     */
    public PiratesPowerDeclarationState(Context context) {
        this.context = context;
    }

    /**
     * Handles the declaration of power from a player in the form of cannon plus double cannons.
     *
     * <p>If the player has enough cannons and batteries, and the declared amount plus their base power
     * is enough to beat the pirates, they will enter the {@link PiratesBatteryRemovalState}
     * to use batteries, otherwise they get marked and move on to the next player. If their base power plus
     * declared amount exceeds the pirates' strength,they are marked as safe. Once all players have declared,
     * the game transitions to the next phase.</p>
     *
     * @param playerName the name of the player making the declaration
     * @param doubleType the type of double being declared (must be {@code DoubleType.CANNONS})
     * @param amount     the amount of power the player is declaring
     */
    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) {
        if (doubleType != DoubleType.CANNONS) {
            return;
        }

        if (amount < 0) {
            return; // Invalid amount
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

        int basePower = 69; //da correggere
        ///TODO: basePower = player.getShipBoard().getBasePower();

        if(context.getPower() > (basePower + amount)){
            if(context.getSpecialPlayers().contains(player)){
                return; // Player already declared power
            }
            context.addSpecialPlayer(player);
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){
                controller.setState(new PiratesCannonShotsState(context));  //tutti i giocatori gestiti
            }else{
                controller.setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
            }
        }else{
            controller.setState(new PiratesBatteryRemovalState(context, amount, basePower)); //rimuovi batteria
        }
    }
}
