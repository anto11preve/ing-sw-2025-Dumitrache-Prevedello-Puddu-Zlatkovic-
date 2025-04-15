package Controller;

import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;

import java.util.Map;

public class RemoveCrewState extends State{
    Controller controller;
    int crew;
    Player player;

    public RemoveCrewState(Controller controller, int crew, Player player) {
        this.controller = controller;
        this.crew = crew;
        this.player = player;
    }

    @Override
    public void execute(Map<String, Object> command, Controller controller){
        String action = (String) command.get("command");
        String playerName = (String) command.get("playerName");

        switch (action){
            case "RemoveCrew":
                if(playerName.equals(player.getName())){
                    if(crew > 0){
                        Cabin cabin = (Cabin) command.get("cabin");
                        switch (cabin.getOccupants()){
                            case SINGLE_HUMAN, BROWN_ALIEN, PURPLE_ALIEN:
                                cabin.setOccupants(Crewmates.EMPTY);
                                break;
                            case DOUBLE_HUMAN:
                                cabin.setOccupants(Crewmates.SINGLE_HUMAN);
                                break;
                            default:
                                break;
                        }
                        controller.setState(new RemoveCrewState(controller, crew, player));
                    }
                    else{
                        controller.setState(new FlightPhase());
                    }
                }
                }
        }
}
