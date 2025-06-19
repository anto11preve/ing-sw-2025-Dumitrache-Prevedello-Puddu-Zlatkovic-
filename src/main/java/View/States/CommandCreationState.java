package View.States;

import View.Client.Actions.Action;
import View.Client.Actions.ActionConstructor;
import View.Client.Actions.EmptyAction;
import View.Client.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandCreationState implements ViewState {
    private final Client client;
    private final ActionConstructor actionConstructor;
    private final Set<String> requiredArguments;
    private final Map<String, String> providedArguments;

    public CommandCreationState(Client client, ActionConstructor actionConstructor) {
        this.client = client;
        this.actionConstructor = actionConstructor;

        this.requiredArguments = new HashSet<>(actionConstructor.getArguments());

        /*TODO: make this view agnostic*/
        System.out.println("Arguments are:");

        for(String arg : this.requiredArguments){
            System.out.println(arg);
        }

        this.providedArguments = new HashMap<>();
    }

    public void addArgument(String argName, String argValue){
        this.providedArguments.put(argName, argValue);
    }

    @Override
    public void callback(String line) {
        final String argName = line.split(" ")[0];
        final String argValue = line.split(" ")[1];
        System.out.println("callback of CommandCreationState");

        if(argName.equalsIgnoreCase("send")){
            this.send();
            return;
        }

        for(String arg : this.requiredArguments){
            if(argName.equalsIgnoreCase(arg)){
                this.addArgument(arg, argValue);
                return;
            }
        }
    }

    public void send() {
        Action action;

        try{
            action = this.actionConstructor.create(this.providedArguments);
        }catch (IllegalArgumentException e) {
            action = new EmptyAction();
        }

        this.client.execute(action);
    }
}
