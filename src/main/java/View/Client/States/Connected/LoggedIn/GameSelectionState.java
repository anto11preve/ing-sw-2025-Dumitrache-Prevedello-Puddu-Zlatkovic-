package View.Client.States.Connected.LoggedIn;

import Controller.Enums.MatchLevel;
import Networking.Messages.CreateGameMessage;
import Networking.Messages.JoinGameMessage;
import Networking.Messages.UpdateListMessage;
import Networking.Network;
import View.Client.Client;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedInState;
import View.States.ViewGamesState;

import java.util.ArrayList;
import java.util.List;

public final class GameSelectionState extends LoggedInState {
    private Integer[] gamesList = new Integer[0];

    public GameSelectionState(Network network, String username){
        super(network, username);

        this.send(new UpdateListMessage());

        Client.view.setState(new ViewGamesState());
    }

    @Override
    public ClientState create(MatchLevel matchLevel){
        final ClientState sendResult = this.send(new CreateGameMessage(matchLevel));

        if(sendResult.isDone()){
            return sendResult;
        }

        return new UnconfirmedSelectionState(this.getNetwork(), this.getUsername());
    }

    @Override
    public ClientState join(int gameId){
        final ClientState sendResult = this.send(new JoinGameMessage(gameId));

        if(sendResult.isDone()){
            return sendResult;
        }

        return new UnconfirmedSelectionState(this.getNetwork(), this.getUsername());
    }

    @Override
    public ClientState updateList(Integer[] newGamesList){
        this.gamesList = newGamesList;

        return this;
    }

    @Override
    public List<String> getAvailableCommands(){
        final List<String> list = new ArrayList<>();

        list.add("UpdateList");
        list.add("Join");
        list.add("Create");
        list.addAll(super.getAvailableCommands());

        return list;
    }

    /*Visualization*/
    @Override
    public void list(){
        final List<String> games = new ArrayList<>();

        for(Integer game : this.gamesList){
            games.add(game.toString());
        }

        /*TODO: make this work well...*/
        Client.view.showOptions("Games are ", games);
    }
}
