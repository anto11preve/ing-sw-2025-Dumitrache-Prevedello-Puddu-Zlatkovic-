package Controller.AbandonedStation;

import Controller.Context;
import Controller.Controller;
import Controller.State;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.GamePhases.FlightPhase;


/**
 * Represents the state in which a player manages the goods obtained from an abandoned station.
 *
 * <p>During this state, the player can choose where to place goods in cargo holds,
 * move goods between cargo holds, or end their action. Each operation is validated
 * to ensure it is the player's turn and that the operations are legal according to the game rules.</p>
 */
public class AbandonedStationLandState extends State {
    /** The shared context containing game state and references. */
    private Context context;

    /**
     * Constructs the state with the given context.
     *
     * @param context the game context used to access controller and shared state.
     */
    public AbandonedStationLandState(Context context) {
        this.context = context;
    }

    /**
     * Places a selected good from the reward into a specific cargo hold at the given position.
     *
     * <p>The good is removed from the context once placed. If the specified component
     * is not a valid cargo hold or the insertion fails, no state transition occurs.</p>
     *
     * @param playerName     the name of the player placing the good
     * @param goodIndex      the index of the good in the context's list of goods
     * @param coordinates    the coordinates of the cargo hold on the player's ship
     * @param CargoHoldIndex the index within the cargo hold where the good should be placed
     */
    @Override
    public void getGood(String playerName, int goodIndex, Coordinates coordinates, int CargoHoldIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
        if(!player.getShipBoard().getCondensedShip().getCargoHolds().contains(component))   //non è un CargoHold
            return;

        CargoHold cargoHold = (CargoHold) component;
        Good selectedGood = context.getGoods().get(goodIndex);
        if(selectedGood == null) {
            return; // Handle the case where the good is not found
        }

        boolean done = cargoHold.addGood(selectedGood, CargoHoldIndex);
        if (!done) {
            return; // Handle the case where the good cannot be added to the cargo hold
        }

        context.removeGood(selectedGood);
        controller.setState(new AbandonedStationLandState(context));
    }

    /**
     * Moves a good from one cargo hold to another within the player's ship.
     *
     * <p>If either coordinate does not refer to a valid cargo hold, or if the move fails,
     * no changes are made. On success, the good is moved and the state is refreshed.</p>
     *
     * @param name         the name of the player performing the move
     * @param oldCoordinates the coordinates of the source cargo hold
     * @param newCoordinates the coordinates of the target cargo hold
     * @param oldIndex     the index of the good in the source cargo hold
     * @param newIndex     the index where the good should be placed in the target cargo hold
     */
    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
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
        controller.setState(new AbandonedStationLandState(context));
    }

    /**
     * Ends the current player's turn and resumes the flight phase.
     *
     * @param playerName the name of the player ending their action
     */
    @Override
    public void end(String playerName){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        controller.setState(new FlightPhase());
    }
}
