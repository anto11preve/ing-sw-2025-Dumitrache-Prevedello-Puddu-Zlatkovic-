package Model.Board.AdventureCards;

import Controller.CardResolverVisitor;
import Controller.Controller;
import Model.AdventureCardOption;

import java.util.List;

public class AdventureCard {
    private String id;
    private String name;
    private String type;
    private String level;
    private String description;
    private String effect;
    private List<String> conditions;
    private List<String> rewards;
    private List<String> penalties;
    private String imagePath;
    private String animationPath;
    private String soundEffect;
    private boolean requiresPlayerChoice;
    private List<AdventureCardOption> options;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public List<String> getPenalties() {
        return penalties;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAnimationPath() {
        return animationPath;
    }

    public String getSoundEffect() {
        return soundEffect;
    }

    public boolean isRequiresPlayerChoice() {
        return requiresPlayerChoice;
    }

    public List<AdventureCardOption> getOptions() {
        return options;
    }

//    public void accept(CardResolverVisitor visitor, Controller controller) {
//        visitor.visit(this, controller);
//    }
}
