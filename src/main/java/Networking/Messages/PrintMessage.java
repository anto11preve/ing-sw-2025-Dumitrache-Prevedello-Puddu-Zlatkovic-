package Networking.Messages;

public class PrintMessage implements Message {
    private final String command;

    public PrintMessage(String command) {
        this.command = command;
    }

    @Override
    public void handle() {
        System.out.println(command);
    }
}
