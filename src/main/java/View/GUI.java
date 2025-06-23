package View;
import View.States.ViewState;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class GUI extends Application implements View {
    private ViewState state = null;
    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

    }

    @Override
    public void setState(ViewState state) {
        this.state = state;
    }

    @Override
    public ViewState getState() {
        return this.state;
    }

    @Override
    public void log(String message) {
        /*TODO: implement this*/
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void showOptions(String prompt, List<String> options){
        /*TODO: implement this*/
    }

    @Override
    public void showArguments(List<String> arguments, Map<String, String> providedArguments){

    }
}
