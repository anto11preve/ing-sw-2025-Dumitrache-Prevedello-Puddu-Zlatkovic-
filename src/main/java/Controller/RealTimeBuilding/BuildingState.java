package Controller.RealTimeBuilding;

import Controller.Controller;
import Controller.Enums.ComponentOrigin;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.*;
import Controller.State;
import Model.Exceptions.InvalidMethodParameters;
import Model.Game;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Board.Timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingState extends State {

    Map<Integer, Player> finishedPlayers=new HashMap<>();
    Map<Integer, List<Integer>> validCoordinates=new HashMap<>();

    public BuildingState(Controller controller) {
        super(controller);
        MatchLevel matchLevel=controller.getMatchLevel();
        if(matchLevel== MatchLevel.TRIAL){

            this.validCoordinates.put(5, List.of(7));
            this.validCoordinates.put(6, List.of(6,7,8));
            this.validCoordinates.put(7, List.of(5,6,7,8,9));
            this.validCoordinates.put(8, List.of(5,6,7,8,9));
            this.validCoordinates.put(9, List.of(5,6,8,9));
        }
        else if(matchLevel== MatchLevel.LEVEL2){

            this.validCoordinates.put(5, List.of(6,8));
            this.validCoordinates.put(6, List.of(5,6,7,8,9));
            this.validCoordinates.put(7, List.of(4,5,6,7,8,9,10));
            this.validCoordinates.put(8, List.of(4,5,6,7,8,9,10));
            this.validCoordinates.put(9, List.of(4,5,6,8,9,10));
        }
        else{
            throw new IllegalArgumentException("Invalid match level");
        }

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
                throw new InvalidParameters("Player not found, strange bug!!!");
            }
            if (finishedPlayers.containsValue(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            if (index < 0 || index >= this.getController().getModel().getTiles().length) {
                throw new InvalidParameters("Invalid index");
            }

            SpaceshipComponent selectedTile = this.getController().getModel().getTiles()[index];
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
            if (finishedPlayers.containsValue(currentPlayer)) {
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
        Game model= this.getController().getModel();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController()));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }
            if (finishedPlayers.containsValue(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            if(!validCoordinates.containsKey(coordinates.getX()) || !validCoordinates.get(coordinates.getX()).contains(coordinates.getY())){
                throw new InvalidParameters("Invalid coordinates");
            }
            if(currentPlayer.getShipBoard().getComponent(coordinates)!=null){
                throw new InvalidParameters("Coordinates already occupied");
            }



            /*
            must be careful that when placing a element from reserved active tile goes back to the table
            Before doing anything the orientation must be setted,
             */

            SpaceshipComponent activeTile;
            List<SpaceshipComponent> reservedComponents = currentPlayer.getShipBoard().getReservedComponents();
            SpaceshipComponent activeComponent = currentPlayer.getShipBoard().getActiveComponent();

            switch (origin) {
                case HAND:
                activeTile= activeComponent;
                    if (activeTile == null) {
                        throw new InvalidCommand("No active component");
                    }
                    currentPlayer.getShipBoard().setActiveComponent(null);
                    break;

                case FIRST_RESERVED:
                    if (reservedComponents.isEmpty()) {
                        throw new InvalidCommand("No active component");
                    }

                    activeTile= currentPlayer.getShipBoard().getReservedComponents().getFirst();
                    currentPlayer.getShipBoard().removeReservedComponent(activeTile);

                    model.addComponent(activeComponent);
                    currentPlayer.getShipBoard().setActiveComponent(null);

                    break;

                case SECOND_RESERVED:
                    if (reservedComponents.size()<2) {
                        throw new InvalidCommand("No active component");
                    }

                    activeTile= currentPlayer.getShipBoard().getReservedComponents().get(1);
                    currentPlayer.getShipBoard().removeReservedComponent(activeTile);

                    model.addComponent(activeComponent);
                    currentPlayer.getShipBoard().setActiveComponent(null);

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + origin);


            }
            activeTile.setOrientation(orientation);
            activeTile.setShipBoard(currentPlayer.getShipBoard());
            activeTile.added();
            currentPlayer.getShipBoard().addComponent(activeTile, coordinates);

        }
    }
    public void lookDeck(String name, int index) throws InvalidCommand, InvalidParameters {

        if(this.getController().getMatchLevel()==MatchLevel.TRIAL){
            throw new InvalidCommand("Trial deck");
        }

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
            if (finishedPlayers.containsValue(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
            model.addComponent(oldTile);
            currentPlayer.getShipBoard().setActiveComponent(null);






        }

    }
    public void flipHourGlass(String name) throws InvalidCommand, InvalidParameters {

        if(this.getController().getMatchLevel()==MatchLevel.TRIAL){
            throw new InvalidCommand("Trial deck");
        }

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

            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
            model.addComponent(oldTile);
            currentPlayer.getShipBoard().setActiveComponent(null);

            if(timer.getTimeLeft()!=0.0f){

                throw new InvalidCommand("Hourglass not finished");
            }
            if(timer.getPhase()== Timer.Phase.MIDDLE_PHASE && !finishedPlayers.containsValue(currentPlayer)){
                throw new InvalidCommand("Last HourGlass can be flipped only by players that already finished building");
            }

            timer.nextPhase();



        }

    }
    public void finishBuilding(String name, int position) throws InvalidCommand, InvalidParameters {
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
            if (finishedPlayers.containsValue(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
            model.addComponent(oldTile);
            currentPlayer.getShipBoard().setActiveComponent(null);

            if(finishedPlayers.containsKey(position)){
                throw new InvalidParameters("Position already occupied");
            }


            int penalty=currentPlayer.getShipBoard().getReservedComponents().size();
            for(int i=0; i<penalty; i++){
                currentPlayer.addJunk();
            }

            finishedPlayers.put(position, currentPlayer);
            try {
                model.getFlightBoard().setStartingPositions(currentPlayer, position);
            } catch (InvalidMethodParameters e) {
                throw new InvalidParameters("Invalid starting position, must be btween 1 and 4");
            }

            if(finishedPlayers.size()==model.getPlayers().size()){
                this.getController().setState(new PlaceAlienState(this.getController()));
            }


        }

    }
}
