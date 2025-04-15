package Controller.Enemy;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.Slavers;
import Model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlaversStatePowerDeclaration extends State {
    Controller controller;
    Slavers card;
    List<Player> players;
    List<Player> penalizedPlayers;

    public SlaversStatePowerDeclaration(Controller controller, Slavers card) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<Player>();
        this.players.addAll(controller.getModel().getPlayers());

    }

    public SlaversStatePowerDeclaration(Controller controller, Slavers card, List<Player> penalizedPlayers) {
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
                        controller.setState(new SlaversStateBatteryRemoved(controller, card, penalizedPlayers, declaredPower, doubleCannons));
                }
        }

    }
}
