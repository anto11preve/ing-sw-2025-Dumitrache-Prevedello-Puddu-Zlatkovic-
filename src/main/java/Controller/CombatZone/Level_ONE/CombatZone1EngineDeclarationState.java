package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Player;

public class CombatZone1EngineDeclarationState extends State {
    private final Context context;
    private final double worst;

    public CombatZone1EngineDeclarationState(Context context) {
        this.context = context;
        this.worst = -1;
        this.setPlayerInTurn(context.getPlayers().getFirst());
    }

    public CombatZone1EngineDeclarationState(Context context, double worst) {
        this.context = context;
        this.worst = worst;
        this.setPlayerInTurn(context.getPlayers().getFirst());
    }

    @Override
    public void onEnter() {
        Controller controller = context.getController();
        if(controller.getModel().getFlightBoard().getTurnOrder().length == 1){
            controller.getModel().setState(new FlightPhase(controller));
            controller.getModel().setError(false);
        }
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, double amount) throws InvalidContextualAction, InvalidParameters {
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


        int batteries = 0;
        double minPower = player.getShipBoard().getCondensedShip().getBaseThrust();
        double maxPower = player.getShipBoard().getCondensedShip().getMaxThrust();

        if (amount < minPower || amount > maxPower) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared amount is out of bounds");
        }


        if ((amount % 1) != (minPower % 1)) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Declared amount must match the ship's base power decimal part");
        }

        int delta = (int) (amount - minPower);

        if(delta % 2 != 0) {
            controller.getModel().setError(true);
            throw new InvalidParameters("Cannot reach the declared amount with double engines");
        }

        int doubleRequired = delta / 2;

        if(player.getShipBoard().getCondensedShip().getEngines().getDoubleEngines()>=doubleRequired){
            batteries = doubleRequired;
        } else {
            controller.getModel().setError(true);
            throw new InvalidParameters("Not enough double engines to declare this amount");
        }

        if(worst < 0){
            controller.getModel().setState(new CombatZone1_E_BatteryRemovalState(context, amount, batteries));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new CombatZone1_E_BatteryRemovalState(context, amount, batteries, worst));
            controller.getModel().setError(false);
        }
    }
}
