package View.Client.Actions;

import Model.Game;
import View.Client.ClientState;

public class JoinSuccessAction implements Action {
    private final Game game;

    public JoinSuccessAction(Game game) {
        this.game = game;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.net_JoinSuccess(game);
    }
}
