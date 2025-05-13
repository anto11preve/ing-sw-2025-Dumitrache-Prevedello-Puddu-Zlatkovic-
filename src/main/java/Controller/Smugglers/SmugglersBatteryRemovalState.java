package Controller.Smugglers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Enums.RewardType;
import Controller.Slavers.SlaversBatteryRemovalState;
import Model.Player;
import Model.Ship.Components.BatteryCompartment;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Controller.State;

public class SmugglersBatteryRemovalState extends State{
    private Context context;
    private int declaredPower;
    private int actualPower;

    public SmugglersBatteryRemovalState(Context context, int declaredPower, int actualPower) {
        this.context = context;
        this.declaredPower = declaredPower;
        this.actualPower = actualPower;
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates){
        if(itemType != ItemType.BATTERIES){
            return;
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        SpaceshipComponent component = player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
        if(!player.getShipBoard().getCondensedShip().getBatteryCompartments().contains(component))   //non è un Battery
            return;
        BatteryCompartment compartment = (BatteryCompartment) component;
        compartment.removeBattery();
        declaredPower--;
        actualPower++;
        if(declaredPower == 0){
            if(actualPower > context.getPower()){
                controller.setState(new SmugglersLandState(context));
            } else if(actualPower == context.getPower()){
                context.removePlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new SmugglersGoodsRemovalState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SmugglersPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }
            else{
                context.removePlayer(player);
                context.addSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new SmugglersGoodsRemovalState(context)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SmugglersPowerDeclarationState(context)); //manca qualcuno da gestire
                }
            }

        }
        else{       //rimuovi altra batteria
            controller.setState(new SlaversBatteryRemovalState(context, declaredPower, actualPower));
        }
    }

    /// nel caso uno vinca solo con la base power
    @Override
    public void getReward(String playerName, RewardType rewardType){
        if(rewardType != RewardType.CREDITS){
            return;
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0])
            return; // Handle the case where it's not the player's turn
        if(actualPower > context.getPower() && declaredPower == 0){
            controller.setState(new SmugglersLandState(context));
        }

    }
}
