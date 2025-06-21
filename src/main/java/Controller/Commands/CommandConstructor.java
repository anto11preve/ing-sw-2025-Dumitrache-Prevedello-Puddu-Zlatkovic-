package Controller.Commands;

import java.util.List;
import java.util.Map;

public interface CommandConstructor {
    /**
     * Method that creates a new Command based on a map of argName->argValue
     * @param args the map
     * @return Command
     * @throws IllegalArgumentException if there is something wrong with the arguments
     */
    Command create(Map<String, String> args) throws IllegalArgumentException;

    /**
     *
     * @return list of argument names
     */
    List<String> getArguments();

    /**
     * Map that holds all commands that exist
     */
    Map<String, CommandConstructor> commandConstructors = Map.of(
            "FinishBuilding", FinishBuildingCommand.getConstructor()
    );
}
