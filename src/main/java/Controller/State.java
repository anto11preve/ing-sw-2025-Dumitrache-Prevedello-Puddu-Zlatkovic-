package Controller;

import Controller.Enums.*;
import Controller.Exceptions.*;
import Model.Ship.Coordinates;

//import java.util.Map;

public abstract class State {

    private final Controller controller;

    public Controller getController() {
        return controller;
    }

    public State(Controller controller) {
        this.controller = controller;
    }

    public void login(String name, int gameID) throws InvalidCommand, InvalidParameters{
        throw new InvalidCommand("Invalid command: login");
    }
    public void logout(String name) throws InvalidCommand {
        throw new InvalidCommand("Invalid command: logout");
    }
    public void startGame(String Name) throws InvalidCommand, InvalidParameters{
        throw new InvalidCommand("Invalid command: startGame");
    }

    public void getComponent(String name, int index) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: getComponent");
    }
    public void reserveComponent(String name) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: reserveComponent");
    }
    public void placeComponent(String name, ComponentOrigin origin, Coordinates coordinates, int orientation) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: placeComponent");
    }
    public void lookDeck(String name, int index) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: lookDeck");
    }
    public void flipHourGlass(String name) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: flipHourGlass");
    }
    public void finishBuilding(String name, int position) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: finishBuilding");
    }
    public void placeCrew(String name, Coordinates coordinates, CrewType type) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: placeCrew");
    }



    // Adventure Card resolution
    public void pickNextCard(String name) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: pickNextCard");
    }

    public void deleteComponent(String name, Coordinates coordinates) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: deleteComponent");
    }
    public void leaveRace(String name) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: leaveRace");
    }
    public void getReward(String name, RewardType rewardType) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: getReward");
    }
    //
    public void moveGoods(String name) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: moveGoods");
    }
    public void useItem(String name, ItemType itemType, Coordinates coordinates, int amount) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: useItem");
    }
    public void declaresDouble(String name, DoubleType doubleType, int amount) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: declaresDouble");
    }
    public void defend(String name) throws InvalidCommand, InvalidParameters {
        throw new InvalidCommand("Invalid command: defend");
    }


}
