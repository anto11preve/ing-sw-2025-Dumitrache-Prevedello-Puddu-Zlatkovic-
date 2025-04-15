package Controller.Enemy;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.Smugglers;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Controller.LandState;
import Controller.Penalty.GoodsPenaltyState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SmugglersStateBatteryRemoved extends State {
    Controller controller;
    Smugglers card;
    List<Player> players;
    List<Player> penalizedPlayers;
    int declaredPower;
    int batteriesRequired;

    public SmugglersStateBatteryRemoved(Controller controller, Smugglers card, List<Player> penalizedPlayers, int declaredPower, int batteriesRequired) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<Player>();
        this.players.addAll(controller.getModel().getPlayers());
        this.penalizedPlayers = penalizedPlayers;
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
                        controller.setState(new SmugglersStateBatteryRemoved(controller, card, penalizedPlayers, declaredPower, batteriesRequired));
                    } else {
                        if (declaredPower > card.getFirePower()) {  //se vinci
                            controller.setState(new LandState(controller, card, players.getFirst()));
                        } else {    //se non vinci
                            if (declaredPower < card.getFirePower()) {  //se perdi
                                penalizedPlayers.add(players.getFirst());

                            }
                            players.remove(players.getFirst());
                            if(players.isEmpty()){
                                controller.setState(new GoodsPenaltyState(controller, card, penalizedPlayers));
                            } else {
                            controller.setState(new SmugglersStatePowerDeclaration(controller, card, penalizedPlayers));
                            }
                        }
                    }
                }
        }

    }

}
