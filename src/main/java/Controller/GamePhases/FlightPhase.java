package Controller.GamePhases;

import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.Exceptions.InvalidParameters;
import Controller.State;
import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.CardDeck;
import Model.Exceptions.InvalidMethodParameters;
import Model.Player;
import Controller.CardResolverVisitor;
import View.Client.ClientState;

import java.util.List;

public class FlightPhase extends State {

    public FlightPhase(Controller controller) {
        super(controller);
        controller.setQueuedAction(ClientState::net_Fly);
    }

    @Override
    public void onEnter() {
        for(Player p: this.getController().getModel().getFlightBoard().getTurnOrder()){
            if(p.getShipBoard().getCondensedShip().getTotalCrew() == 0){
                this.getController().getModel().getFlightBoard().removePlayingPlayer(p);
            }

            CardDeck deck;
            deck = this.getController().getModel().getFlightBoard().getUpcomingCardDeck();
            if(deck.peekCards().isEmpty() || this.getController().getModel().getFlightBoard().getTurnOrder().length == 0){
                this.getController().getModel().setState(new RewardsPhase(this.getController()));
                this.getController().getModel().setError(false);
            }
        }
    }

    @Override
    public void pickNextCard(String playerName) throws InvalidContextualAction, InvalidParameters, InvalidMethodParameters {
        Controller controller = this.getController();
        Player currentPlayer = controller.getModel().getPlayer(playerName);
        if(!currentPlayer.equals(controller.getModel().getFlightBoard().getTurnOrder()[0])){
            controller.getModel().setError(true);
            throw new InvalidParameters("It's not your turn to pick the next card.");
        }
        CardDeck deck;
        deck = controller.getModel().getFlightBoard().getUpcomingCardDeck();
        AdventureCardFilip card = deck.popCard();
        card.accept(new CardResolverVisitor(), controller);
        controller.getModel().setError(false);
    }

    @Override
    public void leaveRace(String playerName){
        Controller controller = this.getController();
        Player player = controller.getModel().getPlayer(playerName);
        controller.getModel().getFlightBoard().removePlayingPlayer(player);
        controller.getModel().setState(new FlightPhase(controller));
    }

    public List<String> getAvailableCommands(){
        return List.of( "PickNextCard",
                        "LeaveRace");
    }
}