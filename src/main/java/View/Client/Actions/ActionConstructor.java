package View.Client.Actions;

import java.util.List;
import java.util.Map;

public interface ActionConstructor {
    Action create(Map<String, String> args) throws IllegalArgumentException;

    List<String> getArguments();

    Map<String, ActionConstructor> actionConstructors = Map.of(
            "connect", ConnectAction.getConstructor(),
            "login", LoginAction.getConstructor(),
            "stop", StopAction.getConstructor()
    );
}
