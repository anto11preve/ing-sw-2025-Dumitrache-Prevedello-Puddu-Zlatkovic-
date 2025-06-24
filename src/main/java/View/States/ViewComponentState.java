package View.States;

import Model.Ship.Components.SpaceshipComponent;
import View.Client.Client;

public class ViewComponentState implements ViewState {
    final SpaceshipComponent component;

    public ViewComponentState(SpaceshipComponent component) {
        this.component = component;
    }

    @Override
    public void paint() {
        Client.client.getState().viewComponent(component);
    }
}
