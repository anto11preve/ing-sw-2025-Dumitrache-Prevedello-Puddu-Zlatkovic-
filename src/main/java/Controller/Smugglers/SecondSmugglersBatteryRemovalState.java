package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

public class SecondSmugglersBatteryRemovalState extends State{
    private Context context;
    private int amount;

    public SecondSmugglersBatteryRemovalState(Context context, int amount) {
        this.context = context;
        this.amount = amount;
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        if(itemType != ItemType.BATTERIES){
            return;
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
        if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            return;
        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        amount--;
        if(amount == 0){
            context.removeSpecialPlayer(player);
            if(context.getSpecialPlayers().isEmpty()){
                controller.setState(new FlightPhase());
            } else {
                controller.setState(new SmugglersGoodsRemovalState(context));
            }
        } else {
            controller.setState(new SecondSmugglersBatteryRemovalState(context, amount));
        }
    }
}
