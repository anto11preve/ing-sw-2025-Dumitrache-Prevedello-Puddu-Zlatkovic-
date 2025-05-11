package Controller.SubStates;

import Controller.AbandonedShip.AbandonedShipState;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.FlightPhase;
import Model.Ship.Coordinates;

public class CrewRemovalState extends SubState {

    @Override
    public void removeCrewMember(Controller controller, AbandonedShipState macroState, String playerName, ItemType itemType, Coordinates coordinates, int amount) {
        parentState = macroState;
        // Logic to remove a crew member from the ship
        controller.setState(new FlightPhase());
    }
}
