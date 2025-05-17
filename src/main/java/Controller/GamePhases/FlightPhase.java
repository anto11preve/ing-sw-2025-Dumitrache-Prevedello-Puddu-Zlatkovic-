package Controller.GamePhases;

import Controller.Controller;
import Controller.State;
import Model.Board.AdventureCards.AdventureCard;
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

        // To do di Marco
    }

    @Override
    public void leaveRace(String playerName){


        // To do di Marco
    }
}