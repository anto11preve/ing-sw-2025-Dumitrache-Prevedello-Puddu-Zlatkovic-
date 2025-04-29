package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Enums.CardLevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CombatZone extends AdventureCardFilip implements Iterable<CombatZoneLine> {
    private final List<CombatZoneLine> lines;

    public CombatZone(int id, CardLevel level, List<CombatZoneLine> lines) {
        super(id, level);
        this.lines = lines;
    }

    @Override
    public String getName() {
        return "Zona di Guerra";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Iterator<CombatZoneLine> iterator() {
        List<CombatZoneLine> lines = new ArrayList<>(this.lines);

        return lines.iterator();
    }
}
