package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CannonShotPenalty;
import Model.Board.AdventureCards.Penalties.DaysPenalty;
import Model.Board.AdventureCards.Penalties.GoodsPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;
import Model.Board.AdventureCards.Projectiles.CannonShot;

import java.util.ArrayList;
import java.util.List;


import Model.Enums.Side;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Pirates extends Enemy<CannonShotPenalty, Credits> {

    public Pirates(int id, CardLevel level, int power, List<CannonShot> cannonShots, int days, int credits) {
        super(id, level, power, new CannonShotPenalty(cannonShots), days, new Credits(credits));
    }

    @Override
    public final String getName() {
        return "Pirati";
    }

    @Override
    public final String getDescription() {
        return "";
    }

    public Pirates(JsonObject json) {
        super(
                json,
                // build the cannon‐shot list from JSON
                new CannonShotPenalty(
                        parseShots(
                                json.getAsJsonObject("penalty")
                                        .getAsJsonArray("shots")
                        )
                ),
                // day cost
                json.getAsJsonObject("penalty").has("days")
                        ? json.getAsJsonObject("penalty").get("days").getAsInt()
                        : 1,
                // credit reward
                new Credits(
                        json.getAsJsonObject("reward").has("credits")
                                ? json.getAsJsonObject("reward").get("credits").getAsInt()
                                : 2
                )
        );
    }

    private static List<CannonShot> parseShots(JsonArray arr) {
        List<CannonShot> shots = new ArrayList<>();
        for (JsonElement e : arr) {
            JsonObject o    = e.getAsJsonObject();
            if(!o.has("isLarge") || !o.has("direction")) {
                throw new IllegalArgumentException("Invalid cannon shot data: at id ");
            }
            boolean   large = o.get("isLarge").getAsBoolean();
            Side      dir   = Side.valueOf(o.get("direction").getAsString().toUpperCase());
            shots.add(new CannonShot(large, dir));
        }
        return shots;
    }

    @Override
    public void visualize() {
        // 1) Common header (ID, Name, Level, Description, Image Path)
        super.visualize();

        // 2) Show the encounter power
        System.out.println("Power:                " + getPower());

        // 3) List each pirate shot from the loss penalty
        CannonShotPenalty shots = getLossPenalty();
        System.out.println("Pirate Shots:         " + shots.getClass().getSimpleName());
        int idx = 0;
        for (CannonShot shot : shots) {
            idx++;
            System.out.printf(
                    "  #%d → large=%s, dir=%s%n",
                    idx,
                    shot.isBig(),
                    shot.getSide()
            );
        }
        if (idx == 0) {
            System.out.println("  (no shots)");
        }

        // 4) Days penalty when you win
        DaysPenalty dp = getWinPenalty();
        System.out.printf(
                "Win Penalty:          %s (type: %s)%n",
                dp,
                dp.getClass().getSimpleName()
        );

        // 5) Credits reward when you win
        Credits cr = getWinReward();
        System.out.printf(
                "Win Reward:           %d credits (type: %s)%n",
                cr.getAmount(),
                cr.getClass().getSimpleName()
        );
    }


}
