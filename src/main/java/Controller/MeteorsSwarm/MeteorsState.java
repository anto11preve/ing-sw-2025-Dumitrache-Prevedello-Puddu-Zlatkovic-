package Controller.MeteorsSwarm;

import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;

import java.util.List;
import java.util.Random;

/**
 * Represents the game state where a meteor storm is initiated.
 *
 * <p>During this state, a player rolls dice to determine the row or column for a meteor event.
 * All players are marked as involved in the special event, and the game transitions
 * to {@link ManageMeteorState} to handle the meteor impact resolution.</p>
 */
public class MeteorsState extends State {
    Context context;

    public MeteorsState(Context context) {
        this.context = context;
    }

    @Override
    public void throwDices(String playerName) throws InvalidMethodParameters, InvalidContextualAction {
        Controller controller = context.getController();
        if(context.getProjectiles().isEmpty()) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("No projectiles available.");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidMethodParameters("It's not your turn to throw the dice.");
        }
        Random rand = new Random();
        int dado1 = rand.nextInt(6) + 1; // numero tra 1 e 6
        int dado2 = rand.nextInt(6) + 1; // numero tra 1 e 6
        int number = dado1 + dado2;

        for(Player p : controller.getModel().getPlayers()){
            context.addSpecialPlayer(p);
        }
        controller.getModel().setState(new ManageMeteorState(context, number));
        controller.getModel().setError(false);

    }

    public List<String> getAvailableCommands(){
        return List.of(
            "ThrowDices"
        );
    }
}
