package Model.Board.AdventureCards.Components;

import Model.Board.AdventureCards.Penalties.Penalty;
import Model.Enums.Criteria;

public class CombatZoneLine {
    private final Criteria orderingCriteria;
    private final Penalty penalty;

    public CombatZoneLine(Criteria orderingCriteria, Penalty penalty) {
        this.orderingCriteria = orderingCriteria;
        this.penalty = penalty;
    }

    public final Criteria getOrderingCriteria() {
        return this.orderingCriteria;
    }

    public final Penalty getPenalty() {
        return this.penalty;
    }
}
