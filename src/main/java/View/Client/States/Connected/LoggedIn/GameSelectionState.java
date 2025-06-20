package View.Client.States.Connected.LoggedIn;

import Networking.Network;
import View.Client.Client;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedInState;

import java.util.ArrayList;
import java.util.List;

public class GameSelectionState extends LoggedInState {
    private int[] gamesList = new int[0];

    public GameSelectionState(Network network, String username){
        super(network, username);
    }

    @Override
    public List<String> getAvailableCommands(){
        final List<String> list = new ArrayList<>();

        list.add("list");
        list.add("join");
        list.add("create");
        list.addAll(super.getAvailableCommands());

        return list;
    }

    public ClientState list(){
        final List<String> games = new ArrayList<>();

        for(Integer game : this.gamesList){
            games.add(game.toString());
        }

        /*TODO: make this work well...*/
        Client.client.showOptions("Games are ", games);

        return this;
    }

    /*TODO: implement this*/
    public ClientState updateList(){
        return this;
    }

    public ClientState join(int gameId){
        return new LobbyState(this.getNetwork(), this.getUsername(), gameId);
    }
}
