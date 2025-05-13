package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Ship.GoodCounter;
import Controller.GamePhases.FlightPhase;
import Controller.State;

public class SmugglersGoodsRemovalState extends State{
    private Context context;
    private GoodCounter goodCounter;
    private int amount;

    public SmugglersGoodsRemovalState(Context context) {
        this.context = context;
        this.amount = context.getRequiredGoods();
    }

    public SmugglersGoodsRemovalState(Context context, int amount) {
        this.context = context;
        this.amount = amount;
    }



    /// rimuove il good, quindi le nuove coordinate sono inutili
    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        GoodCounter goodCounter = player.getShipBoard().getCondensedShip().goodToDiscard(amount);
        if(goodCounter.getRed() + goodCounter.getBlue() + goodCounter.getGreen() + goodCounter.getYellow() == 0){       //non ha abbastanza goods da scartare
            if(player.getShipBoard().getCondensedShip().getTotalBatteries() > 0){   //se almeno ha delle batterie
                controller.setState(new SecondSmugglersBatteryRemovalState(context, amount));
            } else {    //se no non gli succede niente
                context.removeSpecialPlayer(player);
                if(context.getSpecialPlayers().isEmpty()){
                    controller.setState(new FlightPhase());
                } else {
                    controller.setState(new SmugglersGoodsRemovalState(context));
                }
            }
            return;
        }

        SpaceshipComponent Component = player.getShipBoard().getComponent(oldCoordinates.getX(), oldCoordinates.getY());

        if(!player.getShipBoard().getCondensedShip().getCargoHolds().contains(Component)) {
            return; // Handle the case where the components are not cargo holds
        }
        CargoHold cargoHold = (CargoHold) Component;
        Good selectedGood = cargoHold.getGoods()[oldIndex];
        if(selectedGood == null) {
            return; // Handle the case where the good is not found
        }
        boolean done = goodCounter.removeGood(selectedGood);
        if(!done) {
            return; // Need to remove another type of good
        }
        cargoHold.removeGood(oldIndex);
        amount--;
        if(amount == 0){
            context.removeSpecialPlayer(player);
            if(context.getSpecialPlayers().isEmpty()){
                controller.setState(new FlightPhase());
            } else {
                controller.setState(new SmugglersGoodsRemovalState(context));
            }
        } else {
                controller.setState(new SmugglersGoodsRemovalState(context, amount));
        }
    }
}
