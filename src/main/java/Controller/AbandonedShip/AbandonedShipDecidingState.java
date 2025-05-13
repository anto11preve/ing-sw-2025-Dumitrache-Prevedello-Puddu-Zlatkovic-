package Controller.AbandonedShip;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.RewardType;
import Controller.State;
import Model.Player;
import Controller.GamePhases.FlightPhase;


public class AbandonedShipDecidingState extends State {
    private Context context;

    public AbandonedShipDecidingState(Context context) {
        this.context = context;
    }


    @Override
    public void skipReward(String playerName){
        Controller controller = context.getController();
        Player currentPlayer = controller.getModel().getPlayer(playerName);
        if(currentPlayer == controller.getModel().getFlightBoard().getTurnOrder()[0]){  //se è il suo turno
            context.removePlayer(currentPlayer);
            if(context.getPlayers().isEmpty()){         //se skippano tutti....
                controller.setState(new FlightPhase());
            }
            else{
                controller.setState(new AbandonedShipDecidingState(context));
            }

        }

    }

    //caso AbandonedShip
    @Override
    public void getReward(String playerName, RewardType rewardType) {

        //aggiungere controllo rewardtype

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        player.deltaCredits(context.getCredits());
        //parentState.getController().getModel().getFlightBoard() --> perdi giorni di volo
        controller.setState(new AbandonedShipCrewRemovalState(context));
    }
}
