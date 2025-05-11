package Controller.AbandonedShip;

import Controller.Controller;
import Controller.State;
import Controller.SubStates.DecidingState;
import Model.Board.AdventureCards.AbandonedShip;
import Model.Player;
import Controller.SubStates.SubState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbandonedShipState extends State {
    private Controller controller;
    private AbandonedShip card;
    private int crewmates;
    private List<Player> players;


    public AbandonedShipState(Controller controller, AbandonedShip card) {
        this.controller = controller;
        this.card = card;
        this.crewmates = card.getCrew();
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.state = new DecidingState();
    }

    public AbandonedShip getCard() {
        return card;
    }

    public int getCrewmates() {
        return crewmates;
    }
    public void setCrewmates(int crewmates) {
        this.crewmates = crewmates;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void removePlayer(Player player) {
        players.remove(player);
    }


}
