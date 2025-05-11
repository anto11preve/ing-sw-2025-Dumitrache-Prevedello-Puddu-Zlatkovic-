package Controller;

import Controller.SubStates.SubState;

import java.util.Map;

public abstract class State {
    protected SubState state;

    public void setSubState(SubState state) {
        this.state = state;
    }

    public SubState getSubState() {
        return state;
    }


    public void execute(Map<String, Object> command, Controller controller){}
}
