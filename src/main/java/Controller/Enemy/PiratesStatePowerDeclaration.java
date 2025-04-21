package Controller.Enemy;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.Pirates;
import Model.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PiratesStatePowerDeclaration extends State {
    Controller controller;
    Pirates card;
    List<Player> players;
    List<Player> penalizedPlayers;

    public PiratesStatePowerDeclaration(Controller controller, Pirates card) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<Player>();
        this.players.addAll(controller.getModel().getPlayers());

    }

    public PiratesStatePowerDeclaration(Controller controller, Pirates card, List<Player> penalizedPlayers) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<Player>();
        this.players.addAll(controller.getModel().getPlayers());
        this.penalizedPlayers = penalizedPlayers;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller){
        String action = (String) command.get("command");
        String playerName = (String) command.get("playerName");

        switch (action) {
            case "DeclarePower":
                if(playerName == players.getFirst().getName()){
                    int doubleCannons = (int) command.get("doubleCannons");
                    int declaredPower = players.getFirst().getFirePower() + doubleCannons;
                    if(players.getFirst().getShipBoard().getCondensedShip().getTotalBatteries()>=doubleCannons)
                        controller.setState(new PiratesStateBatteryRemoved(controller, card, penalizedPlayers, declaredPower, doubleCannons));
                }
        }

    }
}
