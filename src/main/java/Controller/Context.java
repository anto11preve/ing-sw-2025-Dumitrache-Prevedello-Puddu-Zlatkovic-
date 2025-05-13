package Controller;

import Model.Board.AdventureCards.*;
import Model.Board.AdventureCards.Components.Planet;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Board.AdventureCards.Projectiles.Projectile;
import Model.Enums.Good;
import Model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Context {
    private Controller controller;
    private List<Player> players;
    private List<Player> specialPlayers;
    private int crewmates;
    private int power;
    private int requiredGoods;
    private List<Good> goods;
    private List<Projectile> projectiles;
    private List<Planet> planets;
    private int credits;
    private int daysLost;

    //un costruttore specifico per ogni carta avventura?

    public Context(Controller controller) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));

    }

    public Context(Controller controller, AbandonedShip card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.crewmates = card.getWinPenalty().getAmount();
        this.credits = card.getLandingReward().getAmount();
        this.daysLost = card.getLandingPenalty().getAmount();

    }

    public Context(Controller controller, AbandonedStation card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.crewmates = card.getCrew();
        this.goods = new ArrayList<>(card.getLandingReward().iterator()); //come funziona iterator???
        this.daysLost = card.getLandingPenalty().getAmount();

    }

    public Context(Controller controller, MeteorSwarm card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.projectiles = new ArrayList<>(card.iterator())

    }

    public Context(Controller controller, OpenSpace card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));

    }

    public Context(Controller controller, Pirates card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.credits = card.getWinReward().getAmount();
        this.daysLost = card.getWinPenalty().getAmount();
        this.projectiles = new ArrayList<>(card.getLossPenalty().iterator());
        this.power = card.getPower();

    }

    public Context(Controller controller, Planets card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.daysLost = card.getLandingPenalty().getAmount();
        this.planets = new ArrayList<>(card.iterator());

    }

    public Context(Controller controller, Slavers card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.credits = card.getWinReward().getAmount();
        this.daysLost = card.getWinPenalty().getAmount();
        this.crewmates = card.getLossPenalty().getAmount();
        this.power = card.getPower();

    }

    public Context(Controller controller, Smugglers card) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.goods = new ArrayList<>(card.getWinReward().iterator());
        this.daysLost = card.getWinPenalty().getAmount();
        this.power = card.getPower();
        this.requiredGoods = card.getLossPenalty().getAmount();
    }

    public Controller getController() {
        return controller;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getSpecialPlayers() {
        return specialPlayers;
    }

    public void addSpecialPlayer(Player player) {
        if (specialPlayers == null) {
            specialPlayers = new ArrayList<>();
        }
        specialPlayers.add(player);
    }

    public void removeSpecialPlayer(Player player) {
        if (specialPlayers != null) {
            specialPlayers.remove(player);
        }
    }


    public int getCrewmates() {
        return crewmates;
    }

    public void removeCrewmate() {
        if (crewmates > 0) {
            crewmates--;
        }
    }

    public List<Good> getGoods() {
        return goods;
    }


    public void removeGood( Good good) {
        if (goods != null) {
            goods.remove(good);
        }
    }


    public Planet getPlanet(String name) {
        for (Planet planet : planets) {
            if (planet.getName().equals(name)) {
                return planet;
            }
        }
        return null;
    }



    public int getCredits() {
        return credits;
    }

    public void removeCredit() {
        if (credits > 0) {
            credits--;
        }
    }

    public int getDaysLost() {
        return daysLost;
    }

    public int getRequiredGoods() {
        return requiredGoods;
    }

    public void removeRequiredGood() {
        if (requiredGoods > 0) {
            requiredGoods--;
        }
    }

    public int getPower() {
        return power;
    }

    public Projectile getProjectile(int index){
        if (projectiles != null && index >= 0 && index < projectiles.size()) {
            return projectiles.get(index);
        }
        return null;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void removeProjectile(Projectile projectile) {
        if (projectiles != null) {
            projectiles.remove(projectile);
        }
    }

}
