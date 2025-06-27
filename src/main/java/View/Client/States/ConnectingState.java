package View.Client.States;

import Networking.Messages.Handler;
import Networking.Network;
import Networking.RMI.RMINetwork;
import Networking.TCP.TCPNetwork;
import View.Client.Client;
import View.Client.ClientState;
import View.Client.States.Connected.LoginState;

import java.util.ArrayList;
import java.util.List;

public final class ConnectingState implements ClientState {
    private final boolean useRMI;

    public ConnectingState(boolean useRMI) {
        this.useRMI = useRMI;
    }

    @Override
    public ClientState connect(String hostname, Integer port) {
        final Network network;
        try {
            network = useRMI ? new RMINetwork(hostname, port) : new TCPNetwork(hostname, port);
        } catch (Exception e) {
            System.out.println("Could not connect with " + (useRMI ? "RMI" : "TCP") + " @" + hostname + ":" + port);
            return this;
        }

        new Handler<>(Client.client, network).start();
        return new LoginState(network);
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> actions = new ArrayList<>();

        actions.add("Connect");

        actions.addAll(ClientState.super.getAvailableCommands());

        return actions;
    }
}
