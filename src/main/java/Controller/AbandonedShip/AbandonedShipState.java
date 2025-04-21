package Controller.AbandonedShip;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.AbandonedShip;
import Model.Player;
import Controller.RemoveCrewState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AbandonedShipState extends State {
    Controller controller;
    AbandonedShip card;
    List<Player> players;

    public AbandonedShipState(Controller controller, AbandonedShip card) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightboard().getTurnOrder()));
    }

    public AbandonedShipState(Controller controller, AbandonedShip card, List<Player> players) {
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
                        position += card.getDays();
                        controller.getModel().getFlightboard().updatePosition(player, position);
                        //prendi crediti
                        player.deltaCredits(card.getCredits());
                        controller.setState(new RemoveCrewState(controller, card.getCrew(), player));
                    }
                }
            case "IgnoreRewards":
                players.remove(0);
                controller.setState(new AbandonedShipState(controller, card, players));
            default:
                //do nothing
                break;
        }
    }

}
