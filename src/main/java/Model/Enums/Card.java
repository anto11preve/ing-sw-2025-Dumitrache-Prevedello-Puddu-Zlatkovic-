package Model.Enums;

/**
 * Enum representing different types of spaceship components (cards) used in Galaxy Trucker.
 * Each card type can optionally represent specific functional roles such as cannons or engines.
 */
public enum Card {
    CABIN(false, false),
    ENGINE(false, true),
    DOUBLE_ENGINE(false, true),
    CANNON(true, false),
    DOUBLE_CANNON(true, false),
    BATTERY_COMPARTMENT(false, false),
    SHIELD_GENERATOR(false, false),
    CARGO_HOLD(false, false),
    SPECIAL_CARGO_HOLD(false, false),
    STRUCTURAL_MODULE(false, false),
    ALIEN_LIFE_SUPPORT(false, false);

    private final boolean hasCannon;
    private final boolean hasEngine;

    /**
     * Constructor for the Card enum, specifying whether the card has cannon or engine functionality.
     *
     * @param hasCannon true if the card is a cannon-type
     * @param hasEngine true if the card is an engine-type
     */
    Card(boolean hasCannon, boolean hasEngine) {
        this.hasCannon = hasCannon;
        this.hasEngine = hasEngine;
    }

    /**
     * Returns whether this card has engine functionality.
     *
     * @return true if it's an engine or double engine
     */
    public boolean hasEngine() {
        return hasEngine;
    }

    /**
     * Returns whether this card has cannon functionality.
     *
     * @return true if it's a cannon or double cannon
     */
    public boolean hasCannon() {
        return hasCannon;
    }
}