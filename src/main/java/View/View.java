package View;


import View.States.ViewState;

import java.util.List;
import java.util.Map;

public interface View extends Runnable {
    void setState(ViewState state);
    ViewState getState();
    void log(String message);

    void showOptions(String prompt, List<String> options);

    void showArguments(List<String> arguments, Map<String, String> providedArguments);
}
