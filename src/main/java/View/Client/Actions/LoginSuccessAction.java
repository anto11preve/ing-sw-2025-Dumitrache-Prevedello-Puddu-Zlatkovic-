package View.Client.Actions;

import View.Client.ClientState;

public class LoginSuccessAction implements Action {
    private final String name;

    public LoginSuccessAction(String name){
        this.name = name;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.loginSuccess(name);
    }
}
