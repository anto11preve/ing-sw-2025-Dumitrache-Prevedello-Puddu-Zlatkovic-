package Controller.RealTimeBuilding;

import Controller.Controller;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.*;
import Controller.State;
import Model.Exceptions.InvalidMethodParameters;
import Model.Game;
import Model.Player;

import java.util.Map;

public class HourGlassFinishedState extends State {

    Map<Integer, Player> finishedPlayers;

    public HourGlassFinishedState(Controller controller, Map<Integer, Player> finishedPlayerss) {
        super(controller);
        this.finishedPlayers = finishedPlayerss;
    }

    public void finishBuilding(String name, int position) throws InvalidCommand, InvalidParameters {
        Game model= this.getController().getModel();

        Player currentPlayer = model.getPlayer(name);
        if (currentPlayer == null) {
            throw new InvalidParameters("Player not found");
        }
        if (finishedPlayers.containsValue(currentPlayer)) {
            throw new InvalidCommand("Player already finished");
        }

//        if(this.getController().getMatchLevel()== MatchLevel.TRIAL){
//            if(!currentPlayer.getShipBoard().validateShip()){
//                throw new InvalidCommand("Ship not valid");
//            }
//        }
//  non dovrebe mai trovarsi in questo stato se è in trial

//        SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
//        model.addComponent(oldTile);
//        currentPlayer.getShipBoard().setActiveComponent(null);


        if(finishedPlayers.containsKey(position)){
            throw new InvalidParameters("Position already occupied");
        }

        try {
            model.getFlightBoard().setStartingPositions(currentPlayer, position);
        } catch (InvalidMethodParameters e) {
            throw new InvalidParameters("Invalid starting position, must be btween 1 and 4");
        }

        int penalty=currentPlayer.getShipBoard().getReservedComponents().size();
        for(int i=0; i<penalty; i++){
            currentPlayer.addJunk();
        }

        finishedPlayers.put(position, currentPlayer);

        if(finishedPlayers.size()==model.getPlayers().size()){
            this.getController().setState(new FixShipState(this.getController()));
        }


    }
}
