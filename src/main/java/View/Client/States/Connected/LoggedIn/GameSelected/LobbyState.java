package View.Client.States.Connected.LoggedIn.GameSelected;

import Model.Game;
import Model.Player;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.Playing.BuildingState;
import View.Client.States.Connected.LoggedIn.GameSelectedState;
import View.States.ViewPlayersState;

import java.util.ArrayList;
import java.util.List;

public final class LobbyState extends GameSelectedState {
    public LobbyState(Network network, String username, Game game) {
        super(network, username, game);
        Client.view.setState(new ViewPlayersState());
    }

    @Override
    public BuildingState net_Start() {
        return new BuildingState(this.getNetwork(), this.getUsername(), this.getGame());
    }

    /*Visualizer*/
    @Override
    public void viewPlayers() {
        final List<String> names = new ArrayList<>();
        for (Player player : this.getGame().getPlayers()) {
            names.add(player.getName());
        }

        Client.view.showOptions("Players connected", names);
    }
}
