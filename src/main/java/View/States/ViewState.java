package View.States;

public interface ViewState {
    default boolean isDone(){
        return false;
    }

    void callback(String line);
}
