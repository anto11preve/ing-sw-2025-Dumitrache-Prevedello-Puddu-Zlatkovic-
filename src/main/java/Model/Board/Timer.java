package Model.Board;

import java.io.Serializable;

public class Timer implements Serializable, Cloneable {
    private Phase phase;
    private long targetTime;

    public Timer() {
        this.phase = Phase.NOT_USED;
        this.targetTime = 0;
    }

    public Phase getPhase() {
        return phase;
    }

    public boolean nextPhase() {
        if (getTimeLeft() != 0.0f || this.phase == Phase.LAST_PHASE) {
            return false;
        }

        this.phase = Phase.values()[this.phase.ordinal() + 1];

        //TODO: change 30k to actual time it takes for timer to run out
        targetTime = System.currentTimeMillis() + 30000;

        return true;
    }

    public float getTimeLeft() {
        long currentTime = System.currentTimeMillis();

        if (currentTime > targetTime) {
            return 0.0f;
        }

        return (targetTime - currentTime) * 0.001f;
    }

    public enum Phase {
        NOT_USED, START_PHASE, MIDDLE_PHASE, LAST_PHASE
    }

    @Override
    public Timer clone() {
        Timer clone = new Timer();

        clone.phase = this.phase;
        clone.targetTime = this.targetTime;

        return clone;
    }
}
