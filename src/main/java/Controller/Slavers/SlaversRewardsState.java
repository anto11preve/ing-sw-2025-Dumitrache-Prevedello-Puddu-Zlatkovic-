package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.RewardType;
import Controller.State;
import Model.Player;
import Controller.GamePhases.FlightPhase;

/**
 * This state manages the reward phase during the "Slavers" event, allowing players
 * who successfully surpassed the slavers' power to claim their reward (Credits),
 * or to skip it entirely.
 */
public class SlaversRewardsState extends State{
    /**
     * Context object that holds the game state and player information.
     */
    Context context;

    /**
     * Constructor to initialize the state with the current game context.
     *
     * @param context the shared game context
     */
    public SlaversRewardsState(Context context) {
        this.context = context;
    }

    /**
     * Allows the current player to claim their reward if it's of type {@code CREDITS}.
     * Upon claiming, the player's credits are increased by the amount specified in the context.
     * The player is then removed from the list of players yet to act, and the game proceeds:
     * <ul>
     *     <li>If special players remain, transition to {@link SlaversCrewRemovalState}</li>
     *     <li>Otherwise, the game continues to the {@link FlightPhase}</li>
     * </ul>
     *
     * @param playerName  the name of the player claiming the reward
     * @param rewardType  the type of reward to be claimed (must be {@code CREDITS})
     */
    @Override
    public void getReward(String playerName, RewardType rewardType) {
        if (rewardType != RewardType.CREDITS) {
            return; // Not a valid reward type
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }

        player.deltaCredits(context.getCredits());

        ///TODO: togli i giorni di volo

        context.removePlayer(player);

        if(!context.getSpecialPlayers().isEmpty()){
            controller.setState(new SlaversCrewRemovalState(context));
        } else {
            controller.setState(new FlightPhase());
        }
    }

    /**
     * Allows the current player to skip their reward.
     * The player is simply removed from the remaining players list.
     * The game proceeds similarly to {@link #getReward}, based on whether any special players remain.
     *
     * @param playerName the name of the player skipping the reward
     */
    @Override
    public void skipReward(String playerName) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player == controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }
        context.removePlayer(player);

        if(!context.getSpecialPlayers().isEmpty()){
            controller.setState(new SlaversCrewRemovalState(context));
        } else {
            controller.setState(new FlightPhase());
        }
    }
}
