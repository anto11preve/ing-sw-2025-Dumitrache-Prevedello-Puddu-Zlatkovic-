package Networking.Messages;

import Controller.Commands.LoginCommand;
import Controller.Controller;
import Controller.Enums.MatchLevel;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;

public class CreateGameMessage implements Message {
    private final MatchLevel matchLevel;

    public CreateGameMessage(MatchLevel matchLevel) {
        this.matchLevel = matchLevel;
    }

    @Override
    public void handle(Agent agent, Network network){
        final Server server;

        try{
            server = (Server) agent;
        } catch (ClassCastException e){
            System.err.println("\"server\" is not a Server");
            return;
        }

        final String username;

        //don't accept non-logged in players (should be impossible)
        if((username = server.getUsername(network)) == null){
            return;
        }

        System.err.println("Creating new game");

        final Controller game = server.createGame(this.matchLevel);

        System.err.println("Enqueuing loginCommand");

        try{
            game.login(username);

            System.err.println("Game joined successfully. Killing ServerHandler and starting ControllerHandler");

            new Handler<>(game, network).start();

            throw new RuntimeException();
        }catch (InvalidCommand | InvalidParameters _){
        }



    }
}
