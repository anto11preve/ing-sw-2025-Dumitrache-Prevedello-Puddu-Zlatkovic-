package Networking.Messages;

import Networking.Network;
import View.Client.Actions.Action;
import View.Client.Client;

public class ClientMessage implements Message{
    private final Action action;

    public ClientMessage(Action action) {
        this.action = action;
    }

    public void handle(Client client, Network network) {
        client.execute(action);
    }
}
