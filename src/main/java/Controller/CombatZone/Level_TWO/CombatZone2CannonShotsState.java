package Controller.CombatZone.Level_TWO;

import Controller.CombatZone.Level_ONE.CombatZone1CannonShotsState;
import Controller.CombatZone.Level_ONE.CombatZone1ManageShotState;
import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Projectiles.Projectile;
import Model.Enums.Side;
import Model.Player;

import java.util.Random;

public class CombatZone2CannonShotsState extends State {
    private final Context context;

    public CombatZone2CannonShotsState(Context context) {
        this.context = context;
        this.setPlayerInTurn(context.getPlayers().getFirst());
    }

    @Override
    public void throwDices(String playerName) throws InvalidContextualAction {
        Controller controller = context.getController();
        if(context.getProjectiles().isEmpty()) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("No projectiles available for cannon shots.");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        Random rand = new Random();
        int dado1 = controller.getModel().rollDice(); // numero tra 1 e 6
        int dado2 = controller.getModel().rollDice(); // numero tra 1 e 6
        int number = dado1 + dado2;

        for(Player p : controller.getModel().getFlightBoard().getTurnOrder()){
            context.addSpecialPlayer(p);
        }

        Projectile shot = context.getProjectiles().getFirst();
        boolean out = false;
        if(shot.getSide() == Side.LEFT || shot.getSide() == Side.RIGHT) {
            if(number > 9 || number < 5){      //fuori dalla griglia
                out = true;
            }
        } else {
            if(number > 10 || number < 4){     //fuori dalla griglia
                out = true;
            }
        }
        if(out) {
            context.removeProjectile(shot);
            if(context.getProjectiles().isEmpty()) {
                controller.getModel().setState(new FlightPhase(controller));
                controller.getModel().setError(false);
            } else {
                controller.getModel().setState(new CombatZone2CannonShotsState(context));
                controller.getModel().setError(false);
            }
        } else {
            controller.getModel().setState(new CombatZone2ManageShotState(context, number));
            controller.getModel().setError(false);
        }

    }
}
