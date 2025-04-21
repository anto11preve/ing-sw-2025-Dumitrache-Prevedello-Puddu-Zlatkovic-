package Controller.OpenSpace;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.OpenSpace;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Controller.FlightPhase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OpenSpaceStateBatteryRemoval extends State {
    Controller controller;
    OpenSpace card;
    List<Player> players;
    int declaredPower;
    int batteriesRequired;

    public OpenSpaceStateBatteryRemoval(Controller controller, OpenSpace card, int declaredPower, int batteriesRequired) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<Player>();
        this.players.addAll(controller.getModel().getPlayers());
        this.declaredPower = declaredPower;
        this.batteriesRequired = batteriesRequired;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller){
        String action = (String) command.get("command");
        String playerName = (String) command.get("playerName");

        switch (action) {
            case "removeBattery":
                if(Objects.equals(playerName, players.getFirst().getName())){   //devo passare anche i player a sto punto
                    BatteryCompartment compartment = (BatteryCompartment) command.get("compartment"); //modificare col fatto che posso prenderle da più compratment
                    if(compartment.getBatteries()>= batteriesRequired){ //se nel compartment ho abbastanza batterie
                        for(int i=0; i<batteriesRequired; i++){
                            compartment.removeBattery();
                        }
                        batteriesRequired -= compartment.getBatteries();
                    }
                    if(batteriesRequired > 0){  //se devo selezionare un altro compartment
                        controller.setState(new OpenSpaceStateBatteryRemoval(controller, card, declaredPower, batteriesRequired));
                    } else {
                       Player player = players.getFirst();
                       int position = controller.getModel().getFlightboard().getPosition(player);
                       position += declaredPower;
                       controller.getModel().getFlightboard().updatePosition(player, position);
                       players.remove(player);
                       if(players.isEmpty()){   //ho finito di risolvere la carta
                           controller.setState( new FlightPhase());
                       } else { //mancano ancora giocatori
                           controller.setState(new OpenSpaceState(controller, card, players));
                       }
                    }
                }
        }

    }
}
