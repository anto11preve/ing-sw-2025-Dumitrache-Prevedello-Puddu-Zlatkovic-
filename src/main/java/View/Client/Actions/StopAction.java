package View.Client.Actions;

import View.Client.ClientState;

import java.util.Map;
import java.util.Set;

public class StopAction implements Action {

    @Override
    public ClientState execute(ClientState state) {
        return state.stop();
    }

    static public ActionConstructor getConstructor() {
        return new ActionConstructor() {

            @Override
            public StopAction create(Map<String, String> args) {
                return new StopAction();
            }

            @Override
            public Set<String> getArguments() {
                return null;
            }
        };
    }
}
