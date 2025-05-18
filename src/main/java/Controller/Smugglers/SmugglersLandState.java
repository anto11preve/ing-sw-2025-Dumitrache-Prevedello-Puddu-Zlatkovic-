package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

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
    public void getGood(String playerName, int goodIndex, Coordinates coordinates, int CargoHoldIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn

        if(coordinates == null){
            return; // Handle the case where coordinates are null
        }

        if(goodIndex < 0 || goodIndex >= context.getGoods().size()){
            return; // Handle the case where the good index is out of bounds
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
        if(component == null || !player.getShipBoard().getCondensedShip().getCargoHolds().contains(component))   //non è un CargoHold
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
        controller.setState(new SmugglersLandState(context));
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
        controller.setState(new SmugglersLandState(context));
    }

    /**
     * Ends the player's reward phase. If there are more players to manage, the state
     * transitions back to {@code SmugglersGoodsRemovalState}, otherwise to the flight phase.
     *
     * @param playerName the name of the player finishing the reward phase
     */
    @Override
    public void end(String playerName){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player != controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        if(context.getSpecialPlayers().isEmpty()){
            controller.setState(new FlightPhase());
        }else{
            controller.setState(new SmugglersGoodsRemovalState(context)); //manca qualcuno da gestire
        }
    }
}
