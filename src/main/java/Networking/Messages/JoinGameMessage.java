package Networking.Messages;

import Controller.Commands.LoginCommand;
import Controller.Controller;
import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;
import View.Client.ClientState;

public class JoinGameMessage implements Message {
    private final int gameID;

    public JoinGameMessage(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void handle(Agent agent, Network network) throws UnsupportedOperationException {
        final Server server;

        try{
            server = (Server) agent;
        } catch (ClassCastException e) {
            return;
        }

        final String username = server.getUsername(network);

        final Controller game = server.getGame(this.gameID);

        if(username == null || game == null){
            network.send(new ClientMessage(
                    ClientState::net_JoinFailed
            ));
            return;
        }

        game.enqueueCommand(new LoginCommand(username));

        throw new RuntimeException("Killing ServerHandler. Hopefully controller will handle login soon...");
    }
}
