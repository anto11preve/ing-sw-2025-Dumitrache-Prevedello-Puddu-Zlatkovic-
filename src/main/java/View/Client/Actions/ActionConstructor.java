package View.Client.Actions;

import View.Client.ClientState;

import java.util.List;
import java.util.Map;

public interface ActionConstructor {
    Action create(Map<String, String> args) throws IllegalArgumentException;

    default List<String> getArguments(){
        return null;
    }

    Map<String, ActionConstructor> actionConstructors = Map.of(
            "stop", _ -> ClientState::stop,
            "RMI", _ -> state -> state.chooseProtocol("RMI"),
            "TCP", _ -> state -> state.chooseProtocol("TCP"),
            "connect", ConnectAction.getConstructor(),
            "login", LoginAction.getConstructor(),
            "list", _ -> ClientState::list,
            "join", JoinAction.getConstructor(),
            "create", CreateGameAction.getConstructor()
    );
}
