package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;

public class CombatZone1PowerDeclarationState extends State {
    private Context context;
    private int worst = -1;

    public CombatZone1PowerDeclarationState(Context context) {
        this.context = context;
    }

    public CombatZone1PowerDeclarationState(Context context, int worst) {
        this.context = context;
        this.worst = worst;
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) throws InvalidMethodParameters, InvalidContextualAction {

        Controller controller = context.getController();
        if (doubleType != DoubleType.CANNONS) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid double type, expected CANNONS");
        }

        if (amount < 0) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Negative amount");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }

        if(player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons()*2 +
                player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons() < amount){
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough cannons to declare");
        }

        if(player.getShipBoard().getCondensedShip().getTotalBatteries() < amount){
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not enough batteries to declare");
        }
        if(worst < 0){
            controller.getModel().setState(new CombatZone1_P_BatteryRemovalState(context, amount, 0));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new CombatZone1_P_BatteryRemovalState(context, amount, 0, worst));
            controller.getModel().setError(false);
        }
    }
}
