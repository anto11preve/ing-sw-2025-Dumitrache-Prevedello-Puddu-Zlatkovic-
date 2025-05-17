package Controller.MeteorsSwarm;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.GamePhases.FlightPhase;
import Controller.State;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Enums.Side;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.Cannon;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

/**
 * Game state that handles the impact of a meteor on a player's spaceship.
 * This state checks if a component of the ship is hit, whether it is protected
 * by cannons or shields, and processes any resulting damage.
 */
public class ManageMeteorState extends State {
    /**
     * The context of the game, which contains the current state and player information.
     */
    private Context context;

    /** The row or column index (depending on the meteor's direction) targeted by the meteor. */
    private int number;

    /** Flag indicating whether a component of the ship has been hit. */
    private boolean hit = false;

    /**
     * Constructs a new ManageMeteorState.
     *
     * @param context the game context
     * @param number the index (row or column) that the meteor will impact
     */
    public ManageMeteorState(Context context, int number) {
        this.context = context;
        this.number = number;
    }

    /**
     * Handles the logic when the player decides not to do anything during their turn.
     * It checks whether the meteor hits any component of the ship, processes
     * the damage, and transitions to the next appropriate game state.
     *
     * @param playerName the name of the player whose turn is ending
     */
    @Override
    public void end(String playerName) {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != context.getSpecialPlayers().getFirst()) {
            return; // Handle the case where it's not the player's turn
        }

        Meteor meteor = (Meteor) context.getProjectile(0);
        if (meteor == null) {
            return; // Handle the case where there are no projectiles
        }

        SpaceshipComponent component = null;
        switch (meteor.getSide()) {
            case Side.FRONT:   //arriva da davanti
                for (int i = 5; i <= 9; i++) {
                    component = player.getShipBoard().getComponent(number, i);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(number, i);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
                break;

            case Side.RIGHT:
                for (int i = 10; i >= 4; i--) {
                    component = player.getShipBoard().getComponent(i, number);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(i, number);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
                break;

            case Side.LEFT:
                for (int i = 4; i <= 10; i++) {
                    component = player.getShipBoard().getComponent(i, number);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(i, number);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
                break;

            case Side.REAR:
                for (int i = 0; i <= 4; i++) {
                    component = player.getShipBoard().getComponent(number, i);
                    if (component != null) {
                        hit = true;
                        Coordinates coordinates = new Coordinates(number, i);
                        player.getShipBoard().removeComponent(coordinates);
                        player.addJunk();
                    }
                }
                break;

            default:
                break;
        }
        boolean cannonFound = false;

        /// if he gets hit by a meteor, check if he has a cannon that can be used to protect the ship (in case of a big meteor), or a shield (in case of a small meteor)
        if(hit){
            if(meteor.isBig()){

                switch (meteor.getSide()){
                    case Side.FRONT:
                        for (int i = 5; i <= 9; i++){
                            component = player.getShipBoard().getComponent(i, number);
                            if(component != null || player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                    case Side.RIGHT:
                        for (int i = 10; i >= 4; i--){
                            component = player.getShipBoard().getComponent(i, number);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, number + 1);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, number - 1 );
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                    case Side.LEFT:
                        for (int i = 4; i <= 10; i++){
                            component = player.getShipBoard().getComponent(i, number);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, number + 1);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(i, number - 1 );
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;
                    case Side.REAR:
                        for (int i = 0; i <= 4; i++){
                            component = player.getShipBoard().getComponent(number, i);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(number + 1, i);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                            component = player.getShipBoard().getComponent(number - 1, i);
                            if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                                Cannon cannon = (Cannon) component;
                                cannonFound = !cannon.doubleCannon();   //... e se non è doppio
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

                /// Se è grande e non si trova un cannone singolo, oppure se è piccolo e il lato non è liscio
                if(hit && ((meteor.isBig() && !cannonFound) || (!meteor.isBig() &&  (component == null || component.getSide(meteor.getSide()) ==null)))){
                    boolean brokenShip = player.getShipBoard().checkIntegrity();
                    if (brokenShip) {
                        controller.setState(new MeteorsCheckShipState(context));
                        return;
                    }
                }
                context.removeSpecialPlayer(player);
                if (context.getSpecialPlayers().isEmpty()) {  //tutti i giocatori sono stati colpiti da questo shot
                    context.removeProjectile(meteor);
                    if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                        controller.setState(new FlightPhase());
                        return;
                    }
                    controller.setState(new MeteorsState(context));
                    return;
                } else {
                    controller.setState(new ManageMeteorState(context,number));
                    return;
                }



        }

    }

    /**
     * Handles the use of an item during the meteor state. Specifically processes
     * the use of a battery to activate a double cannon or shield to block a meteor.
     *
     * @param playerName the name of the player using the item
     * @param itemType the type of item used (must be BATTERIES)
     * @param coordinates the coordinates where the item is used (must be a battery compartment)
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates ){
        if (itemType != ItemType.BATTERIES) {
            return; // Handle the case where the item is not a cannon shot
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != context.getSpecialPlayers().getFirst()) {
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
                        component = player.getShipBoard().getComponent(i, number);
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
                        component = player.getShipBoard().getComponent(i, number);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getEastShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }
                break;
            case Side.LEFT:
                if(meteor.isBig()){
                    for (int i = 4; i <= 10; i++){
                        component = player.getShipBoard().getComponent(i, number);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getWestShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }
                break;
            case Side.REAR:
                if(meteor.isBig()){
                    for (int i = 0; i <= 4; i++){
                        component = player.getShipBoard().getComponent(i, number);
                        if(player.getShipBoard().getCondensedShip().getCannons().contains(component)){  //se è un cannone...
                            Cannon cannon = (Cannon) component;
                            cannonOrShieldFound = cannon.doubleCannon();   //... e se non è doppio
                        }
                    }
                } else {
                    if(player.getShipBoard().getCondensedShip().getShields().getSouthShields() > 0){
                        cannonOrShieldFound = true;
                    }
                }
                break;

            default:
                break;
        }

        if(cannonOrShieldFound) {
            SpaceshipComponent component2 = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
            if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component2))   //non è un Battery
                return;
            BatteryCompartment compartment = (BatteryCompartment) component2;
            compartment.removeBattery();

            context.removeSpecialPlayer(player);
            if (context.getSpecialPlayers().isEmpty()) {  //tutti i giocatori sono stati colpiti da questo shot
                context.removeProjectile(meteor);
                if (context.getProjectiles().isEmpty()) {     //tutti i colpi sono stati sparati
                    controller.setState(new FlightPhase());
                    return;
                }
                controller.setState(new MeteorsState(context));
                return;
            } else {
                controller.setState(new ManageMeteorState(context, number));
                return;
            }
        } else {
            return; //sta cercando di usare una batteria ma sarebbe sprecata non ha cannoni doppi o schudi
        }
    }
}
