package View.Client.Actions;

import java.util.Map;
import java.util.Set;

public interface ActionConstructor {
    Action create(Map<String, String> args) throws IllegalArgumentException;

    Set<String> getArguments();

    Map<String, ActionConstructor> actionConstructors = Map.of(
            "connect", ConnectAction.getConstructor(),
            "login", LoginAction.getConstructor(),
            "stop", StopAction.getConstructor()
    );
}
