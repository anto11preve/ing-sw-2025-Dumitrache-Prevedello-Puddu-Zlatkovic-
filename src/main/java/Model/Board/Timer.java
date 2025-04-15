package Model.Board;

import Model.Enums.TimerPhase;

public class Timer {
    private TimerPhase phase;
    private long targetTime;

    public Timer() {
        this.phase = TimerPhase.NOT_USED;
        this.targetTime = 0;
    }

    public TimerPhase getPhase() {
        return phase;
    }

    public boolean nextPhase() {
        if (getTimeLeft() != 0.0f) {
            return false;
        }

        switch (phase) {
            case NOT_USED:
                this.phase = TimerPhase.START_PHASE;
                break;
            case START_PHASE:
                this.phase = TimerPhase.MIDDLE_PHASE;
                break;
            case MIDDLE_PHASE:
                this.phase = TimerPhase.LAST_PHASE;
                break;
            default:
                return false;
        }

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

}
