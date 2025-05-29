package Controller.GamePhases;

import Controller.Controller;
import Controller.Exceptions.InvalidContextualAction;
import Controller.State;
import Model.Board.AdventureCards.AdventureCard;
import Model.Board.AdventureCards.AdventureCardFilip;
import Model.Board.CardDeck;
import Model.Player;
import Controller.CardResolverVisitor;

import java.util.Map;

public class FlightPhase extends State {

    public FlightPhase(Controller controller) {
        super(controller);
    }

    @Override
    public void pickNextCard(String playerName) {
        Controller controller = this.getController();
        Player currentPlayer = controller.getModel().getPlayer(playerName);
        if(currentPlayer != controller.getModel().getFlightBoard().getTurnOrder()[0]){
            throw new IllegalArgumentException("It's not your turn to pick the next card.");
        }
        CardDeck deck;
        deck = controller.getModel().getFlightBoard().getUpcomingCardDeck();
        if(!deck.peekCards().isEmpty()) {
            AdventureCardFilip card = deck.popCard();
            card.accept(new CardResolverVisitor(), controller);
        } else {
            throw new InvalidContextualAction("No cards available to pick.");
        }
    }

    @Override
    public void leaveRace(String playerName){
        Controller controller = this.getController();
        controller.getModel().removePlayer(playerName);

        //
    }
}