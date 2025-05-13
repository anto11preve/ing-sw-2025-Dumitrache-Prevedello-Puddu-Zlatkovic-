package Controller.OpenSpace;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.State;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.GamePhases.FlightPhase;

public class OpenSpaceBatteryRemovalState extends State {
    private Context context;
    private int declaredPower;

    public OpenSpaceBatteryRemovalState(Context context, int declaredPower) {
        this.context = context;
        this.declaredPower = declaredPower;
    }


    //DA RIVEDERE
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
        declaredPower--;
        if(declaredPower == 0){
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){         //passati tutti
                controller.setState(new FlightPhase());
            }
            else{       //manca qualcuno da gestire
                controller.setState(new OpenSpaceEngineDeclarationState(context));
            }
        }
        else{       //rimuovi altra batteria
            controller.setState(new OpenSpaceBatteryRemovalState(context, declaredPower));
        }
    }
}
