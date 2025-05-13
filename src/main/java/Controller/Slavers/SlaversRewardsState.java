package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.RewardType;
import Controller.State;
import Model.Player;
import Controller.GamePhases.FlightPhase;

public class SlaversRewardsState extends State{
    Context context;

    public SlaversRewardsState(Context context) {
        this.context = context;
    }

    @Override
    public void getReward(String playerName, RewardType rewardType) {
        if (rewardType != RewardType.CREDITS) {
            return; // Not a valid reward type
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player == controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }
        player.deltaCredits(context.getCredits());

        //togli i giorni di volo

        context.removePlayer(player);

        if(!context.getSpecialPlayers().isEmpty()){
            controller.setState(new SlaversCrewRemovalState(context));
        } else {
            controller.setState(new FlightPhase());
        }
    }

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
