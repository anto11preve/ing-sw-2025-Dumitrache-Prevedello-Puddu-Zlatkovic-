package Controller.Penalty;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.AdventureCard;
import Model.Player;

import java.util.List;
import java.util.Map;

public class GoodsPenaltyState extends State {

    Controller controller;
    AdventureCard card;
    List<Player> penalizedPlayers;

    public GoodsPenaltyState(Controller controller, AdventureCard card, List<Player> penalizedPlayers) {
        this.controller = controller;
        this.card = card;
        this.penalizedPlayers = penalizedPlayers;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller) {
        String action = (String) command.get("action");
        String playerName = (String) command.get("playerName");

        switch (action) {
            case "GetPenalty":
        }
    }
}
