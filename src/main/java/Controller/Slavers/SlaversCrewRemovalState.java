package Controller.Slavers;

import Controller.Context;
import Controller.Controller;
import Controller.Enums.ItemType;
import Controller.Exceptions.InvalidParameters;
import Model.Enums.Crewmates;
import Model.Player;
import Model.Ship.Components.Cabin;
import Model.Ship.Coordinates;
import Controller.State;
import Controller.GamePhases.FlightPhase;

import java.util.List;

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
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) throws InvalidParameters {
        Controller controller = context.getController();
        if(itemType != ItemType.CREW){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid item type, expected CREW");
        }

        if(coordinates == null){
            controller.getModel().setError(true);
            throw new InvalidParameters("Invalid coordinates");
        }

        Player player = controller.getModel().getPlayer(playerName);
        if(player == context.getSpecialPlayers().getFirst()){
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

                controller.getModel().setState(new SlaversCrewRemovalState(context));
                controller.getModel().setError(false);
            }
            else{
                context.removeSpecialPlayer(player);
                if(context.getPlayers().isEmpty()){         //passati tutti
                    controller.getModel().setState(new FlightPhase(controller)); //tutti i giocatori gestiti
                    controller.getModel().setError(false);
                }
                else{       //manca qualcuno da gestire
                    controller.getModel().setState(new SlaversCrewRemovalState(context)); //manca qualcuno da gestire
                    controller.getModel().setError(false);
                }
            }
        }
    }

    public List<String> getAvailableCommands(){
        return List.of( "UseItem" );
    }
}
