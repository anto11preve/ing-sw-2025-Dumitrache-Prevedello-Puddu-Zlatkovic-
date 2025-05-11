package Controller.Commands;

import Controller.Controller;

public class DrawCardCommand extends Command{
    @Override
    public void execute(Controller controller) {
        controller.DrawCard();
    }
}
