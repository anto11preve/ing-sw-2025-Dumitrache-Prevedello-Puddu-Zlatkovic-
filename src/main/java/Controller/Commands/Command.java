package Controller.Commands;

import Controller.Controller;

public abstract class Command {
    private String PlayerName;
    private int GameID;

    public Command(String playerName, int gameID) {
        PlayerName = playerName;
        GameID = gameID;
    }
    public String getPlayerName() {
        return PlayerName;
    }
    public int getGameID() {
        return GameID;
    }
    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }
    public void setGameID(int gameID) {
        GameID = gameID;
    }
    public abstract void execute(Controller controller) throws Exception;

}
