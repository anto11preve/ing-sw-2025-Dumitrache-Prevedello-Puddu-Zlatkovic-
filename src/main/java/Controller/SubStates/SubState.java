package Controller.SubStates;

import Controller.State;

public abstract class SubState {
    protected State parentState;


    public State getParentState() {
        return parentState;
    }
}
