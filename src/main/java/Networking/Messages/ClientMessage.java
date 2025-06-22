package Networking.Messages;

import Networking.Agent;
import Networking.Network;
import View.Client.Actions.Action;
import View.Client.Client;

public class ClientMessage implements Message{
    private final Action action;

    public ClientMessage(Action action) {
        this.action = action;
    }

    @Override
    public void handle(Agent agent, Network network) {
        final Client client;

        try{
            client = (Client) agent;
        }catch (ClassCastException e){
            return;
        }

        client.execute(action);
    }
}
