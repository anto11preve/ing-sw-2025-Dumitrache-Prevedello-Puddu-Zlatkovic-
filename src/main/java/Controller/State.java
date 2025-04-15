package Controller;

import java.util.Map;

public abstract class State {
    public void execute(Map<String, Object> command, Controller controller){}
}
