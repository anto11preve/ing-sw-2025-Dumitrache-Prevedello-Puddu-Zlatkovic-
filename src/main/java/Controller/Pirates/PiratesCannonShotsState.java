package Controller.Pirates;

import Controller.Controller;
import Controller.State;
import Model.Player;
import Controller.Context;

import java.util.Random;

/**
 * Represents the state where cannon shots from pirates are resolved.
 *
 * <p>In this phase, the active player rolls two dice to determine the target column or row
 * for the pirate cannon shot. The result is used to affect all players' ships.</p>
 *
 * <p>After rolling the dice, the game transitions to the {@link PiratesManageShotState}
 * to handle the damage resolution for each player.</p>
 */
public class PiratesCannonShotsState extends State{
    /**
     * The context of the game, which contains information about the current state and players.
     */
    private Context context;

    /**
     * Constructs a new PiratesCannonShotsState.
     *
     * @param context The context of the game.
     */
    public PiratesCannonShotsState(Context context) {
        this.context = context;
    }

    /**
     * Rolls two dice to determine the column of impact for the pirate cannon shot.
     *
     * <p>Only the first player in the turn order is allowed to perform the roll.
     * If projectiles are present, the total of two dice rolls is used as the
     * impacted column or row.
     * The game then transitions to the {@link PiratesManageShotState} to resolve the effects.</p>
     *
     * @param playerName the name of the player rolling the dice
     */
    @Override
    public void throwDices(String playerName) {
        if(context.getProjectiles().isEmpty()) {
            return; // Handle the case where there are no projectiles
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }
        Random rand = new Random();
        int dado1 = controller.getModel().rollDice(); // numero tra 1 e 6
        int dado2 = controller.getModel().rollDice(); // numero tra 1 e 6
        int number = dado1 + dado2;

        controller.setState(new PiratesManageShotState(context, number, 0));

    }
}
