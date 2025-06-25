package View.States;

import Model.Ship.Components.SpaceshipComponent;
import Model.Ship.Coordinates;
import View.Client.Client;

public class ViewComponentState implements ViewState {
    private final String username;
    private final Coordinates coordinates;

    public ViewComponentState(String username, Coordinates coordinates) {
        this.username = username;
        this.coordinates = coordinates;
    }

    @Override
    public void paint() {
        Client.client.getState().viewComponent(username, coordinates);
    }
}
