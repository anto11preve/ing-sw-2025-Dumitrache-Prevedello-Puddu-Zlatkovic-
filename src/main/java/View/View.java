package View;


import View.States.MenuStates.ChooseActionState;
import View.States.MenuStates.MenuState;
import View.States.ViewNothingState;
import View.States.ViewState;

import java.util.List;
import java.util.Map;

public abstract class View implements Runnable {
    private ViewState state = new ViewNothingState();
    private MenuState menuState = new ChooseActionState();

    public void setState(ViewState state){
        this.state = state;
    }

    public ViewState getState(){
        return this.state;
    }

    public void setMenuState(MenuState menuState){
        this.menuState = menuState;
    }

    public MenuState getMenuState(){
        return this.menuState;
    }

    public void repaint() {
        this.state.paint();
        this.menuState.paint();
    }

    public abstract void log(String message);

    public abstract void showOptions(String prompt, List<String> options);

    public abstract void showArguments(List<String> arguments, Map<String, String> providedArguments);
}
