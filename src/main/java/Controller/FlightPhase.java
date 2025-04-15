package Controller;

import Model.Board.AdventureCards.AdventureCard;
import Model.Board.CardDeck;

import java.util.Map;

public class FlightPhase extends State {
    @Override
    public void execute(Map<String, Object> command, Controller controller) {
        // Handle the flight phase actions here
        String action = (String) command.get("action");
        switch (action) {
            case "KeepThatAsShip":
                // Handle takeoff action
                break;
            case "QuitFlight":
                // Handle landing action
                break;
            case "NewCard":
                CardDeck deck;
                deck = controller.getModel().getFlightboard().getUpcomingCardDeck();
                if(!deck.peekCards().isEmpty()) {
                    AdventureCard card = deck.popCard();
                    card.accept(new CardResolverVisitor(), controller);
                }
                break;
            default:
                //do nothing
                break;
        }
    }
}
