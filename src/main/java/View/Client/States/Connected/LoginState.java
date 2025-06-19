package View.Client.States.Connected;

import Networking.Messages.LoginMessage;
import Networking.Network;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedIn.GameSelectionState;
import View.Client.States.ConnectedState;

import java.util.ArrayList;
import java.util.List;

public class LoginState extends ConnectedState {
    public LoginState(Network network) {
        super(network);
    }

    @Override
    public List<String> getAvailableCommands() {
        final List<String> actions = new ArrayList<>();

        actions.add("login");
        actions.addAll(super.getAvailableCommands());

        return actions;
    }

    @Override
    public ClientState login(String username){
        return this.send(new LoginMessage(username));
    }

    @Override
    public ClientState loginSuccess(String username){
        return new GameSelectionState(this.getNetwork(), username);
    }
}
