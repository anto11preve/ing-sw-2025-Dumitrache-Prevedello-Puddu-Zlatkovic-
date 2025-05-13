package Controller.GamePhases;

import java.util.Map;

import Controller.Controller;
import Controller.State;
import Model.Game;

public class LobbyPhase extends State {
    @Override
    public void execute(Map<String, Object> command, Controller controller) {
        // Handle the lobby phase actions here
        String action = (String) command.get("action");
        switch (action) {
            case "PlayerConnected":
                String playerName = (String) command.get("playerName");
                Game game = controller.getModel();
                game.addPlayer(playerName); //il controllo è gia nel model
                break;
            case "StartConstruction":
                if(controller.getModel().getPlayers().size() == 4)
                    controller.setState(new ConstructionPhase());
                break;

            case "Testing":
                System.out.println("Testing Command");
                System.out.println("Name: " + command.get("playerName"));
                System.out.println("Age: " + command.get("Age"));
                break;
            default:
                //do nothing
                break;
        }
    }
}
