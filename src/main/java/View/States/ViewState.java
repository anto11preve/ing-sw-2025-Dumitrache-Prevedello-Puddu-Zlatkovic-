package View.States;

import Model.Ship.Coordinates;

import java.util.List;

public interface ViewState {
    default ViewState viewComponent(Coordinates coordinates) {
        throw new UnsupportedOperationException("Cannot invoke viewComponent on " + this.getClass().getSimpleName());
    }

    default List<String> getAvailableVisualizers() {
        return List.of();
    }

    void paint();
}
