package Controller;

import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;

import java.util.Map;

public class BuildingState extends State {

    public BuildingState(Controller controller) {
        super(controller);
    }

    @Override
    public void getComponent(String name, int index) throws InvalidCommand, InvalidParameters {

    }
}
