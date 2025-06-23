package Model.Board.AdventureCards.Penalties;

import com.google.gson.JsonObject;

public abstract class RegularPenalty extends Penalty {
    private final int amount;

    public RegularPenalty(int amount) {
        this.amount = amount;
    }

    public RegularPenalty(JsonObject json) {
        boolean crewPenaltyCondition =json.has("penalty") && json.getAsJsonObject("penalty").has("crewLoss");
        boolean goodsPenaltyCondition =json.has("penalty") && json.getAsJsonObject("penalty").has("stealGoodsOnLoss");
        if(crewPenaltyCondition) {
            this.amount=json.getAsJsonObject("penalty").get("crewLoss").getAsInt();
        } else if (goodsPenaltyCondition) {
            this.amount=json.getAsJsonObject("penalty").get("stealGoodsOnLoss").getAsInt();
        } else{
            throw new IllegalArgumentException("Missing 'penalty.crewLoss' or penalty.stealGoodsOnLoss in JSON at id: " + json.get("id").getAsInt());
        }
    }



    public final int getAmount() {
        return amount;
    }
}
