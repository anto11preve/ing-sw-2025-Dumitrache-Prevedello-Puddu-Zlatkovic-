package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;

import java.util.List;

public class CombatZone1PowerDeclarationState extends State {
   
    private double worst = -1;

    public CombatZone1PowerDeclarationState(Context context) {
        super(context);        
        this.setPlayerInTurn(context.getPlayers().getFirst());
    }

    public CombatZone1PowerDeclarationState(Context context, double worst) {
        super(context);
        this.worst = worst;
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, double amount) throws InvalidMethodParameters, InvalidContextualAction, InvalidParameters {

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


        int batteries = 0;
        double minPower = player.getShipBoard().getCondensedShip().getBasePower();
        double maxPower = player.getShipBoard().getCondensedShip().getMaxPower();

        if (amount < minPower || amount > maxPower) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared amount is out of bounds");
        }


        if ((amount % 1) != (minPower % 1)) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared amount must match the ship's base power decimal part");
        }

        int delta = (int) (amount - minPower);

        int frontCannons = player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons();
        int otherCannons = player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons();

        int doubleRequired = delta / 2;
        if (doubleRequired <= frontCannons) {
            batteries += doubleRequired;
            delta -= doubleRequired * 2;
        } else {
            batteries += frontCannons;
            delta -= doubleRequired * 2;
        }

        if (delta > 0) {

            if (delta <= otherCannons) {
                batteries += delta;
            } else {
                controller.getModel().setError(true);
                throw new InvalidParameters("Not enough double cannons to declare this amount");
            }

        }

        if(batteries > player.getShipBoard().getCondensedShip().getTotalBatteries()){
            controller.getModel().setError(true);
            throw new InvalidParameters("Not enough batteries to declare this amount");
        }
        if(worst < 0){
            if(amount == player.getShipBoard().getCondensedShip().getBaseThrust()){
                context.addSpecialPlayer(player);
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){
                    controller.getModel().setState(new CombatZone1CannonShotsState(context));
                    controller.getModel().setError(false);
                } else {
                    controller.getModel().setState(new CombatZone1PowerDeclarationState(context, worst));
                    controller.getModel().setError(false);
                }
            } else {
                controller.getModel().setState(new CombatZone1_P_BatteryRemovalState(context, amount, 0));
                controller.getModel().setError(false);
            }

        } else {
            if(amount == player.getShipBoard().getCondensedShip().getBaseThrust()){
                if(amount < worst){
                    if (context.getSpecialPlayers().getFirst() != null) {
                        context.removeSpecialPlayer(context.getSpecialPlayers().getFirst());
                    }
                    context.addSpecialPlayer(player);
                    worst = amount;
                }
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){
                    controller.getModel().setState(new CombatZone1CannonShotsState(context));
                    controller.getModel().setError(false);
                } else {
                    controller.getModel().setState(new CombatZone1PowerDeclarationState(context, worst));
                    controller.getModel().setError(false);
                }
            } else {
                controller.getModel().setState(new CombatZone1_P_BatteryRemovalState(context, amount, 0, worst));
                controller.getModel().setError(false);
            }

        }
    }

    public List<String> getAvailableCommands(){
        return List.of( "DeclaresDoubleCannon"
        );
    }
}
