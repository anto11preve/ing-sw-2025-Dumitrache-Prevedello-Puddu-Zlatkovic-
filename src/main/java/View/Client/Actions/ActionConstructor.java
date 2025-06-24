package View.Client.Actions;

import View.Client.ClientState;
import View.Client.Visualizes.ViewShipBoard;
import View.Client.Visualizes.Visualize;
import View.States.ViewFlightBoardState;
import View.States.ViewTilesState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ActionConstructor {
    Action create(Map<String, String> args) throws IllegalArgumentException;

    default List<String> getArguments(){
        return null;
    }

    Map<String, ActionConstructor> actionConstructors = new HashMap<>();

    static Map<String, ActionConstructor> getActionConstructors() {
        if(actionConstructors.isEmpty()) {
            actionConstructors.putAll(Map.of(
                    "stop", _ -> ClientState::stop,
                    "RMI", _ -> state -> state.chooseProtocol("RMI"),
                    "TCP", _ -> state -> state.chooseProtocol("TCP"),
                    "connect", ConnectAction.getConstructor(),
                    "login", LoginAction.getConstructor(),
                    "join", JoinAction.getConstructor(),
                    "create", CreateGameAction.getConstructor(),
                    "ViewFlightBoard", _ -> (Visualize) ViewFlightBoardState::new,
                    "ViewShipBoard", ViewShipBoard.getConstructor(),
                    "ViewTiles", _ -> (Visualize) ViewTilesState::new
            ));
        }

        return actionConstructors;
    }
}
