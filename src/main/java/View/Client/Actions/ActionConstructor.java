package View.Client.Actions;

import Controller.Commands.Command;
import Controller.Commands.CommandConstructor;
import Networking.Messages.ControllerMessage;
import Networking.Messages.UpdateListMessage;
import View.Client.Client;
import View.Client.ClientState;
import View.Client.Visualizes.ViewComponent;
import View.Client.Visualizes.ViewShipBoard;
import View.Client.Visualizes.Visualize;
import View.States.*;

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
            /*Actions*/
            actionConstructors.putAll(Map.of(
                    "Stop", _ -> ClientState::stop,
                    "RMI", _ -> state -> state.chooseProtocol("RMI"),
                    "TCP", _ -> state -> state.chooseProtocol("TCP"),
                    "Connect", ConnectAction.getConstructor(),
                    "Login", LoginAction.getConstructor(),
                    "Join", JoinAction.getConstructor(),
                    "Create", CreateGameAction.getConstructor(),
                    "UpdateList", _ -> (Action) state -> state.send(new UpdateListMessage())
            ));

            /*Visualizers*/
            actionConstructors.putAll(Map.of(
                    "BackToShip", _ -> (Visualize) () -> Client.view.getState().backToShip(),
                    "ViewCard", _ -> (Visualize) ViewCardState::new,
                    "ViewComponent", ViewComponent.getConstructor(),
                    "ViewFlightBoard", _ -> (Visualize) ViewFlightBoardState::new,
                    "ViewShip", ViewShipBoard.getConstructor(),
                    "ViewTiles", _ -> (Visualize) ViewTilesState::new,
                    "ViewRewards", _ -> (Visualize) ViewRewardsState::new
            ));

            /*Controller commands*/
            for(String command : CommandConstructor.getCommandConstructors().keySet()){
                CommandConstructor constructor = CommandConstructor.getCommandConstructor(command);
                actionConstructors.put(command, new ActionConstructor() {
                    @Override
                    public Action create(Map<String, String> args) throws IllegalArgumentException {
                        final Command command = constructor.create(Client.client.getState().getUsername(), args);
                        return state -> state.send(new ControllerMessage(command));
                    }

                    @Override
                    public List<String> getArguments() {
                        return constructor.getArguments();
                    }
                });
            }
        }

        return actionConstructors;
    }

    static ActionConstructor getActionConstructor(String action) {
        return getActionConstructors().get(action);
    }
}
