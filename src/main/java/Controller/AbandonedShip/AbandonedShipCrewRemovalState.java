package Controller.AbandonedShip;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.State;
import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Coordinates;
import Controller.GamePhases.FlightPhase;


public class AbandonedShipCrewRemovalState extends State {
    private Context context;

    public AbandonedShipCrewRemovalState(Context context) {
        this.context = context;
    }

    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) {
        if(itemType != ItemType.CREW){
            return;
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(playerName.equals(player.getName())){
            if(context.getCrewmates() > 0){
                Cabin cabin = (Cabin) player.getShipBoard().getComponent(coordinates.getX(), coordinates.getY());
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

                controller.setState(new AbandonedShipCrewRemovalState(context));
            }
            else{
                controller.setState(new FlightPhase());
            }
        }
    }
}
