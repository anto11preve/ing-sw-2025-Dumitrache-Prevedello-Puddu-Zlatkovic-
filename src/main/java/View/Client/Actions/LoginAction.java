package View.Client.Actions;

import View.Client.ClientState;

import java.util.Map;
import java.util.Set;

public class LoginAction implements Action {
    private final String username;

    public LoginAction(String username) {
        this.username = username;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.login(username);
    }

    public static ActionConstructor getConstructor() {
        return new ActionConstructor() {

            @Override
            public Action create(Map<String, String> args) throws IllegalArgumentException {
                return new LoginAction(args.get("username"));
            }

            @Override
            public Set<String> getArguments() {
                return Set.of("username");
            }
        };
    }
}
