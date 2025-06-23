package View.States;

import View.Client.Actions.Action;
import View.Client.Actions.ActionConstructor;
import View.Client.Client;

import java.util.*;

public class ActionCreationState implements ViewState {
    private final ActionConstructor actionConstructor;
    private final Set<String> requiredArguments;
    private final Map<String, String> providedArguments;

    public ActionCreationState(ActionConstructor actionConstructor) {
        this.actionConstructor = actionConstructor;

        this.requiredArguments = new HashSet<>(actionConstructor.getArguments());

        this.providedArguments = new HashMap<>();

        this.listArguments();
    }

    public void listArguments(){
        final List<String> list = new ArrayList<>();
        for(String argName : this.actionConstructor.getArguments()){
            final String argValue = this.providedArguments.get(argName);
            list.add(argName + ": " + (argValue == null ? "_empty_": argValue));
        }

        Client.client.showOptions("arguments", list);
    }

    public void addArgument(String argName, String argValue){
        this.providedArguments.put(argName, argValue);
    }

    @Override
    public void callback(String line) {
        final String argName = line.split(" ")[0];
        System.out.println("callback of CommandCreationState");

        if(argName.equalsIgnoreCase("send")){
            this.send();
            return;
        }

        for(String arg : this.requiredArguments){
            if(argName.equalsIgnoreCase(arg)){
                try {
                    final String argValue = line.split(" ")[1];
                    this.addArgument(arg, argValue);
                }catch (ArrayIndexOutOfBoundsException e){
                    System.err.println("Warning: No argument was provided");
                }
                break;
            }
        }

        this.listArguments();
    }

    public void send() {
        Action action;

        try{
            action = this.actionConstructor.create(this.providedArguments);
        }catch (IllegalArgumentException e) {
            action = state -> state;
        }

        Client.client.execute(action);
    }
}
