package Controller.Enemy;
import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.Smugglers;
import Model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SmugglersStatePowerDeclaration extends State {
    Controller controller;
    Smugglers card;
    List<Player> players;
    List<Player> penalizedPlayers;

    public SmugglersStatePowerDeclaration(Controller controller, Smugglers card) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightboard().getTurnOrder()));

    }

    public SmugglersStatePowerDeclaration(Controller controller, Smugglers card, List<Player> penalizedPlayers) {
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
                        controller.setState(new SmugglersStateBatteryRemoved(controller, card, penalizedPlayers, declaredPower, doubleCannons));
                }
        }

    }
}
