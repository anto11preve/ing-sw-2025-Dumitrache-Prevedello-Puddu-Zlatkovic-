package Controller.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.Slavers.SlaversBatteryRemovalState;
import Controller.Slavers.SlaversCrewRemovalState;
import Controller.Slavers.SlaversPowerDeclarationState;
import Model.Enums.Direction;
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
    public void declaresDouble(String playerName, DoubleType doubleType, double amount) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if (doubleType != DoubleType.CANNONS) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid double type, expected CANNONS");
        }

        if (amount < 0) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Negative amount");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not the player's turn");
        }


        int batteries = 0;
        double minPower = player.getShipBoard().getCondensedShip().getBasePower();
        double maxPower = player.getShipBoard().getCondensedShip().getMaxPower();

        if (amount < minPower || amount > maxPower) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared amount is out of bounds");
        }


        if ((amount % 1) != (minPower % 1)) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared amount must match the ship's base power decimal part");
        }

        int delta = (int) (amount - minPower);

        int frontCannons = player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons();
        int otherCannons = player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons();

        int doubleRequired = delta / 2;
        if (doubleRequired <= frontCannons) {
            batteries += doubleRequired;
            delta -= doubleRequired * 2;
        } else {
            batteries += frontCannons;
            delta -= doubleRequired * 2;
        }

        if (delta > 0) {

            if (delta <= otherCannons) {
                batteries += delta;
            } else {
                controller.getModel().setError(true);
                throw new InvalidParameters("Not enough double cannons to declare this amount");
            }

        }

        if(batteries > player.getShipBoard().getCondensedShip().getTotalBatteries()){
            controller.getModel().setError(true);
            throw new InvalidParameters("Not enough batteries to declare this amount");
        }


        if (context.getPower() > (amount)) {    //se perdi
            if (context.getSpecialPlayers().contains(player)) {
                controller.getModel().setError(true);
                throw new InvalidContextualAction("Player is already marked");
            }
            context.addSpecialPlayer(player);
            context.removePlayer(player);
            if (context.getPlayers().isEmpty()) {
                controller.getModel().setState(new PiratesCannonShotsState(context));  //tutti i giocatori gestiti
                controller.getModel().setError(false);
            } else {
                controller.getModel().setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
                controller.getModel().setError(false);
            }
        } else {    //se non perdi
            controller.getModel().setState(new PiratesBatteryRemovalState(context, amount, batteries)); //rimuovi batteria
            controller.getModel().setError(false);
        }
    }
}
