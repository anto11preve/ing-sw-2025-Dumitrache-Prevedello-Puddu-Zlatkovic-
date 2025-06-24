package View.Client.Actions;

import View.Client.ClientState;

import java.io.Serializable;

public interface Action extends Serializable {
    ClientState execute(ClientState state);

    default boolean isVisualize() {
        return false;
    }

    static ActionConstructor getConstructor() {
        return null;
    }
}
