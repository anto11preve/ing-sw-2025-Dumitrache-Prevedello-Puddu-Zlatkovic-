package Controller.CombatZone.Level_TWO;

import Controller.Context;
import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.GamePhases.FlightPhase;
import Controller.Smugglers.SecondSmugglersBatteryRemovalState;
import Controller.Smugglers.SmugglersGoodsRemovalState;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Enums.Good;
import Model.Player;
import Model.Ship.Components.CargoHold;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Ship.GoodCounter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombatZone2GoodsRemovalState extends State {

    private GoodCounter goodCounter;

    private int amount;


    public CombatZone2GoodsRemovalState(Context context) {
        super(context);
        this.amount = context.getRequiredGoods();
        this.setPlayerInTurn(context.getSpecialPlayers().getFirst());
    }

    public CombatZone2GoodsRemovalState(Context context, int amount) {
        super(context);
        this.amount = amount;
        this.setPlayerInTurn(context.getSpecialPlayers().getFirst());
    }

    @Override
    public void onEnter(){
        Controller controller = context.getController();
        Player player = context.getSpecialPlayers().getFirst();
        boolean availableGoods = false;
        for (CargoHold cargoHold : player.getShipBoard().getCondensedShip().getCargoHolds()) {
            if (!cargoHold.isEmpty()) {
                availableGoods = true;
                break;
            }
        }
        if(!availableGoods){
            if(player.getShipBoard().getCondensedShip().getTotalBatteries() > 0){
                controller.getModel().setState(new SecondCombatZone2BatteryRemovalState(context, amount));
                controller.getModel().setError(false);
            } else {
                context.removeSpecialPlayer(player);
                if(context.getSpecialPlayers().isEmpty()){
                    controller.getModel().setState(new FlightPhase(controller));
                    controller.getModel().setError(false);
                } else {
                    controller.getModel().setState(new CombatZone2GoodsRemovalState(context));
                    controller.getModel().setError(false);
                }
            }
        }
    }

    @Override
    public void moveGood(String name, Coordinates oldCoordinates, Coordinates newCoordinates, int oldIndex, int newIndex) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(name);
        if(!player.equals(context.getSpecialPlayers().getFirst())) {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn");
        }

        if(oldCoordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid coordinates");
        }

        if(oldIndex < 0){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid index");
        }

        GoodCounter goodCounter = player.getShipBoard().getCondensedShip().goodToDiscard(amount);
        if(goodCounter.getRed() + goodCounter.getBlue() + goodCounter.getGreen() + goodCounter.getYellow() == 0){       //non ha abbastanza goods da scartare
            if(player.getShipBoard().getCondensedShip().getTotalBatteries() > 0){   //se almeno ha delle batterie
                controller.getModel().setState(new SecondCombatZone2BatteryRemovalState(context, amount));
                controller.getModel().setError(false);
            } else {    //se no non gli succede niente

                int numPlayers = controller.getModel().getFlightBoard().getTurnOrder().length;
                Player currentPlayer = controller.getModel().getFlightBoard().getTurnOrder()[0];
                for(int i = 0; i<numPlayers; i++){
                    Player nextPlayer = controller.getModel().getFlightBoard().getTurnOrder()[(i+1)];
                    if(nextPlayer.getShipBoard().getCondensedShip().getTotalCrew() > currentPlayer.getShipBoard().getCondensedShip().getTotalCrew()){
                        currentPlayer = nextPlayer;
                    }
                }
                context.addSpecialPlayer(player);
                controller.getModel().setState(new CombatZone2CannonShotsState(context));
                controller.getModel().setError(false);


            }
            return;
        }

        SpaceshipComponent component = player.getShipBoard().getComponent(oldCoordinates);

        if(component == null || !player.getShipBoard().getCondensedShip().getCargoHolds().contains(component)) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Not a valid cargo hold");
        }
        CargoHold cargoHold = (CargoHold) component;
        Good selectedGood = cargoHold.getGoods()[oldIndex];
        if(selectedGood == null) {
            controller.getModel().setError(true);
            throw new InvalidParameters("The selected good is not found");
        }
        boolean done = goodCounter.removeGood(selectedGood);
        if(!done) {
            controller.getModel().setError(true);
            throw new InvalidContextualAction("Need to remove another type of good");
        }
        cargoHold.removeGood(oldIndex);
        amount--;
        if(amount == 0){
            List<Player> allPlayers= new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
            context.setPlayers(allPlayers);
            controller.getModel().setState(new CombatZone2CannonShotsState(context));
            controller.getModel().setError(false);
        } else {
            controller.getModel().setState(new CombatZone2GoodsRemovalState(context, amount));
            controller.getModel().setError(false);
        }
    }

    public List<String> getAvailableCommands(){
        return List.of( "MoveGood");
    }
}
