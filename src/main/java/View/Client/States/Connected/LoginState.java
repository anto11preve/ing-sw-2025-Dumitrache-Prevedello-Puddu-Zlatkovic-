package View.Client.States.Connected;

import Networking.Messages.LoginMessage;
import Networking.Network;
import View.Client.ClientState;
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
        final ClientState sendResult = this.send(new LoginMessage(username));

        if(sendResult.isDone()){
            return sendResult;
        }

        return new UnconfirmedLoginState(this.getNetwork(), username);
    }
}
