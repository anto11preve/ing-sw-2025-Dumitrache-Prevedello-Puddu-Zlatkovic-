package View.States;

import View.Client.Client;

public class ViewShipBoardState implements ViewState{
    final String username;

    public ViewShipBoardState(String username) {
        this.username = username;
    }

    @Override
    public void paint() {
        Client.client.getState().viewShipBoard(username);
    }
}
