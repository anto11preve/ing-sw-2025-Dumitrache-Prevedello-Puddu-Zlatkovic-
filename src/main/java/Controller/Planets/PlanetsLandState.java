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

/**
 * This state allows players to collect goods from the planets they landed on during the {@link ChoosePlanetState}.
 * Players can select goods to store in their cargo holds or rearrange their existing goods before ending the phase.
 * Once all players and planets are handled, the game transitions back to the {@link FlightPhase}.
 */
public class PlanetsLandState extends State {
    /**
     * The context of the game, which contains information about the current state and players.
     */
    private Context context;
    /**
     * The list of planets that have been chosen by players.
     */
    List<Planet> chosenPlanets;

    /**
     * Constructs a new PlanetsLandState.
     *
     * @param context The context of the game.
     */
    public PlanetsLandState(Context context, List<Planet> chosenPlanets) {
        this.context = context;
        this.chosenPlanets = chosenPlanets;
    }

    /**
     * Allows a player to collect a good from the current planet and store it in a cargo hold.
     * Only the first special player can perform this action.
     * The good must be placed in a valid cargo hold component.
     *
     * If the good is successfully added, the planet and player are removed from their respective lists.
     * The game continues until all chosen planets and special players are processed.
     *
     * @param playerName     the name of the player collecting the good
     * @param goodIndex      the index of the good in the planet's reward list
     * @param coordinates    the coordinates of the cargo hold on the player's ship
     * @param CargoHoldIndex the index within the cargo hold where the good should be placed
     */
    @Override
    public void getGood(String playerName, int goodIndex, Coordinates coordinates, int CargoHoldIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        if(context.getSpecialPlayers().getFirst() != player)
            return; // Handle the case where the player is not the first special player
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(!player.getShipBoard().getCondensedShip().getCargoHolds().contains(component))   //non è un CargoHold
            return;
        CargoHold cargoHold = (CargoHold) component;
        Planet planet = chosenPlanets.getFirst();
        if(planet == null) {
            return; // Handle the case where the planet is not found
        }
        if(!planet.isOccupied()){
            return; // Handle the case where the planet is not occupied
        }
        Good selectedGood = null;   //da correggere
        ///TODO: Good selectedGood = planet.getLandingReward().iterator(goodIndex);

        if(selectedGood == null) {
            return; // Handle the case where the good is not found
        }

        boolean done = cargoHold.addGoodAt(selectedGood, CargoHoldIndex);
        if (!done) {
            return; // Handle the case where the good cannot be added to the cargo hold
        }

        ///TODO: planet.getLandingReward().remove(selectedGood);
        if(true) {  /// TODO: replace with planet.getLandingReward().isEmpty()
            chosenPlanets.remove(0);
            context.removeSpecialPlayer(player);
            if(chosenPlanets.isEmpty() && context.getSpecialPlayers().isEmpty()) {      //dovrei controllare anche le non conformità delle due liste
                controller.setState(new FlightPhase(controller));     //finiti pianeti occupati
            } else {
                controller.setState(new PlanetsLandState(context, chosenPlanets));
            }
        } else{
            controller.setState(new PlanetsLandState(context, chosenPlanets));
        }

    }

    /**
     * Allows a player to move a good from one cargo hold to another.
     * Both source and destination must be valid cargo holds.
     *
     * @param name          the name of the player performing the move
     * @param oldCoordinates the coordinates of the current cargo hold
     * @param newCoordinates the coordinates of the target cargo hold
     * @param oldIndex       the index of the good in the original cargo hold
     * @param newIndex       the index in the target cargo hold where the good should be placed
     */
    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        SpaceshipComponent oldComponent = player.getShipBoard().getComponent(oldCoordinates);
        SpaceshipComponent newComponent = player.getShipBoard().getComponent(newCoordinates);

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
        boolean done = newCargoHold.addGoodAt(selectedGood, newIndex);
        if (!done) {
            return; // Handle the case where the good cannot be added to the new cargo hold
        }
        oldCargoHold.removeGood(oldIndex);
        controller.setState(new PlanetsLandState(context, chosenPlanets));
    }

    /**
     * Called when a player ends their interaction with the current planet.
     * Immediately transitions to the {@link FlightPhase}.
     *
     * @param playerName the name of the player ending the phase
     */
    @Override
    public void end(String playerName){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        controller.setState(new FlightPhase(controller));
    }
}
