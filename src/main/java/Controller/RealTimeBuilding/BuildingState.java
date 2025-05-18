package Controller.RealTimeBuilding;

import Controller.Controller;
import Controller.Enums.ComponentOrigin;
import Controller.Exceptions.*;
import Controller.State;
import Model.Enums.Direction;
import Model.Exceptions.InvalidMethodParameters;
import Model.Game;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Board.Timer;
import Controller.FlightPhase;

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
            this.getController().setState(new HourGlassFinishedState(this.getController(), finishedPlayers));
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

    @Override
    public void reserveComponent(String name) throws InvalidCommand, InvalidParameters {
        if(this.getController().getMatchLevel()==MatchLevel.TRIAL){
            throw new InvalidCommand("Trial deck");
        }
        Timer timer= this.getController().getModel().getFlightBoard().getTimer();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController(), finishedPlayers));
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

    @Override
    public void placeComponent(String name, ComponentOrigin origin, Coordinates coordinates, Direction orientation) throws InvalidCommand, InvalidParameters {
        Timer timer= this.getController().getModel().getFlightBoard().getTimer();
        Game model= this.getController().getModel();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController(), finishedPlayers));
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
            //if the component is not near a tile already placed it's not valid





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
                    if(!currentPlayer.getShipBoard().isConnectedToExistingComponents(activeTile, coordinates.getY()-5, coordinates.getX()-4)){
                        throw new InvalidParameters("Invalid position, must be connected to existing components");
                    }
                    currentPlayer.getShipBoard().setActiveComponent(null);
                    break;

                case FIRST_RESERVED:
                    if (reservedComponents.isEmpty()) {
                        throw new InvalidCommand("No active component");
                    }

                    activeTile= currentPlayer.getShipBoard().getReservedComponents().getFirst();
                    if(!currentPlayer.getShipBoard().isConnectedToExistingComponents(activeTile, coordinates.getY()-5, coordinates.getX()-4)){
                        throw new InvalidParameters("Invalid position, must be connected to existing components");
                    }
                    currentPlayer.getShipBoard().removeReservedComponent(activeTile);

                    model.addComponent(activeComponent);
                    currentPlayer.getShipBoard().setActiveComponent(null);

                    break;

                case SECOND_RESERVED:
                    if (reservedComponents.size()<2) {
                        throw new InvalidCommand("No active component");
                    }

                    activeTile= currentPlayer.getShipBoard().getReservedComponents().get(1);
                    if(!currentPlayer.getShipBoard().isConnectedToExistingComponents(activeTile, coordinates.getY()-5, coordinates.getX()-4)){
                        throw new InvalidParameters("Invalid position, must be connected to existing components");
                    }
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
            try {
                currentPlayer.getShipBoard().addComponent(activeTile, coordinates);
            } catch (InvalidMethodParameters e) {
                throw new RuntimeException("Either the map of ship valid position or the convertion between board and matrix coordinates failed, Strange bug!!!");
            }

        }
    }
    public void lookDeck(String name, int index) throws InvalidCommand, InvalidParameters {

        index-=1;
        if(this.getController().getMatchLevel()==MatchLevel.TRIAL){
            throw new InvalidCommand("Trial deck");
        }
        Game model= this.getController().getModel();
        Timer timer= model.getFlightBoard().getTimer();
        if(timer!=null && timer.getPhase()== Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController(), finishedPlayers));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }
            if (finishedPlayers.containsValue(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            if(currentPlayer.getShipBoard().isEmpty()){
                throw new InvalidCommand("No tiles placed yet");
            }
            if(index < 0 || index >= 3) {
                throw new InvalidParameters("Invalid index");
            }

            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
            model.addComponent(oldTile);
            currentPlayer.getShipBoard().setActiveComponent(null);



            //manca l'implementazione



        }

    }
    public void flipHourGlass(String name) throws InvalidCommand, InvalidParameters {

        if(this.getController().getMatchLevel()==MatchLevel.TRIAL){
            throw new InvalidCommand("Trial deck");
        }

        Game model= this.getController().getModel();
        Timer timer= model.getFlightBoard().getTimer();
        if(timer!=null && timer.getPhase()==Timer.Phase.LAST_PHASE && timer.getTimeLeft()==0.0f){
            this.getController().setState(new HourGlassFinishedState(this.getController(), finishedPlayers));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }

//            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
//            model.addComponent(oldTile);
//            currentPlayer.getShipBoard().setActiveComponent(null);

            assert timer!=null: "Timer is null in Level2 error in FlightBoard builder";

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
            this.getController().setState(new HourGlassFinishedState(this.getController(), finishedPlayers));
        }
        else{
            Player currentPlayer = this.getController().getModel().getPlayer(name);
            if (currentPlayer == null) {
                throw new InvalidParameters("Player not found");
            }
            if (finishedPlayers.containsValue(currentPlayer)) {
                throw new InvalidCommand("Player already finished");
            }
            if(this.getController().getMatchLevel()==MatchLevel.TRIAL){
                if(!currentPlayer.getShipBoard().validateShip()){
                    throw new InvalidCommand("Ship not valid");
                }
            }
            if(finishedPlayers.containsKey(position)){
                throw new InvalidParameters("Position already occupied");
            }

            try {
                model.getFlightBoard().setStartingPositions(currentPlayer, position);
            } catch (InvalidMethodParameters e) {
                throw new InvalidParameters("Invalid starting position, must be btween 1 and 4");
            }

            SpaceshipComponent oldTile= currentPlayer.getShipBoard().getActiveComponent();
            model.addComponent(oldTile);
            currentPlayer.getShipBoard().setActiveComponent(null);



            int penalty=currentPlayer.getShipBoard().getReservedComponents().size();
            for(int i=0; i<penalty; i++){
                currentPlayer.addJunk();
            }

            finishedPlayers.put(position, currentPlayer);

            if(finishedPlayers.size()==model.getPlayers().size()){
                MatchLevel matchLevel=this.getController().getMatchLevel();

                if (matchLevel == MatchLevel.TRIAL) {
                    this.getController().setState(new PlaceAlienState(this.getController()));
                    this.getController().setState(new FlightPhase(this.getController()));
                } else {
                    this.getController().setState(new FixShipState(this.getController()));
                }

            }
            //if it's a trial game set at frist PlaceAlienState that will populate cabins autonomously
            // then, skip FixShipState, since you cannot finish building unless your ship is valid.




        }

    }
}
