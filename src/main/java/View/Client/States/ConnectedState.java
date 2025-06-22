package View.Client.States;

import Networking.Messages.Message;
import Networking.Network;
import View.Client.ClientState;

public abstract class ConnectedState implements ClientState {
    private final Network network;

    protected ConnectedState(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return this.network;
    }

    @Override
    public final ClientState send(Message message){
        if(!this.network.send(message)){
            return this.stop();
        }

        return this;
    }

    @Override
    public ClientState stop(){
        this.network.setDone();
        return ClientState.super.stop();
    }
}
