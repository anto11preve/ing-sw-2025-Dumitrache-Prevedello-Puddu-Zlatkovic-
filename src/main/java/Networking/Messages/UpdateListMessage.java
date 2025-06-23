package Networking.Messages;

import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;
import View.Client.Actions.UpdateListAction;

public class UpdateListMessage implements Message {
    @Override
    public void handle(Agent agent, Network network){
        final Server server;

        try{
            server = (Server) network;
        } catch (ClassCastException e){
            System.err.println("Server object is not a Server");
            return;
        }

        network.send(new ClientMessage(new UpdateListAction(server.getGameIds())));
    }
}
