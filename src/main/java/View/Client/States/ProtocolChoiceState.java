package View.Client.States;

import View.Client.ClientState;

import java.util.ArrayList;
import java.util.List;

public final class ProtocolChoiceState implements ClientState {
    @Override
    public ClientState chooseProtocol(String choice){
        if(choice.equalsIgnoreCase("RMI")){
            return new ConnectingState(true);
        }else if(choice.equalsIgnoreCase("TCP")){
            return new ConnectingState(false);
        }

        return this;
    }

    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();

        commands.add("RMI");
        commands.add("TCP");
        commands.addAll(ClientState.super.getAvailableCommands());

        return commands;
    }
}
