package View.Client.States.Connected;

import Networking.Network;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedIn.GameSelectionState;
import View.Client.States.ConnectedState;

public class LoginUnsureState extends ConnectedState {
    private final String username;

    public LoginUnsureState(Network network, String username) {
        super(network);
        this.username = username;
    }

    @Override
    public ClientState loginSuccess(String username){
        if(!username.equals(this.username)){
            return this.stop();
        }

        return new GameSelectionState(this.getNetwork(), this.username);
    }

    @Override
    public ClientState loginFailed(String username) {
        return new LoginState(this.getNetwork());
    }
}
