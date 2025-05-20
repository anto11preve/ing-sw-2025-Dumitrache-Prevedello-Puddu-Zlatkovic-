package Model.Enums;

public enum Card {
    CABIN(false, false),
    ENGINE(false, true),
    CANNON(true, false),
    BATTERY_COMPARTMENT(false, false),
    SHIELD_GENERATOR(false, false),
    CARGO_HOLD(false, false),
    SPECIAL_CARGO_HOLD(false, false),
    STRUCTURAL_MODULE(false, false),
    ALIEN_LIFE_SUPPORT(false, false);

    private final boolean hasCannon;
    private final boolean hasEngine;

    Card(boolean hasCannon, boolean hasEngine) {
        this.hasCannon = hasCannon;
        this.hasEngine = hasEngine;
    }

    public boolean hasEngine() {
        return this == ENGINE /*TODO: fix this: || this == DOUBLE_ENGINE*/;
    }

    public boolean hasCannon() {
        return this == CANNON /*TODO: fix this: || this == DOUBLE_CANNON*/;
    }
}
