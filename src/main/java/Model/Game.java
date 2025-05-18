package Model;

import Controller.Exceptions.InvalidCommand;
import Controller.Exceptions.InvalidParameters;
import Model.Board.FlightBoard;
import Model.Exceptions.InvalidMethodParameters;
import Model.Ship.Components.SpaceshipComponent;
import Controller.Enums.MatchLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final List<Player> players;
    private final SpaceshipComponent[] Tiles;
    //private List<SpaceshipComponent> hiddenComponents;
    //private List<SpaceshipComponent> visibleComponents;
    private FlightBoard flightBoard;

    public Game(MatchLevel matchLevel) {
        this.players = new ArrayList<>();
        this.Tiles = new SpaceshipComponent[156];
        //this.hiddenComponents = new ArrayList<>();
        //this.visibleComponents = new ArrayList<>();
        if(matchLevel==MatchLevel.TRIAL){
            this.flightBoard = new FlightBoard();
        } else if (matchLevel==MatchLevel.LEVEL2) {
            this.flightBoard = new FlightBoard();
        }
    }

    public Player getPlayer(String name){
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }
    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void removePlayer(String name) {
        players.remove(getPlayer(name));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int rollDice() {
        return new Random().nextInt(6) + 1;
    }

    public SpaceshipComponent pickComponent(int index) throws InvalidMethodParameters{
        if (index < 0 || index >= Tiles.length) {
            throw new InvalidMethodParameters("Invalid index");
        }
        SpaceshipComponent choosenComponent = Tiles[index];
        Tiles[index]=null;
        choosenComponent.setVisible();
        return choosenComponent;
    }

    public void addComponent(SpaceshipComponent component) {
        //visibleComponents.add(component);
        if(component!=null){
            int i=0;
            while(Tiles[i]!=null){
                i++;
            }
            Tiles[i]=component;
        }

    }


    public List<SpaceshipComponent> viewVisibleComponents() {
        List<SpaceshipComponent>visibleComponentsList=new ArrayList<>();
        for (SpaceshipComponent tile : Tiles) {
            if (tile != null && tile.isVisible()) {
                visibleComponentsList.add(tile);
            }
        }
        return visibleComponentsList;

    }

    public SpaceshipComponent[] getTiles(){
        return Tiles;
    }
//
//    public void pickVisibleComponent(SpaceshipComponent component) {
//        visibleComponents.remove(component);
//    }

    public FlightBoard getFlightBoard() {
        return flightBoard;
    }
}
