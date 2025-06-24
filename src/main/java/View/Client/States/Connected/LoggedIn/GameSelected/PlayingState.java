package View.Client.States.Connected.LoggedIn.GameSelected;

import Model.Game;
import Model.Ship.Components.SpaceshipComponent;
import Networking.Network;
import View.Client.States.Connected.LoggedIn.GameSelected.Playing.FlightState;
import View.Client.States.Connected.LoggedIn.GameSelectedState;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayingState extends GameSelectedState {
    public PlayingState(Network network, String username, Game game) {
        super(network, username, game);
    }

    @Override
    public FlightState net_Fly() {
        return new FlightState(this.getNetwork(), this.getUsername(), this.getGame());
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();

        /*Visualizes*/
        commands.addAll(List.of("ViewFlightBoard", "ViewShipBoard"));

        commands.addAll(super.getAvailableCommands());

        return commands;
    }

    /*Visualizers - TODO: make all these view agnostic*/
    @Override
    public void viewFlightBoard() {
        /*TODO: fai diventare visualize? di Marco*/
        this.getGame().getFlightBoard().render();
    }

    @Override
    public void viewShipBoard(String username) {
        this.getGame().getPlayer(username).getShipBoard().render();
    }

    @Override
    public void viewComponent(SpaceshipComponent component){
        component.renderBig();
    }
}
