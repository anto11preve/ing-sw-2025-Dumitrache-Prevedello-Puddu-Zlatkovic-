package Controller.RealTimeBuilding;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import Model.Ship.ShipBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixShipState extends State {

    List<Player> playersWithInvalidShip;
    Map<Integer, List<Integer>> validCoordinates=new HashMap<>();

    FixShipState(Controller controller) {

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

        List<Player> allPlayers= this.getController().getModel().getPlayers();
        for (Player player : allPlayers) {
            ShipBoard shipBoard=player.getShipBoard();
            if(!shipBoard.validateShip()){
                playersWithInvalidShip.add(player);
            }
        }
    }

    @Override
    public void deleteComponent(String name, Coordinates coordinates) throws InvalidCommand, InvalidParameters {
        Player currrentPlayer = this.getController().getModel().getPlayer(name);
        if (currrentPlayer == null) {
            throw new InvalidParameters("Player not found");
        }
        if (!playersWithInvalidShip.contains(currrentPlayer)) {
            throw new InvalidCommand("Player not in the list of players with invalid ship");
        }
        ShipBoard shipBoard = currrentPlayer.getShipBoard();

        // Check if the coordinates are valid
        if (!validCoordinates.containsKey(coordinates.getX()) || !validCoordinates.get(coordinates.getX()).contains(coordinates.getY())) {
            throw new InvalidParameters("Invalid coordinates");
        }
        // Check if the coordinates correspond to a valid component
        SpaceshipComponent component = shipBoard.getComponent(coordinates);
        if (component == null) {
            throw new InvalidParameters("No component found at the given coordinates");
        }

        // apply removed on the tile
        component.removed();
        // remove the tile from the ship matrix
        try {
            shipBoard.removeComponent(coordinates);
        } catch (InvalidMethodParameters e) {
            throw new RuntimeException("Either the map of ship valid position or the convertion between board and matrix coordinates failed, Strange bug!!!");
        }

        // add junk
        currrentPlayer.addJunk();

        //check if the ship is now valid and if so remove the player from the list
        if (shipBoard.validateShip()) {
            playersWithInvalidShip.remove(currrentPlayer);
        }

    }

}
