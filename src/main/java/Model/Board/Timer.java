package Model.Board;

import Model.Enums.TimerPhase;

public class Timer {
    private TimerPhase phase;
    private float timeLeft;

    public Timer(TimerPhase Phase) {
        this.phase = TimerPhase.NOT_USED;
        this.timeLeft = 0;
    }

    public TimerPhase getPhase() {
        return phase;
    }
    public boolean nextPhase(TimerPhase phase) {
        switch (phase) {
            case NOT_USED:
                this.phase = TimerPhase.START_PHASE;
                return true;
            case START_PHASE:
                this.phase = TimerPhase.MIDDLE_PHASE;
                return true;
            case MIDDLE_PHASE:
                this.phase = TimerPhase.LAST_PHASE;
                return true;
            case LAST_PHASE:
                this.phase = TimerPhase.NOT_USED;
                return true;
            default:
                return false;
        }
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }
}
