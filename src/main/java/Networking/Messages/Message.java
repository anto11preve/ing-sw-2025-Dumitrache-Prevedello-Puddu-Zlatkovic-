package Networking.Messages;

import Networking.Agent;
import Networking.Network;

import java.io.Serializable;

public interface Message extends Serializable {
    default void handle() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " cannot be invoked in this context.");
    }

    default void handle(Network network) throws UnsupportedOperationException{
        handle();
    }

    default void handle(Agent agent, Network network) throws UnsupportedOperationException {
        handle(network);
    }
}
