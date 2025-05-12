package Controller.RealTimeBuilding;

import Controller.Controller;
import Controller.Enums.ComponentOrigin;
import Controller.Exceptions.*;
import Controller.State;
import Model.Exceptions.InvalidMethodParameters;
import Model.Game;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Board.Timer;

import java.util.ArrayList;
import java.util.List;

public class BuildingState extends State {

    List<Player> finishedPlayers=new ArrayList<Player>();

    public BuildingState(Controller controller) {
        super(controller);
    }

    @Override
    public void getComponent(String name, int index) throws InvalidCommand, InvalidParameters {
        Game model= this.getController().getModel();
        Timer timer= model.getFlightBoard().getTimer();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController()));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }
            if (finishedPlayers.contains(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            if (index < 0 || index >= this.getController().getModel().getTiles().length) {
                throw new InvalidParameters("Invalid index");
            }

            SpaceshipComponent selectedTile =this.getController().getModel().getTiles()[index];
            if(selectedTile == null) {
                throw new InvalidParameters("Component not found");
            }
            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
            model.addComponent(oldTile);
            currentPlayer.getShipBoard().setActiveComponent(selectedTile);
        }


    }

    public void reserveComponent(String name) throws InvalidCommand, InvalidParameters {
        Timer timer= this.getController().getModel().getFlightBoard().getTimer();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController()));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }
            if (finishedPlayers.contains(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            SpaceshipComponent selectedTile = currentPlayer.getShipBoard().getActiveComponent();
            if (selectedTile == null) {
                throw new InvalidCommand("Component not found");
            }
            try {
                currentPlayer.getShipBoard().reserveComponent(selectedTile);
                currentPlayer.getShipBoard().setActiveComponent(null);
            } catch (InvalidMethodParameters e) {
                throw new InvalidCommand("Already reserved 2 tiles");
            }
        }
    }

    public void placeComponent(String name, ComponentOrigin origin, Coordinates coordinates, int orientation) throws InvalidCommand, InvalidParameters {
        Timer timer= this.getController().getModel().getFlightBoard().getTimer();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController()));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }
            if (finishedPlayers.contains(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }

            switch (origin) {
                case HAND:
                SpaceshipComponent activeTile= currentPlayer.getShipBoard().getActiveComponent();
                    if (activeTile == null) {
                        throw new InvalidCommand("No active component");
                    }
                    break;

                case FIRST_RESERVED:

                    break;

                case SECOND_RESERVED:

                    break;


            }
        }
    }
    public void lookDeck(String name, int index) throws InvalidCommand, InvalidParameters {

    }
    public void flipHourGlass(String name) throws InvalidCommand, InvalidParameters {

    }
    public void finishBuilding(String name, int position) throws InvalidCommand, InvalidParameters {

    }
}
