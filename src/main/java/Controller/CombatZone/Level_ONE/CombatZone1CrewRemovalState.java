package Controller.CombatZone.Level_ONE;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Coordinates;

public class CombatZone1CrewRemovalState extends State {
    private Context context;

    public CombatZone1CrewRemovalState(Context context) {
        this.context = context;
        this.setPlayerInTurn(context.getPlayers().getFirst());
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidContextualAction {
        Controller controller = context.getController();
        if(itemType != ItemType.CREW){
            controller.getModel().setError(true);
            throw new IllegalArgumentException("Invalid item type for crew removal.");
        }
        Player player = controller.getModel().getPlayer(playerName);
        if(player.equals(context.getPlayers().getFirst())){
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

                controller.getModel().setState(new CombatZone1CrewRemovalState(context));
                controller.getModel().setError(false);
            }
            else{
                controller.getModel().setState(new CombatZone1PowerDeclarationState(context));
                controller.getModel().setError(false);
            }
        } else {
            controller.getModel().setError(true);
            throw new IllegalArgumentException("It's not your turn to remove crew members.");
        }
    }

}
