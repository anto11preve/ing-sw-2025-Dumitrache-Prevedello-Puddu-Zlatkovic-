package Controller.GamePhases;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.AdventureCard;
import Model.Board.CardDeck;
import Model.Player;
import Controller.CardResolverVisitor;

import java.util.Map;

public class FlightPhase extends State {

    @Override
    public void pickNextCard(String playerName) {
        Controller controller = this.getController();
        Player currentPlayer = controller.getModel().getPlayer(playerName);
        if(currentPlayer != controller.getModel().getFlightBoard().getTurnOrder()[0]){
            return; // Handle the case where it's not the player's turn
        }
        CardDeck deck;
        deck = controller.getModel().getFlightBoard().getUpcomingCardDeck();
        if(!deck.peekCards().isEmpty()) {
            AdventureCard card = deck.popCard();
            card.accept(new CardResolverVisitor(), controller);
        }
    }

    @Override
    public void leaveRace(String playerName){
        Controller controller = this.getController();
        controller.getModel().removePlayer(playerName);

        //
    }
}
