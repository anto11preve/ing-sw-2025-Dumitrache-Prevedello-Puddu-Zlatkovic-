package Controller;

import Controller.Exceptions.InvalidParameters;
import Model.Board.AdventureCards.*;
import Model.Board.AdventureCards.Components.Planet;
import Model.Board.AdventureCards.Penalties.*;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Board.AdventureCards.Projectiles.Projectile;
import Model.Enums.CardLevel;
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
    private Runnable visual;


    public Context(Controller controller) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));

    }

    public Context(Controller controller, AbandonedShip card) {
        this(controller);
        this.crewmates = card.getWinPenalty().getAmount();
        this.credits = card.getLandingReward().getAmount();
        this.daysLost = card.getLandingPenalty().getAmount();
        this.visual = () -> {
            System.out.println("Nave Abbandonata");
            System.out.println("Crew: " + this.crewmates);
            //winpenalty generica serve dire che e in giorni etc...
            System.out.println("Credits: " + this.credits);
            System.out.println("Days: " + this.daysLost);
            System.out.println("---------------------------------------");
        };

    }

    public Context(Controller controller, AbandonedStation card) {
        this(controller);
        this.crewmates = card.getCrew();
        this.goods = new ArrayList<>();
        for(Good good : card.getLandingReward()) {
            this.goods.add(good);
        }
        this.daysLost = card.getLandingPenalty().getAmount();

    }

    public Context(Controller controller, MeteorSwarm card) {
        this(controller);
        this.projectiles = new ArrayList<>();
        for(Meteor meteor : card) {
            this.projectiles.add(meteor);
        }

    }

    public Context(Controller controller, OpenSpace card) {
        this(controller);

    }

    public Context(Controller controller, Pirates card) {
        this(controller);
        this.credits = card.getWinReward().getAmount();
        this.daysLost = card.getWinPenalty().getAmount();
        this.projectiles = new ArrayList<>();
        for (Projectile projectile : card.getLossPenalty()) {
            this.projectiles.add(projectile);
        }
        this.power = card.getPower();

    }

    public Context(Controller controller, Planets card) {
        this(controller);
        this.daysLost = card.getLandingPenalty().getAmount();
        this.planets = new ArrayList<>();
        for (Planet planet : card) {
            this.planets.add(planet);
        }
    }

    public Context(Controller controller, Slavers card) {
        this(controller);
        this.credits = card.getWinReward().getAmount();
        this.daysLost = card.getWinPenalty().getAmount();
        this.crewmates = card.getLossPenalty().getAmount();
        this.power = card.getPower();

    }

    public Context(Controller controller, Smugglers card) {
        this(controller);
        this.goods = new ArrayList<>();
//        for (Good good : card.getWinReward()) {
//            this.goods.add(good);
//        }
        //TODO: rimuovere commento
        this.daysLost = card.getWinPenalty().getAmount();
        this.power = card.getPower();
        this.requiredGoods = card.getLossPenalty().getAmount();
    }

    public Context(Controller controller, CombatZone card) throws InvalidParameters {
        this(controller);
        if( card.getLevel() == CardLevel.LEVEL_ONE){
            this.daysLost = ((DaysPenalty) card.iterator().next().getPenalty()).getAmount();
            this.crewmates = ((CrewPenalty) card.iterator().next().getPenalty()).getAmount();
            for (Projectile projectile : ((CannonShotPenalty) card.iterator().next().getPenalty())) {
                this.projectiles.add(projectile);
            }
        } else if( card.getLevel() == CardLevel.LEVEL_TWO) {
            this.daysLost = ((DaysPenalty) card.iterator().next().getPenalty()).getAmount();
            this.crewmates = ((GoodsPenalty) card.iterator().next().getPenalty()).getAmount();
            for (Projectile projectile : ((CannonShotPenalty) card.iterator().next().getPenalty())) {
                this.projectiles.add(projectile);
            }
        } else {
            throw new InvalidParameters("Invalid card level for CombatZone: " + card.getLevel());
        }

    }

    public void render(){
        visual.run();
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

    public List<Planet> getPlanets() {
        return planets;
    }



    public int getCredits() {
        return credits;
    }


    public int getDaysLost() {
        return daysLost;
    }

    public int getRequiredGoods() {
        return requiredGoods;
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
