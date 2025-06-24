package View.Client.States.Connected.LoggedIn;

import Model.Game;
import Networking.Messages.LeaveGameMessage;
import Networking.Network;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedInState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameSelectedState extends LoggedInState {
    private Game game;

    public GameSelectedState(Network network, String username, Game game) {
        super(network, username);
        this.game = game;
    }

    public final Game getGame() {
        return game;
    }

    @Override
    public ClientState net_Leave(String username) {
        if(!Objects.equals(username, this.getUsername())) {
            return this;
        }

        final ClientState sendResult;

        if((sendResult = this.send(new LeaveGameMessage())).isDone()){
            return sendResult;
        }

        return new GameSelectionState(this.getNetwork(), this.getUsername());
    }

    @Override
    public ClientState updateGame(Game game){
        this.game = game;

        return this;
    }

    @Override
    public List<String> getAvailableCommands() {
        final List<String> commands = new ArrayList<>(game.getState().getAvailableCommands());

        commands.addAll(super.getAvailableCommands());

        return commands;
    }
}
