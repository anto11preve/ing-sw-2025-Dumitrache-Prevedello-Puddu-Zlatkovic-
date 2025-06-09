package Controller.Commands;

import Controller.Controller;
import Controller.Exceptions.InvalidCommand;
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
     * @param gameID the ID of the game session
     * @param planetName the name of the planet to choose
     */
    public ChoosePlanetCommand(String playerName, int gameID, String planetName) {
        super(playerName, gameID);
        this.planetName = planetName;
    }
    
    /**
     * Executes the choose planet command by calling the controller's choosePlanet method.
     *
     * @param controller the controller to execute the command on
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.choosePlanet(getPlayerName(), planetName);
        } catch (InvalidCommand | InvalidParameters e) {
            System.err.println("Failed to choose planet: " + e.getMessage());
        }
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