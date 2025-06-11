package Controller.OpenSpace;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Player;

/**
 * Represents the state in which a player may declare the use of double engines
 * during the open space phase of the game.
 *
 * <p>This state allows a player (not first in turn order) to attempt using double engines,
 * which requires having a sufficient number of both batteries and double engine components.</p>
 */
public class OpenSpaceEngineDeclarationState extends State {

    /**
     * The context in which this state operates, providing access to the game controller.
     */
    private Context context;

    /**
     * Constructs an OpenSpaceEngineDeclarationState with the specified context.
     *
     * @param context The context providing access to the current game context.
     */
    public OpenSpaceEngineDeclarationState(Context context) {
        this.context = context;
    }

    /**
     * Called when a player declares the use of double components, such as engines.
     * Only double engines are handled in this state.
     *
     * <p>If the declared type is not {@code DoubleType.ENGINES}, or the player is first
     * in the turn order, or the player lacks sufficient batteries or double engines,
     * the method returns without taking action.</p>
     *
     * <p>If the declaration is valid, the game transitions to the
     * {@link OpenSpaceBatteryRemovalState} to proceed with the battery deduction.</p>
     *
     * @param playerName the name of the player making the declaration
     * @param doubleType the type of double component being declared (must be ENGINES)
     * @param amount     the number of double engines the player wants to use
     */
    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if(doubleType != DoubleType.ENGINES){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid double type, only ENGINES are allowed");
        }

        if(amount < 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid amount of double, only non negative integers are allowed");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn to throw the dice.");
        }

        //TODO: Se il giocatore ha potenza motrice base 0, lascia la partita

        if(player.getShipBoard().getCondensedShip().getTotalBatteries() < amount || player.getShipBoard().getCondensedShip().getEngines().getDoubleEngines() < amount) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough batteries or double engines to declare.");
        }

        controller.getModel().setState(new OpenSpaceBatteryRemovalState(context, amount));
        controller.getModel().setError(false);
    }


}
