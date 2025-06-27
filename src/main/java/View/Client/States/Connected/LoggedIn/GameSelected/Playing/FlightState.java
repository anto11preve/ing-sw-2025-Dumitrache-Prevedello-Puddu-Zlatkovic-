package View.Client.States.Connected.LoggedIn.GameSelected.Playing;

import Controller.Context;
import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.PlayingState;
import View.States.ViewFlightBoardState;

import java.util.ArrayList;
import java.util.List;

public final class FlightState extends PlayingState {
    public FlightState(Network network, String username, Game game) {
        super(network, username, game);
        Client.view.setState(new ViewFlightBoardState());
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();

        commands.add("ViewCard");

        commands.addAll(super.getAvailableCommands());

        return commands;
    }

    /*Visualizer - TODO: make view agnostic*/
    @Override
    public void viewCard(){
        for(String line : this.getGame().renderCard()){
            System.out.println(line);
        }
    }

    @Override
    public RewardState net_Reward() {
        return new RewardState(this.getNetwork(), this.getUsername(), this.getGame());
    }
}
