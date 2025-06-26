package View.Client;

import Networking.Agent;
import Networking.Utils;
import View.Client.Actions.Action;
import View.Client.Actions.ActionConstructor;
import View.Client.Actions.ExceptionAction;
import View.Client.States.ConnectingState;
import View.Client.States.ProtocolChoiceState;
import View.States.MenuStates.ChooseActionState;
import View.States.MenuStates.ActionCreationState;
import View.States.MenuStates.StopState;
import View.States.ViewNothingState;
import View.TUI;
import View.View;

import java.util.HashMap;
import java.util.Map;

public class Client implements Agent {
    public static Client client;
    public static View view;
    private ClientState state;

    public static void main(String[] args) {
        final Boolean useRMI = (Utils.getPosition("--useRMI", args) != -1) ? Boolean.TRUE : (Utils.getPosition("--useTCP", args) != -1) ? Boolean.FALSE : null;

        final boolean localhost = Utils.getPosition("--localhost", args) != -1;


        view = new TUI();
        client = new Client();

        if (useRMI == null) {
            client.state = new ProtocolChoiceState();
        } else {
            client.state = new ConnectingState(useRMI);
            if(localhost) {
                client.state = client.state.connect("localhost", null);
            }
        }

        client.run();
    }

    @Override
    public void run() {
        view.setState(new ViewNothingState());
        view.setMenuState(new ChooseActionState());
        view.run();
        view.repaint();
    }

    public ClientState getState() {
        return state;
    }

    public synchronized void execute(Action action) {
        try {
            this.state = action.execute(this.state);
        } catch (UnsupportedOperationException e) {
            view.log(e.getMessage());
        }

        if(this.state.isDone()){
            view.setMenuState(new StopState());
        } else {
            view.setMenuState(new ChooseActionState());
            view.repaint();
        }
    }

    public void createAction(String action, String[] args){
        ActionConstructor actionConstructor = ActionConstructor.getActionConstructor(action);

        //the command is not part of the available commands
        if(actionConstructor == null) {
            return;
        }

        //the command is part of the available commands but it doesn't require any arguments
        if(actionConstructor.getArguments() == null || actionConstructor.getArguments().isEmpty()) {
            this.execute(actionConstructor.create(null));
            return;
        }

        final Map<String, String> providedArguments = new HashMap<>();

        for(int i = 0; i < Integer.min(args.length, actionConstructor.getArguments().size()); i++){
            providedArguments.put(actionConstructor.getArguments().get(i), args[i]);
        }

        //the arguments provided are enough
        if(args.length >= actionConstructor.getArguments().size()){
            Action _action;
            try{
                _action = actionConstructor.create(providedArguments);
            } catch(Exception e){
                _action = new ExceptionAction(e);
            }

            this.execute(_action);
            return;
        }

        //the command needs to be created with arguments
        view.setMenuState(new ActionCreationState(actionConstructor, providedArguments));
        view.repaint();
    }
}