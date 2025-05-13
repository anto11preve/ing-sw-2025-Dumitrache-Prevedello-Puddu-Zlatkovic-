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

public class SmugglersLandState extends State{
    private Context context;

    public SmugglersLandState(Context context) {
        this.context = context;
    }

    @Override
    public void getGood(String playerName, int goodIndex, Coordinates coordinates, int CargoHoldIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
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
        controller.setState(new SmugglersLandState(context));
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
        controller.setState(new SmugglersLandState(context));
    }

    @Override
    public void end(String playerName){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        if(context.getSpecialPlayers().isEmpty()){
            controller.setState(new FlightPhase());
        }else{
            controller.setState(new SmugglersGoodsRemovalState(context)); //manca qualcuno da gestire
        }
    }
}
