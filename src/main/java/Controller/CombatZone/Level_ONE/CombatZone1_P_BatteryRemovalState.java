package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class CombatZone1_P_BatteryRemovalState extends State {
    private Context context;
    private int declaredPower;
    private int actualPower;
    private int worst;

    public CombatZone1_P_BatteryRemovalState(Context context, int declaredPower, int actualPower) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.actualPower = actualPower;
    }

    public CombatZone1_P_BatteryRemovalState(Context context, int declaredPower, int actualPower, int worst) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.actualPower = actualPower;
        this.worst = worst;
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidMethodParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.BATTERIES){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid item type, expected BATTERIES");
        }

        if(declaredPower < 0){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Declared power cannot be negative");
        }

        if(coordinates == null){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Coordinates cannot be null");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component)) {
            controller.getModel().setError(true);//non è un Battery
            throw new InvalidContextualAction("Invalid component type, expected BatteryCompartment");
        }

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        actualPower++;
        if(declaredPower == 0){
            if(context.getSpecialPlayers().isEmpty()){
                context.addSpecialPlayer(player);
            } else {
                if(actualPower > worst){
                    context.removeSpecialPlayer(context.getSpecialPlayers().getFirst());
                    context.addSpecialPlayer(player);
                    worst = actualPower;
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
            controller.getModel().setState(new CombatZone1_P_BatteryRemovalState(context, declaredPower, actualPower));
            controller.getModel().setError(false);
        }
    }
}
