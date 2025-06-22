package View.Client.Actions;

import View.Client.ClientState;

import java.io.Serializable;

public interface Action extends Serializable {
    ClientState execute(ClientState state);

    static ActionConstructor getConstructor() {
        return null;
    }
}
