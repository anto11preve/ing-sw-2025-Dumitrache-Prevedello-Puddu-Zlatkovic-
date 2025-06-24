package View.Client.Actions;

import Controller.Enums.MatchLevel;
import View.Client.ClientState;

import java.util.List;
import java.util.Map;

public class CreateGameAction implements Action {
    private final MatchLevel matchLevel;

    public CreateGameAction(MatchLevel matchLevel) {
        this.matchLevel = matchLevel;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.create(matchLevel);
    }

    static public ActionConstructor getConstructor() {
        return new ActionConstructor() {
            @Override
            public CreateGameAction create(Map<String, String> args) throws IllegalArgumentException {
                final MatchLevel matchLevel = MatchLevel.valueOf(args.get("MatchLevel").toUpperCase());

                return new CreateGameAction(matchLevel);
            }

            @Override
            public List<String> getArguments() {
                return List.of("MatchLevel");
            }
        };
    }
}
