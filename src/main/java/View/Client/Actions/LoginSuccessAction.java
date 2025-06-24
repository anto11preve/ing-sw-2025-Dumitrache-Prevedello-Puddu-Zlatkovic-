package View.Client.Actions;

import View.Client.ClientState;

public class LoginSuccessAction implements Action {
    private final String username;

    public LoginSuccessAction(String username){
        this.username = username;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.net_LoginSuccess(username);
    }
}
