package View.Client.States.Connected.LoggedIn;

import Model.Game;
import Networking.Network;
import View.Client.States.Connected.LoggedInState;

public class UnconfirmedSelectionState extends LoggedInState {
    public UnconfirmedSelectionState(Network network, String username) {
        super(network, username);
    }

    @Override
    public LobbyState joinSuccess(Game game){
        return new LobbyState(this.getNetwork(), this.getUsername(), game);
    }

    @Override
    public GameSelectionState joinFailed(){
        return new GameSelectionState(this.getNetwork(), this.getUsername());
    }
}
