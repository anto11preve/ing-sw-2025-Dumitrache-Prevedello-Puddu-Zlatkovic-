package Controller.Planets;

import Controller.Context;
import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.Components.Planet;
import Model.Player;

import java.util.List;

public class ChoosePlanetState extends State {
    Context context;
    List<Planet> chosenPlanets;

    public ChoosePlanetState(Context context) {
        this.context = context;
    }

    public ChoosePlanetState(Context context, List<Planet> choosenPlanets) {
        this.context = context;
        this.chosenPlanets = choosenPlanets;
    }

    @Override
    public void choosePlanet(String playerName, String name) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        Planet chosenPlanet = context.getPlanet(name);
        if(chosenPlanet == null) {
            return; // Handle the case where the planet is not found
        }
        if(chosenPlanet.isOccupied()== true) {
            return; // Handle the case where the planet is already occupied
        }
        chosenPlanet.setOccupied();
        if(chosenPlanets.contains(chosenPlanet)){
            return; // Handle the case where the planet is alreaady in the list
        } else {
            chosenPlanets.add(chosenPlanet);
        }
        if(context.getSpecialPlayers().contains(player)) {
            return; // Handle the case where the player is already a special player
        }
        context.addSpecialPlayer(player);
        context.removePlayer(player);
        if(context.getPlayers().isEmpty()) { // Handle the case where all players have chosen a planet
            controller.setState(new PlanetsLandState(context, chosenPlanets));
        } else {
            controller.setState(new ChoosePlanetState(context, chosenPlanets));
        }
    }
}
