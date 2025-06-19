package Controller.Pirates;

import Controller.CombatZone.Level_TWO.CombatZone2CannonShotsState;
import Controller.CombatZone.Level_TWO.CombatZone2CheckShipState;
import Controller.Context;
import Controller.Controller;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

//TODO: cambiare firma
public class PiratesCheckShipState extends State {
    private Context context;
    int number;
    int turn;

    /// Turno del giocatore speciale che dve subire la cannonata

    public PiratesCheckShipState(Context context, int number, int turn) {
        this.context = context;
        this.number = number;
        this.turn = turn;
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates) throws InvalidMethodParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (!player.equals(context.getPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        if (coordinates == null) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if (component == null) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("No component found at the given coordinates");
        }

        player.getShipBoard().removeComponent(coordinates);
        player.addJunk();

        if (player.getShipBoard().checkIntegrity()) {
            CannonShot shot = (CannonShot) context.getProjectile(0);
            turn++;
            if (turn > context.getSpecialPlayers().size()) {  //tutti i giocatori sono stati colpiti da questo shot
                context.removeProjectile(shot);
                if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                    controller.getModel().setState(new FlightPhase(controller));
                    controller.getModel().setError(false);
                    return;
                }
                controller.getModel().setState(new PiratesCannonShotsState(context));
                controller.getModel().setError(false);
            } else {
                controller.getModel().setState(new PiratesManageShotState(context, number, turn));
                controller.getModel().setError(false);
            }
        }
    }

}
