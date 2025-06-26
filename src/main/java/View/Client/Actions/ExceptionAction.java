package View.Client.Actions;

import View.Client.Client;
import View.Client.ClientState;

public class ExceptionAction implements Action {
    private final Exception e;

    public ExceptionAction(Exception e) {
        this.e = e;
    }

    @Override
    public ClientState execute(ClientState state) {
        Client.view.log(e.getMessage());
        return state;
    }
}
