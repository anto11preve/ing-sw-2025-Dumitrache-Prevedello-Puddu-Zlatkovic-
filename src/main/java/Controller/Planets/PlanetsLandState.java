package Controller.Planets;

import Controller.Context;
import Controller.Controller;
import Model.Board.AdventureCards.Components.Planet;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

import java.util.List;

public class PlanetsLandState extends State {
    private Context context;
    List<Planet> chosenPlanets;

    public PlanetsLandState(Context context, List<Planet> chosenPlanets) {
        this.context = context;
        this.chosenPlanets = chosenPlanets;
    }

    @Override
    public void getGood(String playerName, int goodIndex, Coordinates coordinates, int CargoHoldIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        if(context.getSpecialPlayers().get(0) != player)
            return; // Handle the case where the player is not the first special player
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
        if(!player.getShipBoard().getCondensedShip().getCargoHolds().contains(component))   //non è un CargoHold
            return;
        CargoHold cargoHold = (CargoHold) component;
        Planet planet = chosenPlanets.get(0);
        if(planet == null) {
            return; // Handle the case where the planet is not found
        }
        if(!planet.isOccupied()){
            return; // Handle the case where the planet is not occupied
        }
        Good selectedGood = planet.getLandingReward().iterator(goodIndex); //come cazzo si usa aaaa

        if(selectedGood == null) {
            return; // Handle the case where the good is not found
        }

        boolean done = cargoHold.addGood(selectedGood, CargoHoldIndex);
        if (!done) {
            return; // Handle the case where the good cannot be added to the cargo hold
        }

        planet.getLandingReward().remove(selectedGood);
        if(planet.getLandingReward().isEmpty()) {
            chosenPlanets.remove(0);
            context.removeSpecialPlayer(player);
            if(chosenPlanets.isEmpty() && context.getSpecialPlayers().isEmpty()) {      //dovrei controllare anche le non conformità delle due liste
                controller.setState(new FlightPhase());     //finiti pianeti occupati
            } else {
                controller.setState(new PlanetsLandState(context, chosenPlanets));
            }
        } else{
            controller.setState(new PlanetsLandState(context, chosenPlanets));
        }

    }

    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        SpaceshipComponent oldComponent = player.getShipBoard().getComponent(oldCoordinates.getX(), oldCoordinates.getY());
        SpaceshipComponent newComponent = player.getShipBoard().getComponent(newCoordinates.getX(), newCoordinates.getY());

        if(!player.getShipBoard().getCondensedShip().getCargoHolds().contains(oldComponent) ||
                !player.getShipBoard().getCondensedShip().getCargoHolds().contains(newComponent)) {
            return; // Handle the case where the components are not cargo holds
        }
        CargoHold oldCargoHold = (CargoHold) oldComponent;
        CargoHold newCargoHold = (CargoHold) newComponent;
        Good selectedGood = oldCargoHold.getGoods()[oldIndex];
        if(selectedGood == null) {
            return; // Handle the case where the good is not found
        }
        boolean done = newCargoHold.addGood(selectedGood, newIndex);
        if (!done) {
            return; // Handle the case where the good cannot be added to the new cargo hold
        }
        oldCargoHold.removeGood(oldIndex);
        controller.setState(new PlanetsLandState(context, chosenPlanets));
    }

    @Override
    public void end(String playerName){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        controller.setState(new FlightPhase());
    }
}
