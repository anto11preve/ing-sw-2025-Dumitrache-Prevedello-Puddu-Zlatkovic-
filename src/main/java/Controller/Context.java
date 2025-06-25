package Controller;

import Controller.Exceptions.InvalidParameters;
import Model.Board.AdventureCards.*;
import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Board.AdventureCards.Components.Planet;
import Model.Board.AdventureCards.Penalties.*;
import Model.Board.AdventureCards.Projectiles.Meteor;
import Model.Board.AdventureCards.Projectiles.Projectile;
import Model.Enums.CardLevel;
import Model.Enums.Good;
import Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Context implements Serializable {
    private transient Controller controller;
    private transient List<Player> players;
    private transient List<Player> specialPlayers;
    private int crewmates;
    private int power;
    private int requiredGoods;
    private transient List<Good> goods;
    private transient List<Projectile> projectiles;
    private transient List<Planet> planets;
    private int credits;
    private int daysLost;
    private int diceNumber;
    private transient Runnable visual;


    public Context(Controller controller) {
        this.controller = controller;
        this.players = new ArrayList<>(Arrays.asList(controller.getModel().getFlightBoard().getTurnOrder()));
        this.specialPlayers = new ArrayList<>();

    }

    public Context(Controller controller, AbandonedShip card) {
        this(controller);
        this.crewmates = card.getWinPenalty().getAmount();
        this.credits = card.getLandingReward().getAmount();
        this.daysLost = card.getLandingPenalty().getAmount();
        this.visual = () -> {
            System.out.println("Abandoned Ship");
            System.out.println("Crew to lose: " + this.crewmates);
            //winpenalty generica serve dire che e in giorni etc...
            System.out.println("Credits: " + this.credits);
            System.out.println("Days: " + this.daysLost);
            System.out.println("---------------------------------------");
        };
        this.visual.run();

    }

    public Context(Controller controller, AbandonedStation card) {
        this(controller);
        this.crewmates = card.getCrew();
        this.goods = new ArrayList<>();
        for(Good good : card.getLandingReward()) {
            this.goods.add(good);
        }
        this.daysLost = card.getLandingPenalty().getAmount();
        this.visual = () -> {
            System.out.println("Abandoned Station");
            System.out.println("Crew required: " + this.crewmates);
            //winpenalty generica serve dire che e in giorni etc...
            System.out.print("Goods: ");
            for(Good good : this.goods) {
                System.out.print(good + " ");
            }
            System.out.println();
            System.out.println("Days: " + this.daysLost);
            System.out.println("---------------------------------------");
        };
        this.visual.run();

    }

    public Context(Controller controller, MeteorSwarm card) {
        this(controller);
        this.projectiles = new ArrayList<>();
        for(Meteor meteor : card) {
            this.projectiles.add(meteor);
        }
        this.visual = () -> {
            System.out.println("Meteor Swarm");
            System.out.println("Total Meteors:  " + projectiles.size());
            System.out.println("Details:");
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile m = projectiles.get(i);
                System.out.printf(
                        "  #%d → large=%s, dir=%s%n",
                        i + 1,
                        m.isBig(),
                        m.getSide()
                );
            }
            System.out.println();
            System.out.println("Dices Result : " + this.diceNumber);
            System.out.println("---------------------------------------");
        };
        this.visual.run();

    }

    public Context(Controller controller, OpenSpace card) {
        this(controller);
        this.visual = () -> {
            System.out.println("Open Space");
        };
        System.out.println("---------------------------------------");
        this.visual.run();

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
        this.visual = () -> {
            System.out.println("Pirates");
            System.out.println("Power required: " + this.power);
            System.out.println("Total Shots:  " + projectiles.size());
            System.out.println("Details:");
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile m = projectiles.get(i);
                System.out.printf(
                        "  #%d → large=%s, dir=%s%n",
                        i + 1,
                        m.isBig(),
                        m.getSide()
                );
            }
            System.out.println();
            System.out.println("Dices Result : " + this.diceNumber);
            System.out.println("Credits: " + this.credits);
            System.out.println("Days: " + this.daysLost);
            System.out.println("---------------------------------------");
        };
        this.visual.run();

    }

    public Context(Controller controller, Planets card) {
        this(controller);
        this.daysLost = card.getLandingPenalty().getAmount();
        this.planets = new ArrayList<>();
        for (Planet planet : card) {
            this.planets.add(planet);
        }
        this.visual = () -> {
            for(Planet planet : planets) {
                System.out.print("Planet: " + planet.getName() + " ");
                for(Good good : planet.getLandingReward()) {
                    System.out.print(good + " ");
                }
                System.out.println();
                System.out.println("Days Lost: " + this.daysLost);
                System.out.println("---------------------------------------");
            }
        };
        this.visual.run();

    }

    public Context(Controller controller, Slavers card) {
        this(controller);
        this.credits = card.getWinReward().getAmount();
        this.daysLost = card.getWinPenalty().getAmount();
        this.crewmates = card.getLossPenalty().getAmount();
        this.power = card.getPower();
        this.visual = () -> {
            System.out.println("Slavers");
            System.out.println("Power required: " + this.power);
            System.out.println("Crewmates to lose: " + this.crewmates);
            System.out.println("Credits: " + this.credits);
            System.out.println("Days: " + this.daysLost);
            System.out.println("---------------------------------------");
        };
        this.visual.run();

    }

    public Context(Controller controller, Smugglers card) {
        this(controller);
        this.goods = new ArrayList<>();
        for (Good good : card.getWinReward()) {
            this.goods.add(good);
        }
        this.daysLost = card.getWinPenalty().getAmount();
        this.power = card.getPower();
        this.requiredGoods = card.getLossPenalty().getAmount();
        this.visual = () -> {
            System.out.println("Smugglers");
            System.out.println("Power required: " + this.power);
            System.out.println("Goods to lose: " + this.requiredGoods);
            System.out.print("Available goods: ");
            for(Good good : this.goods) {
                System.out.print(good + " ");
            }
            System.out.println();
            System.out.println("Days: " + this.daysLost);
            System.out.println("---------------------------------------");
        };
        this.visual.run();

    }

    public Context(Controller controller, CombatZone card) throws InvalidParameters {
        this(controller);
        if( card.getLevel() == CardLevel.LEARNER){
            int i = 0;
            this.projectiles = new ArrayList<>();
            for(CombatZoneLine line : card) {
                switch (i){
                    case 0 -> {
                        this.daysLost = ((DaysPenalty) line.getPenalty()).getAmount();
                        i++;
                        break;
                    }
                    case 1 -> {
                        this.crewmates = ((CrewPenalty) line.getPenalty()).getAmount();
                        i++;
                        break;
                    }
                    case 2 -> {
                        for (Projectile projectile : ((CannonShotPenalty) line.getPenalty())) {
                            this.projectiles.add(projectile);
                        }
                        i++;
                        break;
                    }
                    default -> throw new InvalidParameters("Invalid Combat Zone Line index: " + i);
                }

            }
            this.visual = () -> {
                System.out.println("Combat Zone Level One");
                System.out.println("Lowest Crew loses: " + this.daysLost + " days");
                System.out.println("Lowest Engine Power loses: " + this.crewmates + " crewmates");
                System.out.print("Lowest Fire Power gets hit by: ");
                for (int j = 0; j < projectiles.size(); j++) {
                    Projectile m = projectiles.get(j);
                    System.out.printf(
                            "  #%d → large=%s, dir=%s%n",
                            j + 1,
                            m.isBig(),
                            m.getSide()
                    );
                }
                System.out.println();
                System.out.println("Dices Result : " + this.diceNumber);
                System.out.println("---------------------------------------");
            };
        } else if( card.getLevel() == CardLevel.LEVEL_TWO) {
            int i = 0;
            this.projectiles = new ArrayList<>();
            for(CombatZoneLine line : card) {
                switch (i){
                    case 0 -> {
                        this.daysLost = ((DaysPenalty) line.getPenalty()).getAmount();
                        i++;
                        break;
                    }
                    case 1 -> {
                        this.requiredGoods = ((CrewPenalty) line.getPenalty()).getAmount();
                        i++;
                        break;
                    }
                    case 2 -> {
                        for (Projectile projectile : ((CannonShotPenalty) line.getPenalty())) {
                            this.projectiles.add(projectile);
                        }
                        i++;
                        break;
                    }
                    default -> throw new InvalidParameters("Invalid Combat Zone Line index: " + i);
                }

            }
            this.visual = () -> {
                System.out.println("Combat Zone Level Two");
                System.out.println("Lowest Fire Power loses: " + this.daysLost + " days");
                System.out.println("Lowest Engine Power loses: " + this.requiredGoods+ " goods");
                System.out.print("Lowest Crew gets hit by: ");
                for (int j = 0; j < projectiles.size(); j++) {
                    Projectile m = projectiles.get(j);
                    System.out.printf(
                            "  #%d → large=%s, dir=%s%n",
                            j + 1,
                            m.isBig(),
                            m.getSide()
                    );
                }
                System.out.println();
                System.out.println("Dices Result : " + this.diceNumber);
                System.out.println("---------------------------------------");
            };
        } else {
            throw new InvalidParameters("Invalid card level for CombatZone: " + card.getLevel());
        }
        this.visual.run();


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

    public int getDiceNumber() {
        return diceNumber;
    }
    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

}
