package View;

import View.States.ViewState;

import java.io.Console;
import java.util.List;

public final class TUI implements View {
    private ViewState state;
    private final Console console = System.console();

    @Override
    public synchronized void setState(ViewState state) {
        this.state = state;
    }

    @Override
    public synchronized ViewState getState() {
        return this.state;
    }

    @Override
    public void log(String message) {
        System.err.println(message);
    }

    @Override
    public void showOptions(String prompt, List<String> options){
        System.out.println(prompt);
        for(String option : options){
            System.out.println(option);
        }
    }

    @Override
    public void showArguments(List<String> arguments){
        for(String argument : arguments){}

    }

    @Override
    public void run() {
        new Thread(() -> {
            String line;
            while (!TUI.this.getState().isDone()){
                line = console.readLine();
                TUI.this.getState().callback(line);
            }
        }).start();

    }
}
