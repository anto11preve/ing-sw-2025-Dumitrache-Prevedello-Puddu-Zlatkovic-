package Controller.Pirates;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.DoubleType;
import Controller.Slavers.SlaversBatteryRemovalState;
import Controller.Slavers.SlaversCrewRemovalState;
import Controller.Slavers.SlaversPowerDeclarationState;
import Model.Player;
import Controller.State;

public class PiratesPowerDeclarationState extends State {
    Context context;

    public PiratesPowerDeclarationState(Context context) {
        this.context = context;
    }

    @Override
    public void declaresDouble(String playerName, DoubleType doubleType, int amount) {
        if (doubleType != DoubleType.CANNONS) {
            return;
        }

        if (amount < 0) {
            return; // Invalid amount
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if (player != controller.getModel().getFlightBoard().getTurnOrder()[0]) {
            return; // Handle the case where it's not the player's turn
        }

        if(player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getFrontCannons()*2 +
                player.getShipBoard().getCondensedShip().getTotalDoubleCannons().getOtherCannons() < amount){
            return; // Not enough cannons to declare
        }

        if(player.getShipBoard().getCondensedShip().getTotalBatteries() < amount){
            return; // Not enough batteries to declare
        }

        int basePower = player.getShipBoard().getBasePower();

        if(context.getPower() > (basePower + amount)){
            if(context.getSpecialPlayers().contains(player)){
                return; // Player already declared power
            }
            context.addSpecialPlayer(player);
            context.removePlayer(player);
            if(context.getPlayers().isEmpty()){
                controller.setState(new PiratesCannonShotsState(context));  //tutti i giocatori gestiti
            }else{
                controller.setState(new PiratesPowerDeclarationState(context)); //manca qualcuno da gestire
            }
        }else{
            controller.setState(new PiratesBatteryRemovalState(context, amount, basePower)); //rimuovi batteria
        }
    }
}
