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


/**
 * Represents the state where players can remove crew members from their ship
 * after encountering an abandoned ship during the flight phase.
 *
 * <p>This state handles the logic for removing crew members from the ship.
 * If a player has crew members, they can remove them from the cabin.
 * If no crew members are left, the game transitions to the flight phase.</p>
 *
 * <p>Expected usage within a game state machine, with transitions depending on
 * player interactions.</p>
 */
public class AbandonedShipCrewRemovalState extends State {
    /** The shared context containing game state and references. */
    private Context context;

    /**
     * Constructs the state with the given context.
     *
     * @param context the game context used to access controller and shared state.
     */
    public AbandonedShipCrewRemovalState(Context context) {
        this.context = context;
    }

    /**
     * Called when a player wants to remove crew members from their ship.
     *
     * <p>If the item type is CREW, the player can remove crew members from the cabin.
     * If there are no crew members left required by the card, the game transitions to the flight phase.</p>
     *
     * <p>If the player does not have enough crew members, the method should return an error
     *
     * @param playerName the name of the player removing crewmates.
     * @param itemType   the type of item being used.
     * @param coordinates the coordinates of the cabin where crew members are removed.
     */
    @Override
    public void useItem(String playerName, ItemType itemType, Coordinates coordinates) {
        if(itemType != ItemType.CREW){
            return;
        }
        Controller controller = context.getController();
        Player player = controller.getModel().getPlayer(playerName);
        if(playerName.equals(player.getName())){
            if(player.getShipBoard().getCondensedShip().getTotalCrew() < context.getCrewmates()){
                return; //handle the situation where the player doesn't have enough crew
            }
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
