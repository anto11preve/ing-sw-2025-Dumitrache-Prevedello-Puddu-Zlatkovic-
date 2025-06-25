package View.Client.States.Connected.LoggedIn.GameSelected;

import Model.Game;
import Model.Player;
import Model.Ship.Coordinates;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.Playing.FlightState;
import View.Client.States.Connected.LoggedIn.GameSelectedState;
import View.States.ViewFlightBoardState;

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

        commands.addAll(Client.view.getState().getAvailableVisualizers());

        commands.addAll(super.getAvailableCommands());

        return commands;
    }

    /*Visualizers - TODO: make all these view agnostic*/
    @Override
    public void viewFlightBoard() {
        this.getGame().getFlightBoard().visualize(this.getGame().getState().getPlayerInTurn());
    }

    @Override
    public void viewShipBoard(String username) {
        final Player player = this.getGame().getPlayer(username);

        if(player == null){
            Client.view.setState(new ViewFlightBoardState());
            return;
        }

        player.getShipBoard().render(this.getGame().getLevel());
    }

    @Override
    public void viewComponent(String username, Coordinates coordinates){
        final Player player = this.getGame().getPlayer(username);

        if(player == null){
            Client.view.setState(new ViewFlightBoardState());
            return;
        }

        final String[] component = player.getShipBoard().getComponent(coordinates).renderBig();

        for(String s : component){
            System.out.println(s);
        }
    }
}
