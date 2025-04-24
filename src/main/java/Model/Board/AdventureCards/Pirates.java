package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Penalties.CannonShotPenalty;
import Model.Board.AdventureCards.Rewards.Credits;
import Model.Enums.CardLevel;
import Model.Board.AdventureCards.Projectiles.CannonShot;

import java.util.List;

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
}
