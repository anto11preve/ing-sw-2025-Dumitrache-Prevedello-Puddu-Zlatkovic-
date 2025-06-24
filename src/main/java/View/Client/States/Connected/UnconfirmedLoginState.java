package View.Client.States.Connected;

import Networking.Network;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedIn.GameSelectionState;
import View.Client.States.ConnectedState;

public final class UnconfirmedLoginState extends ConnectedState {
    private final String username;

    public UnconfirmedLoginState(Network network, String username) {
        super(network);
        this.username = username;
    }

    @Override
    public ClientState net_LoginSuccess(String username){
        if(!username.equals(this.username)){
            return this.stop();
        }

        return new GameSelectionState(this.getNetwork(), this.username);
    }

    @Override
    public LoginState net_LoginFailed(String username) {
        return new LoginState(this.getNetwork());
    }
}
