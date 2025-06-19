package Networking.Messages;

import Networking.Agent;
import Networking.Network;

import java.io.Serializable;

public interface Message extends Serializable {
    default void handle() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " cannot be invoked in this context.");
    }

    default void handle(Network network) {
        handle();
    }

    default void handle(Agent agent, Network network) {
        handle(network);
    }
}
