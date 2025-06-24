package View.Client.States.Connected.LoggedIn.GameSelected.Playing;

import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.PlayingState;
import View.States.ViewTilesState;

import java.util.ArrayList;
import java.util.List;

public final class BuildingState extends PlayingState {
    public BuildingState(Network network, String username, Game game) {
        super(network, username, game);
        Client.view.setState(new ViewTilesState());
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();

        commands.add("ViewTiles");

        commands.addAll(super.getAvailableCommands());

        return commands;
    }

    /*Visualizers - TODO: make view agnostic*/
    @Override
    public void viewTiles() {
        this.getGame().render();
    }
}
