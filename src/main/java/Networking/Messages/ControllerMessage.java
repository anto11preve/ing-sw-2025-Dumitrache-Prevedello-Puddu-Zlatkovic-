package Networking.Messages;

import Controller.Commands.Command;
import Controller.Controller;
import Networking.Agent;
import Networking.Network;

public class ControllerMessage implements Message {
    private final Command command;

    public ControllerMessage(Command command) {
        this.command = command;
    }

    @Override
    public void handle(Agent agent, Network network) {
        final Controller controller;

        try{
            controller = (Controller) agent;
        } catch (ClassCastException e) {
            System.err.println("\"controller\" is not a controller");
            return;
        }

        controller.enqueueCommand(this.command);
    }

}
