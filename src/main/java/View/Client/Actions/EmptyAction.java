package View.Client.Actions;

import View.Client.ClientState;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyAction implements Action {
    @Override
    public ClientState execute(ClientState state) {
        return state;
    }

    static public ActionConstructor getConstructor() {
        return new ActionConstructor() {
            @Override
            public Action create(Map<String, String> args) throws IllegalArgumentException {
                return new EmptyAction();
            }

            @Override
            public List<String> getArguments() {
                return List.of();
            }
        };
    }
}
