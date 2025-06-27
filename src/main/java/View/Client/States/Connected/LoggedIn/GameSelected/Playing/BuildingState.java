package View.Client.States.Connected.LoggedIn.GameSelected.Playing;

import Model.Game;
import Networking.Network;
import View.Client.Client;
import View.Client.States.Connected.LoggedIn.GameSelected.PlayingState;
import View.States.ViewTilesState;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state of the game when a player is building.
 * This state allows the player to view tiles and perform other actions related to building.
 */
public final class BuildingState extends PlayingState {

    /**
     * Constructs a BuildingState with the given network, username, and game.
     *
     * @param network The network used for communication.
     * @param username The username of the player.
     * @param game The current game instance.
     */
    public BuildingState(Network network, String username, Game game) {
        super(network, username, game);
        Client.view.setState(new ViewTilesState());
    }

    /**
     * Returns a list of available commands in the BuildingState.
     * This includes the "ViewTiles" command and all commands from the parent class.
     *
     * @return A list of available commands.
     */
    @Override
    public List<String> getAvailableCommands() {
        List<String> commands = new ArrayList<>();

        commands.add("ViewTiles");

        commands.addAll(super.getAvailableCommands());

        return commands;
    }

    /* * Handles the "ViewTiles" command by rendering the game tiles.
     * This method is called when the player wants to view the tiles in the game.
     */
    /*Visualizers - TODO: make view agnostic*/
    @Override
    public void viewTiles() {
        this.getGame().render();
    }
}
