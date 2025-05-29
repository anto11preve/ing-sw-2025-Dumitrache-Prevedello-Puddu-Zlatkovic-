package Controller.Exceptions;

public class InvalidContextualAction extends RuntimeException {
    public InvalidContextualAction(String message) {
        super(message);
    }
}
