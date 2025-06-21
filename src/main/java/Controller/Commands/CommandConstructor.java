package Controller.Commands;

import java.util.List;
import java.util.Map;

public interface CommandConstructor {
    /**
     * Method that creates a new Command based on a map of argName->argValue
     *
     * @param username
     * @param args     the map
     * @return Command
     * @throws IllegalArgumentException if there is something wrong with the arguments
     */
    Command create(String username, Map<String, String> args) throws IllegalArgumentException;

    /**
     *
     * @return list of argument names
     */
    List<String> getArguments();

    /**
     * Map that holds all commands that exist
     */
    Map<String, CommandConstructor> commandConstructors = Map.ofEntries(
            "ChoosePlanet", ChoosePlanetCommand.getConstructor(),
            "DeclaresDoubleCannon", DeclaresDoubleCommand.getCannonConstructor(),
            "DeclaresDoubleEngine", DeclaresDoubleCommand.getEngineConstructor(),
            "DeleteComponent", DeleteComponentCommand.getConstructor(),
            "End", EndCommand.getConstructor(),
            "FinishBuilding", FinishBuildingCommand.getConstructor(),
            "FlipHourGlass", FlipHourGlassCommand.getConstructor(),
            "GetComponent", GetComponentCommand.getConstructor(),
            "GetGood", GetGoodCommand.getConstructor(),
            "GetGoodReward", GetRewardCommand.getGoodsConstructor(),
            "GetCreditsReward", GetRewardCommand.getCreditsConstructor(),
            "LeaveRace", LeaveRaceCommand.getConstructor(),
            "Login", LoginCommand.getConstructor(),
            "Logout", LogoutCommand.getConstructor(),
            "LookDeck", LookDeckCommand.getConstructor(),
            "MoveGood", MoveGoodCommand.getConstructor(),
            "PickNextCard", PickNextCardCommand.getConstructor(),
            //TODO: "PlaceComponent" command is not implemented yet
            "PlaceBrownAlien", PlaceCrewCommand.getBrownConstructor(),
            "PlacePurpleAlien", PlaceCrewCommand.getPurpleConstructor(),
            "PlaceHuman", PlaceCrewCommand.getHumanConstructor(),
            "ReserveComponent", ReserveComponentCommand.getConstructor(),
            "SkipReward", SkipRewardCommand.getConstructor(),
            "StartTrialGame", StartGameCommand.getTrialConstructor(),
            "StartLvL2Game", StartGameCommand.getLevel2Constructor(),
            "ThrowDices", ThrowDicesCommand.getConstructor(),
            "UseBatterie", UseItemCommand.getBatteriesConstructor(),
            "UseCrew", UseItemCommand.getCrewConstructor()


    );
}
