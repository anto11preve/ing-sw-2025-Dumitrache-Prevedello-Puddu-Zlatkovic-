package Controller.Pirates;
import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.State;
import Model.Board.AdventureCards.Projectiles.CannonShot;
import Model.Enums.Side;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;

public class PiratesManageShotState extends State{
    Context context;
    int row;
    int column;

    public PiratesManageShotState(Context context, int row, int column) {
        this.context = context;
        this.row = row;
        this.column = column;
    }

    @Override
    public void end(String playerName){
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player == context.getSpecialPlayers().get(0)) {
            return; // Handle the case where it's not the player's turn
        }
        CannonShot shot = (CannonShot) context.getProjectile(0);
        if(shot == null){
            return; // Handle the case where there are no projectiles
        }
        SpaceshipComponent component = null;
        switch (shot.isBig()) {
            case true:  //è un colpo grosso
                if(shot.getSide() == Side.FRONT){   //arriva da davanti
                    for(int i = 5; i <= 9; i++){
                        component = player.getShipBoard().getComponent(column, i);
                        if(component != null){
                            Coordinates coordinates = new Coordinates(column, i);
                            player.getShipBoard().removeComponent(coordinates);
                            player.addJunk();
                            boolean brokenShip = player.getShipBoard().checkIntegrity();
                            if(brokenShip){
                                controller.setState(new PiratesCheckShipState(context));
                            } else {        //togli component
                                context.removeSpecialPlayer(player);
                                if(context.getSpecialPlayers().isEmpty()){  //tutti i giocatori sono stati colpiti da questo shot
                                    context.removeProjectile(shot);
                                    if(context.getProjectiles().isEmpty()){

                                    }
                                    controller.setState(new PiratesEndGameState(context));
                                } else {
                                    controller.setState(new PiratesManageState(context));
                                }
                            }
                        }
                    }
                }
        }


    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates ){
        //usa batteria per scudo o cannone
    }
}
