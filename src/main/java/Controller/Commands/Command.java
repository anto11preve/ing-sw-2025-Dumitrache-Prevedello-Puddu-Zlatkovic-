package Controller.Commands;

import Controller.Controller;

public abstract class Command {
    String name;
    int gameID;

    public void execute(Controller controller) {
        // Implement the command execution logic here
    }

    public String getName() {
        return name;
    }

    public int getGameID() {
        return gameID;
    }

}
