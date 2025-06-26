package Controller.CombatZone.Level_TWO;

import Controller.CombatZone.Level_ONE.CombatZone1CannonShotsState;
import Controller.CombatZone.Level_ONE.CombatZone1CheckShipState;
import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidParameters;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

import java.util.List;

public class CombatZone2CheckShipState extends State {
   

    public CombatZone2CheckShipState(Context context) {
        super(context);
        this.setPlayerInTurn(context.getSpecialPlayers().getFirst());
    }

    @Override
    public void deleteComponent(String playerName, Coordinates coordinates) throws InvalidMethodParameters, InvalidParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(!player.equals(context.getSpecialPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not the player's turn");
        }
        if(coordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Coordinates cannot be null");
        }
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
        if(component == null) {
            controller.getModel().setError(true);
            throw new InvalidParameters("No component found at the given coordinates");
        }

        player.getShipBoard().removeComponent(coordinates);
        player.addJunk();

        if(player.getShipBoard().checkIntegrity()){
            CannonShot shot = (CannonShot) context.getProjectile(0);
            context.removeProjectile(shot);
            if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                controller.getModel().setState(new FlightPhase(controller));
                controller.getModel().setError(false);
                return;
            }
            controller.getModel().setState(new CombatZone2CannonShotsState(context));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new CombatZone2CheckShipState(context));
            controller.getModel().setError(false);
        }
    }

    public List<String> getAvailableCommands(){
        return List.of(
            "deleteComponent",
            "useItem"
        );
    }
}
