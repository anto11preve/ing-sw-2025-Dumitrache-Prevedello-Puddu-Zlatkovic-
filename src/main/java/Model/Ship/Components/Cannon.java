package Model.Ship.Components;

import com.google.gson.JsonObject;
import Model.Enums.Card;
import Model.Enums.ConnectorType;
import Model.Enums.Direction; // enum UP, DOWN, LEFT, RIGHT (che dovete avere o creare)

/**
 * Cannon – può essere singolo o doppio, spara nella direzione frontale.
 * La potenza di fuoco dipende dall'orientamento rispetto alla nave.
 */
public class Cannon extends SpaceshipComponent {

    private final boolean isDouble;
    private final boolean requiresBattery;
    private final int basePower;
    private Direction orientation; // es. UP (nord della nave), DOWN, ecc.

    public Cannon(Card Type, ConnectorType front, ConnectorType rear, ConnectorType left, ConnectorType right, boolean isDouble) {
        super(Type, front, rear, left, right);
        this.isDouble = isDouble;
        this.requiresBattery = isDouble;
        this.basePower = isDouble ? 2 : 1;
        this.orientation = Direction.UP; // default, modificabile dopo il posizionamento
    }

    public Cannon(JsonObject json) {
        super(
                Card.valueOf(json.get("type").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("front").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("rear").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("left").getAsString()),
                ConnectorType.valueOf(json.getAsJsonObject("connectors").get("right").getAsString())
        );
        this.isDouble = json.get("isDoubleCannon").getAsBoolean();
        this.requiresBattery = json.get("requiresBattery").getAsBoolean();
        this.basePower = json.get("cannonStrength").getAsInt();
        this.orientation = Direction.UP; // default, da aggiornare nel posizionamento
    }

    // Metodo per impostare l’orientamento al momento della costruzione della nave
    public void setOrientation(Direction dir) {
        this.orientation = dir;
    }

    /**
     * Restituisce la potenza effettiva tenendo conto dell'orientamento
     * - Se doppio cannone e orientato in avanti → 2
     * - Altrimenti → 1
     */
    public int getEffectivePower(Direction shipForward) {
        // Se doppio e orientato nella stessa direzione della nave → 2
        if (isDouble && orientation == shipForward) {
            return 2;
        }
        return 1;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean requiresBattery() {
        return requiresBattery;
    }

    public int getBasePower() {
        return basePower;
    }
}

