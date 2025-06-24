package View.Client;

import Controller.Commands.CommandConstructor;
import Networking.Agent;
import Networking.Messages.ControllerMessage;
import Networking.Utils;
import View.Client.Actions.Action;
import View.Client.Actions.ActionConstructor;
import View.Client.States.ProtocolChoiceState;
import View.GUI;
import View.States.MenuStates.ChooseActionState;
import View.States.MenuStates.ActionCreationState;
import View.States.MenuStates.StopState;
import View.States.ViewNothingState;
import View.TUI;
import View.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements Agent {
    public static Client client;
    public static View view;
    private ClientState state;

    public static void main(String[] args) {
        final boolean useGui = Utils.getPosition("--nogui", args) == -1;

        final String hostname = Utils.getOption("--hostname", args);

        final String port = Utils.getOption("--port", args);

        view = (useGui) ? new GUI() : new TUI();
        client = new Client(hostname, port);

        client.run();
    }

    private Client(final String hostname, final String port) {
        /*TODO: make the arguments not be ignored*/
        this.state = new ProtocolChoiceState();
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
        this.state = action.execute(this.state);

        if(this.state.isDone()){
            view.setMenuState(new StopState());
        } else if (!action.isVisualize()) {
            /*If the action is just a visualization action,
             there is no need to change the menu*/
            view.setMenuState(new ChooseActionState());
            view.repaint();
        }
    }

    public void createAction(String action, String[] args){
        ActionConstructor actionConstructor = ActionConstructor.getActionConstructors().get(action);

        //the command is not part of the available commands
        if(actionConstructor == null) {
            final CommandConstructor commandConstructor = CommandConstructor.getCommandConstructor().get(action);

            if(commandConstructor == null){
                return;
            }

            actionConstructor = new ActionConstructor() {
                @Override
                public Action create(Map<String, String> args) throws IllegalArgumentException {
                    return state -> state.send(
                            new ControllerMessage(
                                    commandConstructor.create(state.getUsername(), args)
                            )
                    );
                }

                @Override
                public List<String> getArguments() {
                    return commandConstructor.getArguments();
                }
            };
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
            } catch(IllegalArgumentException e){
                _action = state -> state;
            }

            this.execute(_action);
            return;
        }

        //the command needs to be created with arguments
        view.setMenuState(new ActionCreationState(actionConstructor, providedArguments));
    }
}