package Controller.Pirates;
import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Enums.ConnectorType;
import Model.Enums.Side;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class PiratesManageShotState extends State{
    Context context;
    int row;
    int column;
    int turn;   ///Turno del giocatore speciale che dve subire la cannonata
    boolean hit = false;

    public PiratesManageShotState(Context context, int row, int column, int turn) {
        this.context = context;
        this.row = row;
        this.column = column;
        this.turn = turn;
    }

    @Override
    public void end(String playerName) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != context.getSpecialPlayers().get(0)) {
            return; // Handle the case where it's not the player's turn
        }
        CannonShot shot = (CannonShot) context.getProjectile(0);
        if (shot == null) {
            return; // Handle the case where there are no projectiles
        }
        SpaceshipComponent component = null;

        switch (shot.getSide()) {
            case Side.FRONT:   //arriva da davanti
                for (int i = 5; i <= 9; i++) {
                    component = player.getShipBoard().getComponent(column, i);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(column, i);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
            case Side.RIGHT:
                for (int i = 10; i >= 4; i--) {
                    component = player.getShipBoard().getComponent(i, row);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(i, row);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
            case Side.LEFT:
                for (int i = 4; i <= 10; i++) {
                    component = player.getShipBoard().getComponent(i, row);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(i, row);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
            case Side.REAR:
                for (int i = 0; i <= 4; i++) {
                    component = player.getShipBoard().getComponent(column, i);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(column, i);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
        }

        if(hit){
            boolean brokenShip = player.getShipBoard().checkIntegrity();
            if (brokenShip) {
                controller.setState(new PiratesCheckShipState(context));
                return;
            }
        }
        turn++;
        if (turn > context.getSpecialPlayers().size()) {  //tutti i giocatori sono stati colpiti da questo shot
            context.removeProjectile(shot);
            if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                controller.setState(new FlightPhase());
                return;
            }
            controller.setState(new PiratesCannonShotsState(context));
            return;
        } else {
            controller.setState(new PiratesManageShotState(context, row, column, turn));
            return;
        }

    }
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates ){
        if (itemType != ItemType.BATTERIES) {
            return; // Handle the case where the item is not a cannon shot
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != context.getSpecialPlayers().get(0)) {
            return; // Handle the case where it's not the player's turn
        }
        CannonShot shot = (CannonShot) context.getProjectile(0);
        if (shot == null) {
            return; // Handle the case where there are no projectiles
        }
        SpaceshipComponent component = null;
        if(shot.isBig()){
            return; //non lo puoi parare bro...
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
            SpaceshipComponent component2 = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
            if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component2))   //non è un Battery
                return;
            BatteryCompartment compartment = (BatteryCompartment) component2;
            compartment.removeBattery();

            turn++;
            if (turn > context.getSpecialPlayers().size()) {  //tutti i giocatori sono stati colpiti da questo shot
                context.removeProjectile(shot);
                if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                    controller.setState(new FlightPhase());
                    return;
                }
                controller.setState(new PiratesCannonShotsState(context));
                return;
            } else {
                controller.setState(new PiratesManageShotState(context, row, column, turn));
                return;
            }
        } else {
            return; //sta cercando di usare una batteria ma sarebbe sprecata non ha cannoni doppi o schudi
        }

    }

}
