package Model.Board.AdventureCards.Penalties;

import Model.Board.AdventureCards.Projectiles.CannonShot;

import java.util.List;

public class CannonShotPenalty extends Penalty {
	private int lastShot;
	private final List<CannonShot> CannonShots;

	public CannonShotPenalty(List<CannonShot> CannonShots) {
		this.lastShot = -1;
		this.CannonShots = CannonShots;
	}

	public final CannonShot getNextShot() {
		if (lastShot + 1 >= this.CannonShots.size()) {
			return null;
		}

		this.lastShot += 1;

		return this.CannonShots.get(this.lastShot);
	}
}
