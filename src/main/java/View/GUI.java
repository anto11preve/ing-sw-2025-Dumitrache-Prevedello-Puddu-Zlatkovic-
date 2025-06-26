package View;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public abstract class GUI extends View {
    private Application application = new Application() {
        @Override
        public void start(Stage stage) throws Exception {

        }
    };

    @Override
    public void run() {
        application.launch();
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
