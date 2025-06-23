package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class CombatZone1CheckShipState extends State {
    private Context context;

    public CombatZone1CheckShipState(Context context) {
        this.context = context;
        this.setPlayerInTurn(context.getPlayers().getFirst());
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
            CannonShot shot = (CannonShot) context.getProjectile(0);
            context.removeProjectile(shot);
            if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                controller.getModel().setState(new FlightPhase(controller));
                controller.getModel().setError(false);
                return;
            }
            controller.getModel().setState(new CombatZone1CannonShotsState(context));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new CombatZone1CheckShipState(context));
            controller.getModel().setError(false);
        }
    }
    //TODO: aggiungere comando
//    public void chooseShip(String playerName, Coordinates coordinates) {
//        /*  1. controlla che esista un component in quella coordinata
//            2. controlla che la nave sia davvero spezzata
//            3. identifica la nave attaccata a quel componente
//            4. tutto ciò che non è attaccato a quel componente viene rimosso, aggiungendo altrettanti scarti
//            5. fai checkShip, se è apposto vai allo stato successivo, se no rimanda questo stato
//         */
//        Controller controller = context.getController();
//        Player player = controller.getModel().getPlayer(playerName);
//        if(!player.equals(context.getPlayers().getFirst())) {
//            controller.getModel().setError(true);
//            throw new IllegalArgumentException("It's not the player's turn");
//        }
//        if(coordinates == null){
//            controller.getModel().setError(true);
//            throw new IllegalArgumentException("Coordinates cannot be null");
//        }
//        if(player.getShipBoard().checkIntegrity()){
//            controller.getModel().setError(true);
//            throw new IllegalArgumentException("The ship is not broken, cannot choose a ship");
//        }
//        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates);
//        if(component == null) {
//            controller.getModel().setError(true);
//            throw new IllegalArgumentException("No component found at the given coordinates");
//        }
//
//        //TODO: punti 3 e 4
//
//        if(player.getShipBoard().checkIntegrity()){
//            CannonShot shot = (CannonShot) context.getProjectile(0);
//            context.removeProjectile(shot);
//            if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
//                controller.getModel().setState(new FlightPhase(controller));
//                controller.getModel().setError(false);
//                return;
//            }
//            controller.getModel().setState(new CombatZone1CannonShotsState(context));
//            controller.getModel().setError(false);
//        } else {
//            controller.getModel().setState(new CombatZone1CheckShipState(context));
//            controller.getModel().setError(false);
//        }
//
//    }
}
