package View.Client.States.Connected.LoggedIn.GameSelected.Playing;

import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.PlayingState;
import View.States.ViewRewardsState;

import java.util.ArrayList;
import java.util.List;

public class RewardState extends PlayingState {
    public RewardState(Network network, String username, Game game) {
        super(network, username, game);
        Client.view.setState(new ViewRewardsState());
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();

        commands.add("ViewRewards");

        commands.addAll(super.getAvailableCommands());

        return commands;
    }

    /*Visualizer*/
    @Override
    public void viewRewards() {
        this.getGame().visualizeRewards();
    }
}
