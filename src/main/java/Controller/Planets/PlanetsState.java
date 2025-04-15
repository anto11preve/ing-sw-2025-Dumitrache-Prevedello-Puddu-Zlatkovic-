package Controller.Planets;

import Controller.AbandonedShip.AbandonedShipState;
import Controller.State;
import Controller.Controller;
import Model.Board.AdventureCards.AvailablePlanets;
import Model.Board.AdventureCards.Planet;
import Model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlanetsState extends State{
    Controller controller;
    AvailablePlanets card;
    List<Player> players;
    List<Player> landedPlayers;
    List<Planet> chosenPlanets;

    public PlanetsState( Controller controller, AvailablePlanets card) {
        this.controller = controller;
        this.card = card;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightboard().getTurnOrder()));
        this.landedPlayers = new  ArrayList<>();
        this.chosenPlanets = new ArrayList<>();
    }
    public PlanetsState( Controller controller, AvailablePlanets card, List<Player> players, List<Player> landedPlayers, List<Planet> chosenPlanets) {
        this.controller = controller;
        this.card = card;
        this.players = players;
        this.landedPlayers = landedPlayers;
        this.chosenPlanets = chosenPlanets;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller){
        String action = (String) command.get("command");
        String playerName = (String) command.get("playerName");

        switch (action){
            case "LandOnPlanet":
                String planetColor = (String) command.get("planet");
                Planet planet = card.getPlanet(planetColor);


                if(playerName.equals(players.get(0).getName()) && !planet.getOccupied()){
                    planet.setOccupied(true);
                    Player player = players.get(0);
                    landedPlayers.add(player);
                    chosenPlanets.add(planet);
                    players.remove(player);
                    if(players.isEmpty()){
                        controller.setState(new LandOnPlanetState(controller, card, landedPlayers, chosenPlanets));
                    } else {
                        controller.setState(new PlanetsState(controller, card, players, landedPlayers, chosenPlanets));
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
