package View.Client.States.Connected.LoggedIn;

import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.LobbyState;
import View.Client.States.Connected.LoggedInState;
import View.States.ViewNothingState;

public final class UnconfirmedSelectionState extends LoggedInState {
    public UnconfirmedSelectionState(Network network, String username) {
        super(network, username);
        Client.view.setState(new ViewNothingState());
    }

    @Override
    public LobbyState net_JoinSuccess(Game game){
        return new LobbyState(this.getNetwork(), this.getUsername(), game);
    }

    @Override
    public GameSelectionState net_JoinFailed(){
        return new GameSelectionState(this.getNetwork(), this.getUsername());
    }
}
