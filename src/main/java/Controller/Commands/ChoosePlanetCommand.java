package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;

/**
 * Command for choosing a planet to land on during planet encounters.
 * Players select which planet they want to visit for goods.
 */
public class ChoosePlanetCommand extends Command {
    /** The name of the planet to choose */
    private final String planetName;
    
    /**
     * Constructs a new ChoosePlanetCommand.
     *
     * @param playerName the name of the player choosing the planet
     * @param planetName the name of the planet to choose
     */
    public ChoosePlanetCommand(String playerName, String planetName) {
        super(playerName);
        this.planetName = planetName;
    }
    
    /**
     * Executes the choose planet command by calling the controller's choosePlanet method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) throws InvalidCommand, InvalidParameters, InvalidContextualAction {
        controller.choosePlanet(getPlayerName(), planetName);
    }
    
    /**
     * Gets the planet name.
     *
     * @return the planet name
     */
    public String getPlanetName() {
        return planetName;
    }
}