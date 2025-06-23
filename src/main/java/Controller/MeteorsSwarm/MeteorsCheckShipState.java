package Controller.MeteorsSwarm;

import Controller.CombatZone.Level_ONE.CombatZone1CannonShotsState;
import Controller.CombatZone.Level_ONE.CombatZone1CheckShipState;
import Controller.Context;
import Controller.Controller;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

//TODO: correggere firma
public class MeteorsCheckShipState extends State {
    private Context context;
    private final int number;

    public MeteorsCheckShipState(Context context, int number) {
        this.context = context;
        this.number = number;
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates) throws InvalidMethodParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        if(coordinates == null){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("No component found at the given coordinates");
        }

        player.getShipBoard().removeComponent(coordinates);
        player.addJunk();

        if(player.getShipBoard().checkIntegrity()){
            Meteor meteor = (Meteor) context.getProjectile(0);
            context.removeSpecialPlayer(player);
            if (context.getSpecialPlayers().isEmpty()) {  //tutti i giocatori sono stati colpiti da questo shot
                context.removeProjectile(meteor);
                if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                    controller.getModel().setState(new FlightPhase(controller));
                    controller.getModel().setError(false);
                } else {
                    controller.getModel().setState(new MeteorsState(context));
                    controller.getModel().setError(false);
                }
            } else {
                controller.getModel().setState(new ManageMeteorState(context,number));
                controller.getModel().setError(false);
            }
        } else {
            controller.getModel().setState(new MeteorsCheckShipState(context, number));
            controller.getModel().setError(false);
        }
    }
}
