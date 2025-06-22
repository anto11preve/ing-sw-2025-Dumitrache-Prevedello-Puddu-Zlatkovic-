package View.Client.States.Connected.LoggedIn;

import Networking.Network;
import View.Client.States.Connected.LoggedInState;
import View.Client.States.ConnectedState;

public class LobbyState extends LoggedInState {
    private final int gameId;

    public LobbyState(Network network, String username, int gameId) {
        super(network, username);
        this.gameId = gameId;
    }

    public final int  getGameId() {
        return this.gameId;
    }
}