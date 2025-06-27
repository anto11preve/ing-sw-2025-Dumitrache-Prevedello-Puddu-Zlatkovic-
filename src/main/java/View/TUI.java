package View;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TUI (Text User Interface) class that extends the View class.
 * This class provides a text-based user interface for the application,
 * allowing users to interact with the system through console input and output.
 */
public final class TUI extends View {

    /**
     * The console used for input and output operations.
     * This console is used to read user input and display messages in the terminal.
     */
    private final Console console = System.console();


    /**
     * Abstract method to repaint the view.
     * This method should be implemented by subclasses to define how the view should be redrawn.
     * It typically clears the console and redraws the current state of the application.
     *
     */
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

    /**
     * Logs a message to the console.
     * This method is used to display messages or logs in the console output.
     *
     * @param message The message to be logged.
     */
    @Override
    public void log(String message) {
        System.err.println(message);
    }

    //TODO: DOCUMENTATION
    @Override
    public void showOptions(String prompt, List<String> options){
        System.out.println(prompt + ":");
        for(String option : options){
            System.out.println("- " + option);
        }
    }

    /**
     * Displays a list of arguments with their provided values.
     * This method is used to show the arguments that need to be filled by the user.
     *
     * @param arguments The list of argument names.
     * @param providedArguments A map containing the provided values for each argument.
     */
    @Override
    public void showArguments(List<String> arguments, Map<String, String> providedArguments){
        final List<String> list = new ArrayList<>();
        for(String argName : arguments){
            final String argValue = providedArguments.get(argName);
            list.add(argName + ": " + (argValue == null ? "_empty_": argValue));
        }

        this.showOptions("Fill arguments: argName argValue (type \"send\" to finish)", list);
    }

    /**
     * Starts the TUI by creating a new thread to handle console input.
     * This method allows the TUI to run asynchronously, listening for user input
     * while the application is running.
     */
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
