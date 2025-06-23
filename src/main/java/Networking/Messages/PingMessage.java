package Networking.Messages;

import Networking.Network;

public class PingMessage implements Message {
    @Override
    public void handle(Network network) {
        if(!network.send(new EmptyMessage())){
            System.err.println("PingCommand: could not send pong command. Killing network");
            network.setDone();
        }
    }
}
