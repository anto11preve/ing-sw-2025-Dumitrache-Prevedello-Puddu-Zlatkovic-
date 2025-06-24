package View.Client;

import Controller.Commands.CommandConstructor;
import Networking.Agent;
import Networking.Messages.ControllerMessage;
import Networking.Utils;
import View.Client.Actions.Action;
import View.Client.Actions.ActionConstructor;
import View.Client.States.ProtocolChoiceState;
import View.GUI;
import View.States.ChooseActionState;
import View.States.ActionCreationState;
import View.States.StopState;
import View.TUI;
import View.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements Agent {
    public static Client client;
    private final View view;
    private ClientState state;

    public static void main(String[] args) {
        final boolean useGui = Utils.getPosition("--nogui", args) == -1;

        final String hostname = Utils.getOption("--hostname", args);

        final String port = Utils.getOption("--port", args);

        client = new Client((useGui) ? new GUI() : new TUI(), hostname, port);

        client.run();
    }

    private Client(final View view, final String hostname, final String port) {
        /*TODO: make the arguments not be ignored*/
        this.view = view;
        this.state = new ProtocolChoiceState();
    }

    @Override
    public void run() {
        this.view.setState(new ChooseActionState());
        view.run();
    }

    public ClientState getState() {
        return state;
    }

    public synchronized void execute(Action action) {
        this.state = action.execute(this.state);

        if(this.state.isDone()){
            this.view.setState(new StopState());
        }else {
            this.view.setState(new ChooseActionState());
        }
    }

    public void showOptions(String prompt, List<String> options){
        view.showOptions(prompt, options);
    }

    public void showArguments(List<String> arguments, Map<String, String> providedArguments){
        view.showArguments(arguments, providedArguments);
    }

    public void createAction(String action, String[] args){
        ActionConstructor actionConstructor = ActionConstructor.actionConstructors.get(action);

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
            this.execute(actionConstructor.create(providedArguments));
            return;
        }

        //the command needs to be created with arguments
        this.view.setState(new ActionCreationState(actionConstructor, providedArguments));
    }
}