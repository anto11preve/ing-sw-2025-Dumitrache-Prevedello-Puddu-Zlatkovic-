package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Enums.Side;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class CombatZone1ManageShotState extends State {
    private final Context context;
    private final int number; //TODO: non controlla se il numero è valido o se è out of bounds, occhio perchè getComponent ritorna null se non trova il componente
    boolean hit = false;

    public CombatZone1ManageShotState(Context context,  int number) {
        this.context = context;
        this.number = number;
    }

    @Override
    public void end(String playerName) throws InvalidMethodParameters, InvalidParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != context.getSpecialPlayers().getFirst()) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        CannonShot shot = (CannonShot) context.getProjectile(0);
        if (shot == null) {
            controller.getModel().setError(true);
            throw new InvalidParameters("The shot is null");
        }
        SpaceshipComponent component = null;

        switch (shot.getSide()) {
            case Side.FRONT:   //arriva da davanti
                for (int i = 5; (i <= 9)&&(!hit); i++) {
                    Coordinates coordinates = new Coordinates(number, i); //TODO: mi sa che da quando ho modificato le coordinate, non funziona più, basta invertire i e number ma il bug c'era in ogni caso perchè a volte era usata in un modo a volte in un altro
                    component = player.getShipBoard().getComponent(coordinates);
                    if (component != null) {
                        hit = true;
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
            case Side.RIGHT:
                for (int i = 10; (i >= 4)&&(!hit); i--) {
                    Coordinates coordinates = new Coordinates(i, number);
                    component = player.getShipBoard().getComponent(coordinates);
                    if (component != null) {
                        hit = true;
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
            case Side.LEFT:
                for (int i = 4; (i <= 10&&(!hit)); i++) {
                    Coordinates coordinates = new Coordinates(i, number);
                    component = player.getShipBoard().getComponent(coordinates);
                    if (component != null) {
                        hit = true;
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
            case Side.REAR:
                for (int i = 0; (i <= 4&&(!hit)); i++) {
                    Coordinates coordinates = new Coordinates(number, i);
                    component = player.getShipBoard().getComponent(coordinates);
                    if (component != null) {
                        hit = true;
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
        }

        if(hit){
            boolean brokenShip = player.getShipBoard().checkIntegrity();
            if (brokenShip) {
                controller.getModel().setState(new CombatZone1CheckShipState(context));
                controller.getModel().setError(false);
                return;
            }
        }

        context.removeProjectile(shot);
        if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
            controller.getModel().setState(new FlightPhase(controller));
            controller.getModel().setError(false);
            return;
        }
        controller.getModel().setState(new CombatZone1CannonShotsState(context));
        controller.getModel().setError(false);

    }


    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates ) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if (itemType != ItemType.BATTERIES) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid item type, expected BATTERIES");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if (player != context.getSpecialPlayers().getFirst()) {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not the player's turn");
        }
        CannonShot shot = (CannonShot) context.getProjectile(0);
        if (shot == null) {
            controller.getModel().setError(true);
            throw new InvalidParameters("The shot is null");
        }
        SpaceshipComponent component = null;
        if(shot.isBig()){
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Cannot use batteries on a big shot");
        }
        boolean shieldFound = false;
        switch (shot.getSide()){
            case Side.FRONT:
                if(player.getShipBoard().getCondensedShip().getShields().getNorthShields() > 0){
                    shieldFound = true;
                }
                break;
            case Side.RIGHT:
                if(player.getShipBoard().getCondensedShip().getShields().getWestShields() > 0){
                    shieldFound = true;
                }
                break;
            case Side.LEFT:
                if(player.getShipBoard().getCondensedShip().getShields().getEastShields() > 0){
                    shieldFound = true;
                }
                break;
            case Side.REAR:
                if(player.getShipBoard().getCondensedShip().getShields().getSouthShields() > 0){
                    shieldFound = true;
                }
                break;
        }

        if(shieldFound) {
            SpaceshipComponent component2 = player.getShipBoard().getComponent(coordinates);
            if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component2)) {   //non è un Battery
                controller.getModel().setError(true);
                throw new IllegalArgumentException("The component is not a BatteryCompartment");
            }
            BatteryCompartment compartment = (BatteryCompartment) component2;
            compartment.removeBattery();

            context.removeProjectile(shot);
            if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                controller.getModel().setState(new FlightPhase(controller));
                controller.getModel().setError(false);
                return;
            }
            controller.getModel().setState(new CombatZone1CannonShotsState(context));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("No shield found to use");
        }

    }
}
