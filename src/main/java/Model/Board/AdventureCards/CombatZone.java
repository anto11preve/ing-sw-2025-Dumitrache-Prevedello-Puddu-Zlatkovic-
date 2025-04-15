package Model.Board.AdventureCards;

import Model.Board.AdventureCards.Components.CombatZoneLine;
import Model.Enums.CardLevel;

import java.util.List;

public class CombatZone extends AdventureCard {
    private int lastLine;
    private final List<CombatZoneLine> lines;

    public CombatZone(int id, CardLevel level, List<CombatZoneLine> lines) {
        super(id, level);
        this.lastLine = -1;
        this.lines = lines;
    }

    public CombatZoneLine getNextLine() {
        if (lastLine + 1 >= lines.size()) {
            return null;
        }

        lastLine++;

        return lines.get(lastLine);
    }

    @Override
    public String getName() {
        return "Zona di Guerra";
    }

    @Override
    public String getDescription() {
        return "";
    }
}
