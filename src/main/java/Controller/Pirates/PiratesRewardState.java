package Controller.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.RewardType;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Controller.State;
import Controller.GamePhases.FlightPhase;

/**
 * Handles the reward phase after the pirate attack.
 *
 * <p>In this state, players who won against the pirate attack
 * have the opportunity to claim a reward or skip it.
 * After they have responded, the game proceeds either to the
 * cannon shot phase (if someone else before lost) or returns to the main flight phase.</p>
 */
public class PiratesRewardState extends State {
    /**
     * The context of the game, which contains information about the current state and players.
     */
    Context context;

    /**
     * Constructs a new PiratesRewardState.
     *
     * @param context The context of the game.
     */
    public PiratesRewardState(Context context) {
        this.context = context;
    }

    /**
     * Grants a reward to the player if eligible.
     *
     * <p>Only players who are not first in the turn order can receive rewards.
     * The only available reward type is {@code CREDITS}. After receiving the reward,
     * the player is removed from the player list. Once all the players before are handled,
     * the game transitions accordingly.</p>
     *
     * @param playerName the name of the player claiming the reward
     * @param rewardType the type of reward the player wants (only {@code CREDITS} is valid)
     */
    @Override
    public void getReward(String playerName, RewardType rewardType) throws InvalidMethodParameters {
        Controller controller = context.getController();
        if (rewardType != RewardType.CREDITS) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid reward type, expected CREDITS");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        player.deltaCredits(context.getCredits());

        controller.getModel().getFlightBoard().deltaFlightDays(player, context.getDaysLost());

        context.removePlayer(player);

        if(!context.getSpecialPlayers().isEmpty()){
            controller.getModel().setState(new PiratesCannonShotsState(context));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new FlightPhase(controller));
            controller.getModel().setError(false);
        }
    }

    /**
     * Allows the player to skip the reward.
     *
     * <p>The player is removed from the reward list without receiving any reward.
     * Then the game transitions to the next appropriate state.</p>
     *
     * @param playerName the name of the player skipping the reward
     */
    @Override
    public void skipReward(String playerName) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        context.removePlayer(player);

        if(!context.getSpecialPlayers().isEmpty()){
            controller.getModel().setState(new PiratesCannonShotsState(context));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new FlightPhase(controller));
            controller.getModel().setError(false);
        }
    }
}
