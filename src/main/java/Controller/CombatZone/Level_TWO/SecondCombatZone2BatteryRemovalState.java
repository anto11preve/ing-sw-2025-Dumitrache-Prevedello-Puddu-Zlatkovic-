package Controller.CombatZone.Level_TWO;

import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.Context;
import Controller.State;

public class SecondCombatZone2BatteryRemovalState extends State {

    private Context context;
    private int amount;

    public SecondCombatZone2BatteryRemovalState(Context context, int amount) {
        this.context = context;
        this.amount = amount;
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidMethodParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.BATTERIES){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid item type, expected BATTERIES");
        }

        if(amount < 0){
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
        if(component == null || !player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component)) {   //non è un Battery
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Invalid component type, expected BatteryCompartment");
        }

        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        amount--;
        if(amount == 0){
            int numPlayers = controller.getModel().getFlightBoard().getTurnOrder().length;
            Player currentPlayer = controller.getModel().getFlightBoard().getTurnOrder()[0];
            for(int i = 0; i<numPlayers; i++){
                Player nextPlayer = controller.getModel().getFlightBoard().getTurnOrder()[(i+1)];
                if(nextPlayer.getShipBoard().getCondensedShip().getTotalCrew() > currentPlayer.getShipBoard().getCondensedShip().getTotalCrew()){
                    currentPlayer = nextPlayer;
                }
            }
            context.addSpecialPlayer(player);
            controller.getModel().setState(new CombatZone2CannonShotsState(context));
            controller.getModel().setError(false);
        }
        else{       //rimuovi altra batteria
            controller.getModel().setState(new SecondCombatZone2BatteryRemovalState(context, amount));
            controller.getModel().setError(false);
        }
    }
}
