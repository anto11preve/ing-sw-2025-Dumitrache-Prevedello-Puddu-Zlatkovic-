package View.Client.States.Connected.LoggedIn;

import Model.Game;
import Networking.Network;
import View.Client.States.Connected.LoggedInState;

public class PlayingState extends LoggedInState {
    private final Game game;

    public PlayingState(Network network, String username, Game game) {
        super(network, username);
        this.game = game;
    }
}
