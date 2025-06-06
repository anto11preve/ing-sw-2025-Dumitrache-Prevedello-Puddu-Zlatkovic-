package Controller.PreMatchLobby;

import Controller.Controller;
import Controller.Exceptions.*;
import Controller.RealTimeBuilding.BuildingState;
import Controller.State;
import Model.Player;

public class LogInState extends State {

//    @Override
//    public void execute(Map<String, Object> command, Controller controller) {
//        // Handle the lobby phase actions here
//        String action = (String) command.get("action");
//        switch (action) {
//            case "PlayerConnected":
//                String playerName = (String) command.get("playerName");
//                Game game = controller.getModel();
//                game.addPlayer(playerName); //il controllo è gia nel model
//                break;
//            case "StartConstruction":
//                if(controller.getModel().getPlayers().size() == 4)
//                    controller.getModel().setState(new BuildingState());
//                break;
//
//            case "Testing":
//                System.out.println("Testing Command");
//                System.out.println("Name: " + command.get("playerName"));
//                System.out.println("Age: " + command.get("Age"));
//                break;
//            default:
//                //do nothing
//                break;
//        }
//    }

    public LogInState(Controller controller){
        super(controller);
    }

    @Override
    public void login(String name) throws InvalidParameters, InvalidCommand{

        Player test= this.getController().getModel().getPlayer(name);
        if(test != null){
            throw new InvalidParameters("Player with this name already connected");
        }
        if(this.getController().getModel().getPlayers().size() == 4){
            throw new InvalidParameters("Game is full");
        }

        this.getController().getModel().addPlayer(name);

        // Handle the login action here
        System.out.println("Player " + name + " logged in to game " + this.getController().getGameID());
        // You can add more logic here if needed
    }

    @Override
    public void logout(String name)throws InvalidParameters {

        Player removed_player= this.getController().getModel().getPlayer(name);
        if(removed_player == null){
            throw new InvalidParameters("Player with this name not connected");
        }
        this.getController().getModel().removePlayer(name);
        if(this.getController().getModel().getPlayers().isEmpty()){

            //qui andrebbe eliminata dalla lista partite accedibili
            this.getController().setState(new OffState(this.getController()));
        }
        System.out.println("Player " + name + " logged out");
        // You can add more logic here if needed
    }

    @Override
    public void startGame(String name) throws InvalidParameters, InvalidCommand {
        Player admin= this.getController().getModel().getPlayers().get(0);
        if(!admin.getName().equals(name)){
            throw new InvalidParameters("Only the admin can start the game");
        }

        if(this.getController().getModel().getPlayers().size() < 2){
            throw new InvalidCommand("Not enough players to start the game");
        }

        this.getController().setState(new BuildingState(this.getController()));

        System.out.println("Game started by admin " + name);
        // You can add more logic here if needed
    }
}
