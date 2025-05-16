package Controller.MeteorsSwarm;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.GamePhases.FlightPhase;
import Controller.Pirates.PiratesCannonShotsState;
import Controller.Pirates.PiratesCheckShipState;
import Controller.Pirates.PiratesManageShotState;
import Controller.State;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.Side;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class ManageMeteorState extends State {
    private Context context;
    private int row;
    private int column;
    int turn;   ///Turno del giocatore speciale che dve subire il meteorite
    boolean hit = false;

    public ManageMeteorState(Context context, int row, int column, int turn) {
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
        Meteor meteor = (Meteor) context.getProjectile(0);
        if (meteor == null) {
            return; // Handle the case where there are no projectiles
        }
        SpaceshipComponent component = null;
        //è un colpo grosso
        switch (meteor.getSide()) {
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
        boolean cannonFound = false;
        if(hit){
            if(meteor.isBig()){

                switch (meteor.getSide()){
                    case Side.FRONT:
                        for (int i = 5; i <= 9; i++){
                            component = player.getShipBoard().getComponent(i, row);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                    case Side.RIGHT:
                        for (int i = 10; i >= 4; i--){
                            component = player.getShipBoard().getComponent(i, row);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, row + 1);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, row - 1 );
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                    case Side.LEFT:
                        for (int i = 4; i <= 10; i++){
                            component = player.getShipBoard().getComponent(i, row);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, row + 1);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, row - 1 );
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                    case Side.REAR:
                        for (int i = 0; i <= 4; i++){
                            component = player.getShipBoard().getComponent(column, i);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(column + 1, i);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(column - 1, i);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                }
            } else {
                if(component.getSide(meteor.getSide()) ==null ){   //rimbalza su lato liscio
                    hit = false;
                }

                if(hit && !cannonFound){
                    boolean brokenShip = player.getShipBoard().checkIntegrity();
                    if (brokenShip) {
                        controller.setState(new MeteorsCheckShipState(context));
                        return;
                    }
                }
                turn++;
                if (turn > context.getSpecialPlayers().size()) {  //tutti i giocatori sono stati colpiti da questo shot
                    context.removeProjectile(meteor);
                    if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                        controller.setState(new FlightPhase());
                        return;
                    }
                    controller.setState(new MeteorsState(context));
                    return;
                } else {
                    controller.setState(new ManageMeteorState(context, row, column, turn));
                    return;
                }


            }
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
        Meteor meteor = (Meteor) context.getProjectile(0);
        if (meteor == null) {
            return; // Handle the case where there are no projectiles
        }
        SpaceshipComponent component = null;

        boolean cannonOrShieldFound = false;
        switch (meteor.getSide()){
            case Side.FRONT:
                if(meteor.isBig()){
                    for (int i = 5; i <= 9; i++){
                        component = player.getShipBoard().getComponent(i, row);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getNorthShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }

                break;
            case Side.RIGHT:
                if(meteor.isBig()){
                    for (int i = 10; i >= 4; i--){
                        component = player.getShipBoard().getComponent(i, row);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getNorthShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }
                break;
            case Side.LEFT:
                if(meteor.isBig()){
                    for (int i = 4; i <= 10; i++){
                        component = player.getShipBoard().getComponent(i, row);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getNorthShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }
                break;
            case Side.REAR:
                if(meteor.isBig()){
                    for (int i = 0; i <= 4; i++){
                        component = player.getShipBoard().getComponent(i, row);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getNorthShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }
                break;
        }

        if(cannonOrShieldFound) {
            SpaceshipComponent component2 = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
            if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component2))   //non è un Battery
                return;
            BatteryCompartment compartment = (BatteryCompartment) component2;
            compartment.removeBattery();

            turn++;
            if (turn > context.getSpecialPlayers().size()) {  //tutti i giocatori sono stati colpiti da questo shot
                context.removeProjectile(meteor);
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
