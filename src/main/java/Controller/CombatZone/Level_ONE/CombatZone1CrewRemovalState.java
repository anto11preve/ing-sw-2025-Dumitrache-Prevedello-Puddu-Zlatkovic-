package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombatZone1CrewRemovalState extends State {
   

    public CombatZone1CrewRemovalState(Context context) {
        super(context);
        this.setPlayerInTurn(context.getSpecialPlayers().getFirst());
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidContextualAction, InvalidParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.CREW){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid item type for crew removal.");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if(player.equals(context.getSpecialPlayers().getFirst())){
            if(player.getShipBoard().getCondensedShip().getTotalCrew() < context.getCrewmates()){
                controller.getModel().setError(true);
                throw new InvalidContextualAction("The player doesn't have enough crew"); //handle the situation where the player doesn't have enough crew
            }
            if(context.getCrewmates() > 0){
                Cabin cabin = (Cabin) player.getShipBoard().getComponent(coordinates);
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
                context.removeCrewmate();

                if(context.getCrewmates() == 0){
                    List<Player> allPlayers= new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
                    context.setPlayers(allPlayers);
                    controller.getModel().setState(new CombatZone1PowerDeclarationState(context));
                    controller.getModel().setError(false);
                } else {
                    controller.getModel().setState(new CombatZone1CrewRemovalState(context));
                    controller.getModel().setError(false);
                }
            }
            else{
                List<Player> allPlayers= new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
                context.setPlayers(allPlayers);
                controller.getModel().setState(new CombatZone1PowerDeclarationState(context));
                controller.getModel().setError(false);
            }
        } else {
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn to remove crew members.");
        }
    }

    public List<String> getAvailableCommands(){
        return List.of(
            "UseCrew"
        );
    }

}
