package Controller.OpenSpace;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.OpenSpace;
import Model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenSpaceState extends State {
    Controller controller;
    OpenSpace card;
    List<Player> players;

    public OpenSpaceState(Controller controller, OpenSpace card){
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<Player>();
        this.players.addAll(controller.getModel().getPlayers());
    }

    public OpenSpaceState(Controller controller, OpenSpace card, List<Player> players){
        this.controller = controller;
        this.card = card;
        this.players = players;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller){
        String action = (String) command.get("command");
        String playerName = (String) command.get("playerName");

        switch (action) {
            case "DeclareEnginePower":
                int doubleEngines = (int) command.get("doubleEngines");
                int declaredPower = players.getFirst().getEnginePower() + doubleEngines;
                controller.setState(new OpenSpaceStateBatteryRemoval(controller, card, declaredPower, doubleEngines));
        }
    }


}
