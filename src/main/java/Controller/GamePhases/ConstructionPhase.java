package Controller.GamePhases;

import Controller.Controller;
import Controller.State;

import java.util.Map;

public class ConstructionPhase extends State {
    @Override
    public void execute(Map<String, Object> command, Controller controller) {
        // Handle the construction phase actions here
        String action = (String) command.get("action");
        switch (action) {
            case "PeekTile":
                // Handle peeking action
                break;
            case "PlaceTile":
                // Handle placing action
                break;
            case "DiscardTile":
                // Handle discarding action
                break;
            case "ReserveTile":
                // Handle reserving action
                break;
            case "PlaceReservedTile":
                // Handle placing reserved tile action
                break;
            case "rotateTile":
                // Handle rotating tile action
                break;
            case "PeekDeck":
                // Handle peeking deck action
                break;
            case "TurnHourglass":
                // Handle turning hourglass action
                break;
            case "CheckTime":
                // Handle checking time action
                break;
            case "EndConstruction":
                controller.setState(new FlightPhase());
                break;
            case "PlaceCrewmates":
                // Handle placing crewmates action
                break;

            default:
                //do nothing
                break;
        }
    }
}
