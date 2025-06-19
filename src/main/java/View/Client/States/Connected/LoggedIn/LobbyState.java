package View.Client.States.Connected.LoggedIn;

import Networking.Network;
import View.Client.States.ConnectedState;

public class LobbyState extends ConnectedState {
    public LobbyState(Network network){
        super(network);
    }
}