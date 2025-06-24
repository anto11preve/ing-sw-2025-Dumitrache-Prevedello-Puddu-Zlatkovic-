package View.Client.States.Connected;

import Networking.Messages.EmptyMessage;
import Networking.Network;
import View.Client.ClientState;
import View.Client.States.ConnectedState;

public abstract class LoggedInState extends ConnectedState {
    private final String username;

    public LoggedInState(Network network, String username){
        super(network);
        this.username = username;
    }

    @Override
    public final String getUsername() {
        return this.username;
    }

    @Override
    public ClientState stop(){
        /*TODO: implement logoutMessage*/
        this.getNetwork().send(new EmptyMessage());
        return super.stop();
    }
}
