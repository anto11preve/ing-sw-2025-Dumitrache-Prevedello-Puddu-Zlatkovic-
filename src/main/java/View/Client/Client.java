package View.Client;

import Networking.Agent;
import Networking.Utils;
import View.Client.Actions.Action;
import View.Client.Actions.ActionConstructor;
import View.Client.States.ConnectingState;
import View.GUI;
import View.States.ChooseState;
import View.States.CommandCreationState;
import View.States.StopState;
import View.TUI;
import View.View;

import java.util.List;
import java.util.function.Function;

public class Client implements Agent {
    private final View view;
    private ClientState state;

    public static void main(String[] args) {
        final boolean useGui = Utils.getPosition("--nogui", args) == -1;

        final String hostname = Utils.getOption("--hostname", args);

        final String port = Utils.getOption("--port", args);

        final Client client = new Client((useGui) ? new GUI() : new TUI(), hostname, port);

        client.run();
    }

    private Client(final View view, final String hostname, final String port) {
        this.view = view;
        this.state = new ConnectingState(this);
        this.view.setState(new ChooseState(this));
    }

    @Override
    public void run() {
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
            this.view.setState(new ChooseState(this));
        }
        final Function<Integer, String> function = Object::toString;

        final String applied = function.apply(10);
    }

    public void showOptions(String prompt, List<String> options){
        view.showOptions(prompt, options);
    }

    public void createAction(String[] command){
        final ActionConstructor actionConstructor = ActionConstructor.actionConstructors.get(command[0]);

        //the command is not part of the available commands
        if(actionConstructor == null) {
            return;
        }

        //the command is part of the available commands but it doesn't require any arguments
        if(actionConstructor.getArguments() == null || actionConstructor.getArguments().isEmpty()) {
            this.execute(actionConstructor.create(null));
            return;
        }

        //the command needs to be created with arguments
        this.view.setState(new CommandCreationState(this, actionConstructor));
    }
}