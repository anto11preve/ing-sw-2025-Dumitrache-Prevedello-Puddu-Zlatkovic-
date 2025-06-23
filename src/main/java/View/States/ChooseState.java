package View.States;

import View.Client.Client;

public class ChooseState implements ViewState {

    public ChooseState() {
        this.listArguments();
    }

    private void listArguments() {
        Client.client.showOptions("Choose command", Client.client.getState().getAvailableCommands());
    }

    @Override
    public void callback(String line) {
        System.out.println("Callback of ChooseState");

        final String action = line.split(" ", 2)[0];

        for(String option : Client.client.getState().getAvailableCommands()){
            if(action.equalsIgnoreCase(option)){
                Client.client.createAction(line.split(" "));
                return;
            }
        }

        this.listArguments();
    }
}
