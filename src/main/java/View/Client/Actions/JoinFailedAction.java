package View.Client.Actions;

import View.Client.ClientState;
import View.Client.States.Connected.LoggedIn.UnconfirmedSelectionState;

public class JoinFailedAction implements Action {

    @Override
    public ClientState execute(ClientState state) {
        return state.joinFailed();
    }
}
