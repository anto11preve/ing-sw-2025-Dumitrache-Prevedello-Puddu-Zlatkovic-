package Networking.Messages;

import Networking.Agent;
import Networking.Network;
import View.Client.Client;
import View.Client.ClientState;

public class QuitMessage implements Message {
    @Override
    public void handle(Network network) {
        System.out.println("Received QuitCommand");
        network.setDone();
    }

    @Override
    public void handle(Agent agent, Network network) {
        final Client client;

        try{
            client = (Client) agent;
        } catch (ClassCastException e) {
            handle(network);
            return;
        }

        client.execute(ClientState::stop);
    }
}
