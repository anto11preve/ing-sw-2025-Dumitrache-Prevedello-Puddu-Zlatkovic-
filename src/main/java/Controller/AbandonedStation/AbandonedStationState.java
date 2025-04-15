package Controller.AbandonedStation;
import Controller.Controller;
import Controller.State;
import Controller.LandState;
import Model.Board.AdventureCards.AbandonedStation;
import Model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AbandonedStationState extends State{
    Controller controller;
    AbandonedStation card;
    List<Player> players;

    public AbandonedStationState(Controller controller, AbandonedStation card) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightboard().getTurnOrder()));
    }

    public AbandonedStationState(Controller controller, AbandonedStation card, List<Player> players) {
        this.controller = controller;
        this.card = card;
        this.players = players;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller){
        String action = (String) command.get("command");
        String playerName = (String) command.get("playerName");

        switch (action){
            case "GetRewards":
                if(playerName.equals(players.get(0).getName())){
                    int crew = players.get(0).getShipBoard().getCondensedShip().getTotalCrew();
                    if(crew > card.getCrew()){//getCrew deve dare un int, da modificare
                        //update della posizione
                        Player player = controller.getModel().getPlayer(playerName);
                        int position = controller.getModel().getFlightboard().getPosition(player);
                        position += card.getLostDays();
                        controller.getModel().getFlightboard().updatePosition(player, position);
                        //atterra
                        controller.setState(new LandState(controller, card, player));
                    }
                }
            case "IgnoreRewards":
                players.remove(0);
                controller.setState(new AbandonedStationState(controller, card, players));
        }
    }
}
