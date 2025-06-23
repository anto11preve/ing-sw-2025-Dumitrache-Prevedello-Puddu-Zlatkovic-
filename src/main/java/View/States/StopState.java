package View.States;

public class StopState implements ViewState {
    @Override
    public boolean isDone(){
        return true;
    }

    @Override
    public void callback(String line) {
        System.out.println("Cannot callback on stop state");
    }
}
