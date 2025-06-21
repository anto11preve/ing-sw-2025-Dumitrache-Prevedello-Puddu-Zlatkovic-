package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CrewPenalty;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;
import com.google.gson.JsonObject;

public class AbandonedShip extends AdventureCardFilip {
    private final CrewPenalty winPenalty;
    private final Credits landingReward;
    private final DaysPenalty landingPenalty;
    private String imagePath;

    public AbandonedShip(int id, CardLevel level, int crew, int credits, int days) {
        super(id, level);
        this.winPenalty = new CrewPenalty(crew);
        this.landingReward = new Credits(credits);
        this.landingPenalty = new DaysPenalty(days);
    }

    public int getCrew() {
        return winPenalty.getAmount();
    }

    public CrewPenalty getWinPenalty() {
        return winPenalty;
    }

    public Credits getLandingReward() {
        return landingReward;
    }

    public DaysPenalty getLandingPenalty() {
        return landingPenalty;
    }

    @Override
    public String getName() {
        return "Nave Abbandonata";
    }

    @Override
    public String getDescription() {
        return "";
    }

    public AbandonedShip(JsonObject json) {
        super(json);

        // Default values
        int crew = 1;
        int credits = 0;
        int days = 0;
        
        // Parse from JSON if available
        if (json.has("crewRequired")) {
            crew = json.get("crewRequired").getAsInt();
        }
        
        if (json.has("reward") && json.getAsJsonObject("reward").has("crew")) {
            crew = json.getAsJsonObject("reward").get("crew").getAsInt();
        }
        
        if (json.has("reward") && json.getAsJsonObject("reward").has("credits")) {
            credits = json.getAsJsonObject("reward").get("credits").getAsInt();
        }
        
        if (json.has("days")) {
            days = json.get("days").getAsInt();
        }

        this.winPenalty = new CrewPenalty(crew);
        this.landingReward = new Credits(credits);
        this.landingPenalty = new DaysPenalty(days);
        this.imagePath = json.get("imagePath").getAsString();
    }

    public String getImagePath() {
        return imagePath;
    }
}
