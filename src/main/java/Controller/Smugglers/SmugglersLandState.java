package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

import java.util.List;

/**
 * This state handles the phase where a player who successfully surpassed the smugglers' power
 * gets to collect rewards in the form of goods. The player can place goods in their cargo holds
 * and rearrange them as needed before confirming the end of their action.
 */
public class SmugglersLandState extends State{
    /**
     * Context object that holds the game state and player information.
     */
    private Context context;

    /**
     * Constructor to initialize the state with the current game context.
     *
     * @param context the shared game context
     */
    public SmugglersLandState(Context context) {
        this.context = context;
    }

    /**
     * Allows the player to collect a good from the available reward pool and place it into one
     * of their cargo holds at a specific index.
     *
     * @param playerName the name of the player placing the good
     * @param goodIndex the index of the good in the reward pool
     * @param coordinates the coordinates of the cargo hold where the good is placed
     * @param CargoHoldIndex the index within the cargo hold to place the good
     */
    @Override
    public void getGood(String playerName, int goodIndex, Coordinates coordinates, int CargoHoldIndex) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn");
        }

        if(coordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid coordinates");
        }

        if(goodIndex < 0 || goodIndex >= context.getGoods().size()){
            controller.getModel().setError(true);
            throw new IndexOutOfBoundsException("Invalid goodIndex");
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null || !player.getShipBoard().getCondensedShip().getCargoHolds().contains(component)) {  //non è un CargoHold
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Invalid component type, expected CargoHold");
        }

        CargoHold cargoHold = (CargoHold) component;
        Good selectedGood = context.getGoods().get(goodIndex);
        if(selectedGood == null) {
            controller.getModel().setError(true);
           throw new InvalidParameters("Good not found");
        }

        boolean done = cargoHold.addGoodAt(selectedGood, CargoHoldIndex);
        if (!done) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Cannot add good to the cargo hold");
        }

        context.removeGood(selectedGood);
        controller.getModel().setState(new SmugglersLandState(context));
        controller.getModel().setError(false);
    }

    /**
     * Allows the player to rearrange goods between cargo holds during the reward phase.
     *
     * @param name the name of the player moving the good
     * @param oldCoordinates coordinates of the origin cargo hold
     * @param newCoordinates coordinates of the target cargo hold
     * @param oldIndex the index of the good in the origin cargo hold
     * @param newIndex the target index in the destination cargo hold
     */
    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex) throws InvalidParameters, InvalidContextualAction {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn");
        }

        SpaceshipComponent oldComponent = player.getShipBoard().getComponent(oldCoordinates);
        SpaceshipComponent newComponent = player.getShipBoard().getComponent(newCoordinates);

        if(!player.getShipBoard().getCondensedShip().getCargoHolds().contains(oldComponent) ||
                !player.getShipBoard().getCondensedShip().getCargoHolds().contains(newComponent)) {
            controller.getModel().setError(true);
           throw new InvalidContextualAction("Invalid components, expected CargoHolds");
        }
        CargoHold oldCargoHold = (CargoHold) oldComponent;
        CargoHold newCargoHold = (CargoHold) newComponent;
        Good selectedGood = oldCargoHold.getGoods()[oldIndex];
        if(selectedGood == null) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Good not found");
        }

        boolean done = newCargoHold.addGoodAt(selectedGood, newIndex);
        if (!done) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Cannot add good to the cargo hold");
        }
        oldCargoHold.removeGood(oldIndex);
        controller.getModel().setState(new SmugglersLandState(context));
        controller.getModel().setError(false);
    }

    /**
     * Ends the player's reward phase. If there are more players to manage, the state
     * transitions back to {@code SmugglersGoodsRemovalState}, otherwise to the flight phase.
     *
     * @param playerName the name of the player finishing the reward phase
     */
    @Override
    public void end(String playerName) throws InvalidParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn");
        }
        if(context.getSpecialPlayers().isEmpty()){
            controller.getModel().setState(new FlightPhase(controller));
            controller.getModel().setError(false);
        }else{
            controller.getModel().setState(new SmugglersGoodsRemovalState(context)); //manca qualcuno da gestire
            controller.getModel().setError(false);
        }
    }
    @Override
    public List<String> getAvailableCommands(){
        return List.of(
                "GetGood",
                "MoveGood",
                "End"
        );
    }
}
