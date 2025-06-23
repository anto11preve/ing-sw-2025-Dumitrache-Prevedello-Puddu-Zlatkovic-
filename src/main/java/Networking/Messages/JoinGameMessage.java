package Networking.Messages;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;

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
            return;
        }

        try{
            game.login(username);

            System.err.println("Game joined successfully. Killing ServerHandler and starting ControllerHandler");

            new Handler<>(game, network).start();

            throw new RuntimeException();
        }catch (InvalidCommand | InvalidParameters _){
        }
    }
}
