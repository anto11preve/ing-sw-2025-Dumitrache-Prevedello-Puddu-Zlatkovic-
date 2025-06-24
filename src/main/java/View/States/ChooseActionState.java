package View.States;

import View.Client.Client;

public class ChooseActionState implements ViewState {

    public ChooseActionState() {
        this.listActions();
    }

    private void listActions() {
        Client.client.showOptions("Choose action", Client.client.getState().getAvailableCommands());
    }

    @Override
    public void callback(String line) {
        final String action = line.split(" ", 2)[0];

        String[] args;
        try{
            args = line.split(" ", 2)[1].split(" ");
        } catch (ArrayIndexOutOfBoundsException e){
            args = new String[0];
        }

        for(String option : Client.client.getState().getAvailableCommands()){
            if(action.equalsIgnoreCase(option)){
                Client.client.createAction(option, args);
                return;
            }
        }

        this.listActions();
    }
}
