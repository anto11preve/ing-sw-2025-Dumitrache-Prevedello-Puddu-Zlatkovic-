package Controller.PreMatchLobby;

import Controller.Controller;
import Controller.State;

import java.util.List;

public class OffState extends State {

    public OffState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public List<String> getAvailableCommands(){
        return List.of();
    }

}
