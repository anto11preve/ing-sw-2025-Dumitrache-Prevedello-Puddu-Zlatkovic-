package View.Client.States.Connected.LoggedIn.GameSelected.Playing;

import Model.Board.CardDeck;
import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.ClientState;
import View.Client.States.Connected.LoggedIn.GameSelected.PlayingState;
import View.States.ViewCardDeckState;
import View.States.ViewFlightBoardState;
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

    /*Network commands*/
    @Override
    public ClientState net_ViewCardDeck(String username) {
        if(username.equals(this.getUsername())) {
            Client.view.setState(new ViewCardDeckState());
        }

        return this;
    }

    /*Visualizers - TODO: make view agnostic*/
    @Override
    public void viewCardDeck() {
        final Game game = this.getGame();
        final CardDeck deck;

        if((deck = game.getFlightBoard().getBookedDecks().get(this.getUsername())) == null){
            Client.view.setState(new ViewFlightBoardState());
            return;
        }

        CardDeck.render(deck.peekCards());
    }

    @Override
    public void viewTiles() {
        this.getGame().render();
    }
}
