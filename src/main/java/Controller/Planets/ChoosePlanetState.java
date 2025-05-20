package Controller.Planets;

import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.Components.Planet;
import Model.Player;

import java.util.HashSet;
import java.util.List;

/**
 * This state allows the first player in turn to choose a planet to land on.
 * Each planet can only be selected once. Players who choose a valid, unoccupied planet
 * are removed from the turn order and added to the special players list.
 *
 * Once all players have made a decision or all available planets have been selected,
 * the game transitions to the {@link PlanetsLandState}.
 */
public class ChoosePlanetState extends State {
    /**
     * The context of the game, which contains information about the current state and players.
     */
    Context context;
    /**
     * The list of planets that have been chosen by players.
     */
    List<Planet> chosenPlanets;

    /**
     * Constructs a new ChoosePlanetState.
     *
     * @param context The context of the game.
     */
    public ChoosePlanetState(Context context) {
        this.context = context;
    }

    /**
     * Constructs a new ChoosePlanetState with the specified context and chosen planets.
     *
     * @param context The context of the game.
     * @param choosenPlanets The list of planets that have been chosen by players so far.
     */
    public ChoosePlanetState(Context context, List<Planet> choosenPlanets) {
        this.context = context;
        this.chosenPlanets = choosenPlanets;
    }

    /**
     * Called when a player selects a planet.
     * The method verifies that the player is allowed to choose,
     * and that the planet is valid, unoccupied, and not already selected.
     * If the choice is successful, the player is removed from the active list
     * and added to the special players list.
     *
     * When all players have chosen or all planets are taken,
     * the state changes to {@link PlanetsLandState}.
     *
     * @param playerName the name of the player making the choice
     * @param name       the name of the chosen planet
     */
    @Override
    public void choosePlanet(String playerName, String name) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            throw new IllegalArgumentException("It's not your turn to choose a planet");

        Planet chosenPlanet = context.getPlanet(name);
        if(chosenPlanet == null) {
            throw new NullPointerException("Planet not found");
        }

        if(chosenPlanet.isOccupied()) {
            throw new InvalidContextualAction("Planet is already occupied");
        }

        chosenPlanet.setOccupied();
        if(chosenPlanets.contains(chosenPlanet)){
            throw new InvalidContextualAction("Planet already chosen");
        } else {
            chosenPlanets.add(chosenPlanet);
        }

        if(context.getSpecialPlayers().contains(player)) {
            throw new InvalidContextualAction("Player already chosen a planet");
        }

        context.addSpecialPlayer(player);
        context.removePlayer(player);

        if(context.getPlayers().isEmpty() || new HashSet<>(chosenPlanets).containsAll(context.getPlanets())) { // Handle the case where all players have chosen a planet, or all the planets has been chosen
            controller.setState(new PlanetsLandState(context, chosenPlanets));
        } else {
            controller.setState(new ChoosePlanetState(context, chosenPlanets));
        }
    }

    /**
     * Called when a player decides to skip choosing a planet.
     * The player is removed from the list of active players,
     * and the state is refreshed.
     *
     * @param playerName the name of the player skipping the choice
     */
    @Override
    public void end(String playerName) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            throw new IllegalArgumentException("It's not your turn");

        context.removePlayer(player);
        controller.setState(new ChoosePlanetState(context, chosenPlanets));
    }
}
