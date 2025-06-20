package View.Client.Actions;

import View.Client.ClientState;

import java.util.List;
import java.util.Map;

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
            public List<String> getArguments() {
                return null;
            }
        };
    }
}
