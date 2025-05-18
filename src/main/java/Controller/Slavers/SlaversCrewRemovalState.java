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

/**
 * This state handles the forced removal of crewmates from players' ships during
 * the "Slavers" encounter, specifically for players who failed to surpass the slavers' power.
 * Players must remove crew members until they have no remaining required removals.
 */
public class SlaversCrewRemovalState extends State {
    /**
     * Context object that holds the game state and player information.
     */
    private Context context;

    /**
     * Constructor to initialize the state with the current game context.
     *
     * @param context the shared game context
     */
    public SlaversCrewRemovalState(Context context) {
        this.context = context;
    }

    /**
     * Allows the current player to remove a crew member from one of their cabins.
     * The player must be the current one in turn, and a valid {@link Cabin} must be targeted.
     *
     * After a crew member is removed, if more removals are required, the state refreshes.
     * Otherwise, the player is removed from the special players list and:
     * <ul>
     *     <li>If all players have been processed, the game transitions to {@link FlightPhase}</li>
     *     <li>Otherwise, the state remains to process the next player</li>
     * </ul>
     *
     * @param playerName  the name of the player attempting to remove a crew member
     * @param itemType    the item type used (must be {@code CREW})
     * @param coordinates the coordinates of the cabin containing the crew member
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) {
        if(itemType != ItemType.CREW){
            return;
        }

        if(coordinates == null){
            return; // Handle the case where coordinates are null
        }

        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(player == controller.getModel().getFlightBoard().getTurnOrder()[0]){
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

                controller.setState(new SlaversCrewRemovalState(context));
            }
            else{
                context.removeSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.setState(new FlightPhase(controller)); //tutti i giocatori gestiti
                }
                else{       //manca qualcuno da gestire
                    controller.setState(new SlaversCrewRemovalState(context)); //manca qualcuno da gestire
                }
            }
        }
    }
}
