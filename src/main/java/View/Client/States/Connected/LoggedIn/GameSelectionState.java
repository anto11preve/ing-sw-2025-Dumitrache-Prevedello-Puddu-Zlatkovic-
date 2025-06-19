package View.Client.States.Connected.LoggedIn;

import Networking.Network;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedInState;

public class GameSelectionState extends LoggedInState {
    public GameSelectionState(Network network, String username){
        super(network, username);
    }

    public ClientState list(){
        /*TODO: implement this*/
        return this;
    }

    /*TODO: implement this*/
    public ClientState updateList(){
        return this;
    }
}
