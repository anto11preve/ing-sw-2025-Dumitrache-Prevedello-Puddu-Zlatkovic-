package View.States;

import View.Client.Client;

import java.util.ArrayList;
import java.util.List;

public class ChooseState implements ViewState {
    private final Client client;
    private final List<String> actions;

    public ChooseState(Client client) {
        this.client = client;

        this.actions = new ArrayList<>();

        client.showOptions("Choose command", this.client.getState().getAvailableCommands());
    }

    @Override
    public void callback(String line) {
        System.out.println("Callback of ChooseState");

        final String action = line.split(" ", 2)[0];

        for(String option : this.actions){
            if(action.equals(option)){
                client.createAction(line.split(" "));
            }
        }
    }
}
