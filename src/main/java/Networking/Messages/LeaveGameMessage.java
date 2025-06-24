package Networking.Messages;

import Controller.Controller;
import Controller.Server.Server;
import Networking.Agent;
import Networking.Network;

public class LeaveGameMessage implements Message {
    @Override
    public void handle(Agent agent, Network network) {
        try{
            final Controller _ = (Controller) agent;
        } catch(ClassCastException e){
            System.err.println(agent.getClass().getSimpleName() + " is not a Controller");
            return;
        }

        new Handler<>(Server.server, network).start();

        throw new RuntimeException("Killing ControllerHandler and starting ServerHandler");
    }
}
