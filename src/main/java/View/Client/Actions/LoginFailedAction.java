package View.Client.Actions;

import View.Client.ClientState;

public class LoginFailedAction implements Action {
    final String username;

    public LoginFailedAction(String username) {
        this.username = username;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.net_LoginFailed(username);
    }
}
