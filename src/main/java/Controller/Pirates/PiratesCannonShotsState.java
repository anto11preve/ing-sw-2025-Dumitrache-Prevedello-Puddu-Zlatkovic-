package Controller.Pirates;

import Controller.Controller;
import Controller.State;
import Model.Player;
import Controller.Context;

import java.util.Random;

public class PiratesCannonShotsState extends State{
    private Context context;

    public PiratesCannonShotsState(Context context) {
        this.context = context;
    }

    @Override
    public void throwDices(String playerName) {
        if(context.getProjectiles.isEmpty()) {
            return; // Handle the case where there are no projectiles
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player == controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }
        Random rand = new Random();
        int dado1 = rand.nextInt(6) + 1; // numero tra 1 e 6
        int dado2 = rand.nextInt(6) + 1; // numero tra 1 e 6
        int row = dado1 + dado2;

        dado1 = rand.nextInt(6) + 1; // numero tra 1 e 6
        dado2 = rand.nextInt(6) + 1; // numero tra 1 e 6
        int column = dado1 + dado2;

        for(Player p : controller.getModel().getPlayers()){
            context.addSpecialPlayer(p);
        }
        controller.setState(new PiratesManageShotState(context, row, column));

    }
}
