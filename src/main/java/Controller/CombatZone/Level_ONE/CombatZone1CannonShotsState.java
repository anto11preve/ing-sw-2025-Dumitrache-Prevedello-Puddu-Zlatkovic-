package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Player;

import java.util.Random;

public class CombatZone1CannonShotsState extends State {
    private final Context context;

    public CombatZone1CannonShotsState(Context context) {
        this.context = context;
    }

    @Override
    public void throwDices(String playerName) {
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

        controller.getModel().setState(new CombatZone1ManageShotState(context, number));
        controller.getModel().setError(false);

    }
}
