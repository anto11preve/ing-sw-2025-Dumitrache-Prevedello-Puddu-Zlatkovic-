package Controller.RealTimeBuilding;

import Controller.Controller;
import Controller.Enums.CrewType;
import Controller.Exceptions.*;
import Controller.State;
import Controller.FlightPhase;
import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.CondensedShip;
import Model.Ship.Coordinates;
import Model.Ship.ShipBoard;

import java.util.List;
import java.util.Map;

class CanInsertAliens {

    private boolean canInstertBrown;
    private boolean canInsertPurple;

    public CanInsertAliens(boolean canInstertBrown, boolean canInsertPurple) {
        this.canInstertBrown = canInstertBrown;
        this.canInsertPurple = canInsertPurple;
    }

    public boolean getCanInstertBrown() {
        return canInstertBrown;
    }
    public boolean getCanInsertPurple() {
        return canInsertPurple;
    }

    public void insertedBrown() {
        this.canInstertBrown = false;
    }
    public void insertedPurple() {
        this.canInsertPurple = false;
    }
}

public class PlaceAlienState extends State {

    Map<Player, CanInsertAliens> playersAlienAvailability;

    private void fillCabinsWithHumans(CondensedShip condensedShip) {
        List<Cabin> cabins = condensedShip.getCabins();
        for (Cabin cabin : cabins) {
            if (cabin.getOccupants() == Crewmates.EMPTY) {
                cabin.setOccupants(Crewmates.DOUBLE_HUMAN);
            }
        }

    }

    public PlaceAlienState(Controller controller) {
        super(controller);
        List<Player> players = controller.getModel().getPlayers();
        MatchLevel matchLevel = controller.getMatchLevel();
        for (Player player : players) {
            CondensedShip condensedShip = player.getShipBoard().getCondensedShip();

            if (matchLevel== MatchLevel.LEVEL2) {
                boolean localCanContainBrown = condensedShip.canContainBrown();
                boolean localCanContainPurple = condensedShip.canContainPurple();

                if (localCanContainBrown || localCanContainPurple) {
                    playersAlienAvailability.put(player, new CanInsertAliens(localCanContainBrown, localCanContainPurple));
                }else{
                    fillCabinsWithHumans(condensedShip);
                }

            }else{
                fillCabinsWithHumans(condensedShip);
            }

        }



    }

    @Override
    public void placeCrew(String name, Coordinates coordinates, CrewType type) throws InvalidCommand, InvalidParameters {
        Player currentPlayer = this.getController().getModel().getPlayer(name);
        if (currentPlayer == null) {
            throw new InvalidParameters("Player not found");
        }
        if (!playersAlienAvailability.containsKey(currentPlayer)) {
            throw new InvalidCommand("Already placed aliens");
        }
        if (type == CrewType.BROWN_ALIEN && !playersAlienAvailability.get(currentPlayer).getCanInstertBrown()) {
            throw new InvalidCommand("Already placed brown alien");
        }
        if (type == CrewType.PURPLE_ALIEN && !playersAlienAvailability.get(currentPlayer).getCanInsertPurple()) {
            throw new InvalidCommand("Already placed purple alien");
        }
        // Check if the coordinates correspond to a cabin
        ShipBoard shipBoard= currentPlayer.getShipBoard();
        SpaceshipComponent shipComponent = shipBoard.getComponent(coordinates);
        if (shipComponent == null || !(shipBoard.getCondensedShip().getCabins().contains(shipComponent))) {
            throw new InvalidParameters("Invalid coordinates, not a cabin");
        }



        //Check if the cabin can contain the alien type specified
        Cabin cabin = (Cabin) shipComponent;
        if (type == CrewType.BROWN_ALIEN && !cabin.getCanContainBrown()) {
            throw new InvalidCommand("Cabin cannot contain brown alien");
        }
        if (type == CrewType.PURPLE_ALIEN && !cabin.getCanContainPurple()) {
            throw new InvalidCommand("Cabin cannot contain purple alien");
        }
        // and it's not already occupied
        if (cabin.getOccupants() != Crewmates.EMPTY) {
            throw new InvalidCommand("Cabin already occupied");
        }


        // insert the alien in the cabin
        // and update the playersAlienAvailability map
        CanInsertAliens canInsertAliens = playersAlienAvailability.get(currentPlayer);

        if (type == CrewType.BROWN_ALIEN) {
            cabin.setOccupants(Crewmates.BROWN_ALIEN);
            canInsertAliens.insertedBrown();
        } else if (type == CrewType.PURPLE_ALIEN) {
            cabin.setOccupants(Crewmates.PURPLE_ALIEN);
            canInsertAliens.insertedPurple();
        }
        // if both aliens are placed, remove the player from the map

        if (!canInsertAliens.getCanInstertBrown() && !canInsertAliens.getCanInsertPurple()) {
            playersAlienAvailability.remove(currentPlayer);
            fillCabinsWithHumans(shipBoard.getCondensedShip());
        }

        // If the map is empty, change the state to the next one
        if (playersAlienAvailability.isEmpty()) {
            this.getController().setState(new FlightPhase(this.getController()));
        }


    }



}
