package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

public class SlaversCrewRemovalState extends State {
    private Context context;

    public SlaversCrewRemovalState(Context context) {
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

                controller.setState(new SlaversCrewRemovalState(context));
            }
            else{
                context.removeSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new FlightPhase()); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SlaversCrewRemovalState(context)); //manca qualcuno da gestire
                }
            }
        }
    }
}
