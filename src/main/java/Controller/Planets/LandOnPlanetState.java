package Controller.Planets;

import Controller.Controller;
import Controller.State;
import Controller.FlightPhase;
import Model.Board.AdventureCards.AvailablePlanets;
import Model.Board.AdventureCards.Planet;
import Model.Enums.Good;
import Model.Player;

import java.util.List;
import java.util.Map;

public class LandOnPlanetState extends State {
    Controller controller;
    List<Good> goods;
    AvailablePlanets card;
    List<Player> landedPlayers;
    List<Planet> chosenPlanets;

    public LandOnPlanetState(Controller controller, AvailablePlanets card, List<Player> landedPlayers, List<Planet> chosenPlanets) {
        this.controller = controller;
        this.goods = chosenPlanets.get(0).getAvailableGoods();
        this.card = card;
        this.landedPlayers = landedPlayers;
        this.chosenPlanets = chosenPlanets;
    }

    public LandOnPlanetState(Controller controller, AvailablePlanets card, List<Player> landedPlayers, List<Planet> chosenPlanets, List<Good> goods) {
        this.controller = controller;
        this.goods = goods;
        this.card = card;
        this.landedPlayers = landedPlayers;
        this.chosenPlanets = chosenPlanets;
    }


    @Override
    public void execute(Map<String, Object> command, Controller controller) {
        String action = (String) command.get("action");
        String playerName = (String) command.get("playerName");
        Player player = controller.getModel().getPlayer(playerName);

        if(player.getName().equals(playerName))
            switch (action) {
                case "GetGood":     //riceve la persona che chiama la execute, quale good prendere e dove metterlo
                    int goodIndex = (int) command.get("goodIndex");
                    if (goodIndex >= 0 && goodIndex < goods.size()){
                        Good selectedGood = goods.get(goodIndex);
                        //aggiungi il good in quel posto
                        goods.remove(selectedGood);
                        if(goods.isEmpty()){    //finito di scaricare dal pianeta
                            int position = controller.getModel().getFlightboard().getPosition(player);
                            position += card.getLandingPenalty();
                            controller.getModel().getFlightboard().updatePosition(player, position);
                            landedPlayers.remove(0);
                            chosenPlanets.remove(0);
                            if(chosenPlanets.isEmpty() && landedPlayers.isEmpty()){ //gestiti tutti i giocatori atterrati
                                controller.setState(new FlightPhase());
                            } else{
                                controller.setState(new LandOnPlanetState(controller,card,landedPlayers,chosenPlanets));
                            }
                        } else {
                            controller.setState(new LandOnPlanetState(controller, card, landedPlayers, chosenPlanets, goods));
                        }
                    }
                    break;
                case "MoveGood":    //riceve la persona, quale good spostare, dove spostarlo e da dove spostarlo

                    break;
                default:
                    //do nothing
                    break;
            }
    }
}
