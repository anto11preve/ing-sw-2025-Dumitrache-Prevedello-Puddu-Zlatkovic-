package View.Client.Actions;

import View.Client.ClientState;

public class UpdateListAction implements Action {
    private final Integer[] newList;

    public UpdateListAction(Integer[] newList) {
        this.newList = newList;
    }

    @Override
    public ClientState execute(ClientState state) {
        return state.updateList(newList);
    }
}
