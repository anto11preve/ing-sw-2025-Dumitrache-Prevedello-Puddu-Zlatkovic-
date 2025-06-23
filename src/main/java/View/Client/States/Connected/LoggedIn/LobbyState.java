package View.Client.States.Connected.LoggedIn;

import Model.Game;
import Networking.Network;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedInState;

import java.util.ArrayList;
import java.util.List;

public class LobbyState extends LoggedInState {
    private Game game;

    public LobbyState(Network network, String username, Game game) {
        super(network, username);
        this.game = game;
    }

    public final Game getGame() {
        return game;
    }

    @Override
    public ClientState updateGame(Game game){
        this.game = game;

        return this;
    }

    @Override
    public List<String> getAvailableCommands() {
        final List<String> commands = new ArrayList<>(/*TODO: add this after merge: game.getState().getAvailableCommands()*/);

        commands.addAll(super.getAvailableCommands());

        return commands;
    }
}
