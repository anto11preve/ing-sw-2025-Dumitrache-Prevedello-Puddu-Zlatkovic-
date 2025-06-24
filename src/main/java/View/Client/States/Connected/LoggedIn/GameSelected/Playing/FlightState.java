package View.Client.States.Connected.LoggedIn.GameSelected.Playing;

import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.PlayingState;
import View.States.ViewFlightBoardState;

public final class FlightState extends PlayingState {
    public FlightState(Network network, String username, Game game) {
        super(network, username, game);
        Client.view.setState(new ViewFlightBoardState());
    }

    @Override
    public RewardState net_Reward() {
        return new RewardState(this.getNetwork(), this.getUsername(), this.getGame());
    }
}
