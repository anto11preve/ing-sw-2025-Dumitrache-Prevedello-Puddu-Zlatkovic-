package Controller.CombatZone.Level_TWO;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;

public class CombatZone2PowerDeclarationState extends State {
    private Context context;

    public CombatZone2PowerDeclarationState(Context context) {
        this.context = context;
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) throws InvalidMethodParameters, InvalidContextualAction, InvalidParameters {

        Controller controller = context.getController();
        if (doubleType != DoubleType.CANNONS) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid double type, expected CANNONS");
        }

        if (amount < 0) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Negative amount");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not the player's turn");
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
        controller.getModel().setState(new CombatZone2_P_BatteryRemovalState(context, amount, 0));
        controller.getModel().setError(false);
    }
}
