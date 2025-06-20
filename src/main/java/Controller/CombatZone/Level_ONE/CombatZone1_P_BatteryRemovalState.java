package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class CombatZone1_P_BatteryRemovalState extends State {
    private Context context;
    private double declaredPower;
    private int batteries;
    private double worst;

    public CombatZone1_P_BatteryRemovalState(Context context, double declaredPower, int batteries) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.batteries = batteries;
    }

    public CombatZone1_P_BatteryRemovalState(Context context, double declaredPower, int batteries, double worst) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.batteries = batteries;
        this.worst = worst;
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidMethodParameters, InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.BATTERIES){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid item type, expected BATTERIES");
        }

        if(declaredPower < 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared power cannot be negative");
        }

        if(coordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Coordinates cannot be null");
        }

        if(batteries < 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid number of batteries");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not the player's turn");
        }

        if (batteries > 0) {
            SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
            if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component)) {   //non è un Battery
                controller.getModel().setError(true);
                throw new InvalidContextualAction("Invalid component type, expected BatteryCompartment");
            }

            BatteryCompartment compartment = (BatteryCompartment) component;
            compartment.removeBattery();
            batteries--;
        }
        if(batteries == 0){
            if(context.getSpecialPlayers().isEmpty()){
                context.addSpecialPlayer(player);
            } else {
                if(declaredPower > worst){
                    context.removeSpecialPlayer(context.getSpecialPlayers().getFirst());
                    context.addSpecialPlayer(player);
                    worst = declaredPower;
                }
            }
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){
                controller.getModel().setState(new CombatZone1CannonShotsState(context));
                controller.getModel().setError(false);
            } else {
                controller.getModel().setState(new CombatZone1PowerDeclarationState(context, worst));
                controller.getModel().setError(false);
            }
        }
        else{       //rimuovi altra batteria
            controller.getModel().setState(new CombatZone1_P_BatteryRemovalState(context, declaredPower, batteries));
            controller.getModel().setError(false);
        }
    }
}
