package Model.Board.AdventureCards;

import Model.Ship.ShipBoard;
import Model.Board.AdventureCards.AdventureCard;
import Model.Enums.CardLevel;
import Model.Enums.Direction;
import Model.Ship.Components.SpaceshipComponent;
import Model.Utils.Position;

import java.util.List;
import java.util.Random;

public class Stardust extends AdventureCard {

    private final Random random = new Random();

    public Stardust(int id, CardLevel level) {
        super(id, level);
    }

    /**
     * Applica l'effetto Stardust secondo le regole ufficiali di Galaxy Trucker:
     * colpisce un singolo modulo esposto scelto casualmente. Se è protetto da
     * uno scudo attivo nella direzione dell'impatto, viene salvato. Altrimenti, viene distrutto.
     */
    public void resolve(ShipBoard ship) {
        List<Position> exposedPositions = ship.getExposedComponentPositions();

        if (exposedPositions.isEmpty()) {
            System.out.println("[Stardust] ✨ Nessun modulo esposto da colpire.");
            return;
        }

        // Scegli un modulo esposto a caso
        Position target = exposedPositions.get(random.nextInt(exposedPositions.size()));

        // Scegli direzione dell'impatto casuale
        Direction[] directions = Direction.values();
        Direction impactDirection = directions[random.nextInt(directions.length)];

        System.out.println("[Stardust] ✨ Stardust colpisce modulo in " + target + " dalla direzione " + impactDirection);

        boolean shielded = ship.hasActiveShieldFacing(target, impactDirection);

        if (shielded) {
            System.out.println("[Stardust] 🛡️ Il modulo era protetto da uno scudo attivo!");
        } else {
            ship.destroyComponentAt(target);
            System.out.println("[Stardust] 💥 Modulo distrutto da Stardust!");
        }
    }
}
