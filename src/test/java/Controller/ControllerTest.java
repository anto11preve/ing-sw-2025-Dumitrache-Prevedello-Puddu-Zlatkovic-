package Controller;

import Controller.Enums.MatchLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getModel() {

        //Controller controllerTrial=new Controller(MatchLevel.LEVEL2, 1);
        Controller controller2=new Controller(MatchLevel.LEVEL2, 2);
        assertNotNull(controller2.getModel());


    }

    @Test
    void getGameID() {
    }

    @Test
    void enqueueCommand() {
    }

    @Test
    void dequeueCommand() {
    }

    @Test
    void getMatchLevel() {
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void startGame() {
    }

    @Test
    void getComponent() {
    }

    @Test
    void reserveComponent() {
    }

    @Test
    void placeComponent() {
    }

    @Test
    void lookDeck() {
    }

    @Test
    void flipHourGlass() {
    }

    @Test
    void finishBuilding() {
    }

    @Test
    void placeCrew() {
    }

    @Test
    void pickNextCard() {
    }

    @Test
    void deleteComponent() {
    }

    @Test
    void leaveRace() {
    }

    @Test
    void getReward() {
    }

    @Test
    void moveGood() {
    }

    @Test
    void useItem() {
    }

    @Test
    void declaresDouble() {
    }

    @Test
    void end() {
    }

    @Test
    void choosePlanet() {
    }

    @Test
    void skipReward() {
    }

    @Test
    void getGood() {
    }

    @Test
    void throwDices() {
    }

    @Test
    void run() {
    }
}