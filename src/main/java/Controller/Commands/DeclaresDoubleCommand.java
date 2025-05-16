package Controller.Commands;

import Controller.Enums.DoubleType;

public class DeclaresDoubleCommand extends Command {
    DoubleType doubleType;
    int amount;
    @Override
    public void execute(Controller.Controller controller) {
        controller.declaresDouble();
    }
}
