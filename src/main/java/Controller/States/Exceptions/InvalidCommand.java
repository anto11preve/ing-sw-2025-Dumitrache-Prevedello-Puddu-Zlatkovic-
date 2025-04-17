package Controller.States.Exceptions;

public class InvalidCommand extends RuntimeException {
  public InvalidCommand(String message) {
    super(message);
  }
}
