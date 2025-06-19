package Networking.Messages;

import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;
import View.Client.Actions.LoginSuccessAction;

public class LoginMessage implements Message {
    final String username;

    public LoginMessage(String username) {
        this.username = username;
    }

    @Override
    public void handle(Agent agent, Network network) {
        final Server server;
        try {
            server = (Server) agent;
        } catch (ClassCastException e) {
            System.err.println("\"server\" is not server");
            return;
        }
        if(server.login(network, username)){
            network.send(new ClientMessage(new LoginSuccessAction(username)));
        }else{
            network.send(new ClientMessage(null/*new LoginFailedAction(username)))*/));
        }
    }
}
