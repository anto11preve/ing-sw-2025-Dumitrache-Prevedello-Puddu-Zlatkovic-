package Networking.Messages;

import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;
import View.Client.Actions.LoginFailedAction;
import View.Client.Actions.LoginSuccessAction;

public class LoginMessage implements Message {
    final String username;

    public LoginMessage(String username) {
        this.username = username;
    }

    @Override
    public void handle(Agent agent, Network network) {
        final Server server;

        try{
            server = (Server) agent;
        } catch (ClassCastException e) {
            return;
        }

        network.send(new ClientMessage(server.login(network, username) ? new LoginSuccessAction(username) : new LoginFailedAction(username)));
    }
}
