package View.Client.Actions;

import Model.Game;
import View.Client.ClientState;

public class UpdateGameAction implements Action {
    private final Game game;

    public UpdateGameAction(Game game) {
        this.game = game;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.updateGame(this.game);
    }
}
