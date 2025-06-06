package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Player;

public class CombatZone1EngineDeclarationState extends State {
    private Context context;
    private int worst;

    public CombatZone1EngineDeclarationState(Context context) {
        this.context = context;
        this.worst = -1;
    }

    public CombatZone1EngineDeclarationState(Context context, int worst) {
        this.context = context;
        this.worst = worst;
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount){
        Controller controller = context.getController();
        if(doubleType != DoubleType.ENGINES){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid double type, only ENGINES are allowed");
        }

        if(amount < 0){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid amount of double, only non negative integers are allowed");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not your turn to throw the dice.");
        }


        if(player.getShipBoard().getCondensedShip().getTotalBatteries() < amount || player.getShipBoard().getCondensedShip().getEngines().getDoubleEngines() < amount) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough batteries or double engines to declare.");
        }

        if(worst < 0){
            controller.getModel().setState(new CombatZone1_E_BatteryRemovalState(context, amount, 0));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new CombatZone1_E_BatteryRemovalState(context, amount, 0, worst));
            controller.getModel().setError(false);
        }
    }
}
