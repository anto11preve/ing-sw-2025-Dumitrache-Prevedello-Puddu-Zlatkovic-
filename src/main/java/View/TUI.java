package View;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class TUI extends View {
    private final Console console = System.console();

    @Override
    public void repaint() {
        String clear = "";
        for(int i = 0; i < 30; i++){
            clear = clear + '\n';
        }
        System.out.print(clear);
        this.getState().paint();
        System.out.println();
        this.getMenuState().paint();
        System.out.print("> ");
    }

    @Override
    public void log(String message) {
        System.err.println(message);
    }

    @Override
    public void showOptions(String prompt, List<String> options){
        System.out.println(prompt + ":");
        for(String option : options){
            System.out.println("- " + option);
        }
    }

    @Override
    public void showArguments(List<String> arguments, Map<String, String> providedArguments){
        final List<String> list = new ArrayList<>();
        for(String argName : arguments){
            final String argValue = providedArguments.get(argName);
            list.add(argName + ": " + (argValue == null ? "_empty_": argValue));
        }

        this.showOptions("Fill arguments: argName argValue (type \"send\" to finish)", list);
    }

    @Override
    public void run() {
        new Thread(() -> {
            String line;
            while (!TUI.this.getMenuState().isDone()){
                line = console.readLine();
                TUI.this.getMenuState().callback(line);
            }
        }).start();

    }
}
