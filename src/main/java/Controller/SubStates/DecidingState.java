package Controller.SubStates;

import Controller.AbandonedShip.AbandonedShipState;
import Controller.Controller;
import Controller.State;
import Model.Player;

import java.util.List;

public class DecidingState extends SubState {


    @Override
    public void skipReward(Controller controller, AbandonedShipState macroState, String playerName){
        Player currentPlayer = controller.getModel().getPlayer(playerName);
        if(currentPlayer == controller.getModel().getFlightBoard().getTurnOrder()[0]){  //se è il suo turno
            List<Player> players = macroState.getPlayers();
            players.remove(currentPlayer);
            macroState.setSubState(new DecidingState());
        }
    //se skippano tutti....
    }

    @Override
    public void getReward(Controller controller, AbandonedShipState macroState, String playerName) {
        parentState = macroState;
        Player player = controller.getModel().getPlayer(playerName);
        player.deltaCredits(macroState.getCard().getLandingReward().getAmount());
        //parentState.getController().getModel().getFlightBoard() --> perdi giorni di volo
        parentState.setSubState(new CrewRemovalState());
    }

}
