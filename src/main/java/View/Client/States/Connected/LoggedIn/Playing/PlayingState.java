package View.Client.States.Connected.LoggedIn.Playing;

import Model.Game;
import Networking.Network;
import View.Client.States.Connected.LoggedIn.LobbyState;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayingState extends LobbyState {
    public PlayingState(Network network, String username, Game game) {
        super(network, username, game);
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();
        /*TODO: add viewFlightboard and viewShipboard(player)*/

        commands.addAll(super.getAvailableCommands());

        return commands;
    }
}
