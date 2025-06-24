package View.Client.Actions;

import View.Client.ClientState;

import java.util.List;
import java.util.Map;

public class JoinAction implements Action {
    private final int gameId;

    public JoinAction(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.join(gameId);
    }

    public static ActionConstructor getConstructor() {
        return new ActionConstructor() {
            @Override
            public JoinAction create(Map<String, String> args) throws IllegalArgumentException {
                final int gameId;

                try {
                    gameId = Integer.parseInt(args.get("gameId"));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e);
                }

                return new JoinAction(gameId);
            }

            @Override
            public List<String> getArguments() {
                return List.of("gameId");
            }
        };
    }
}
