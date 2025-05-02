package Model.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TimerTest {

    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
    }

    @Test
    void getPhase() {
        assertEquals(Timer.Phase.NOT_USED, timer.getPhase());
    }

    @Test
    void nextPhase() {
        while (timer.getPhase() != Timer.Phase.LAST_PHASE){
            System.out.println("Phase: " + timer.getPhase());
            while (timer.getTimeLeft() != 0.0f) {
                assertFalse(timer.nextPhase());
                System.out.println("Time left: " + timer.getTimeLeft());
            }
            assertTrue(timer.nextPhase());
        }
        System.out.println("Phase: " + timer.getPhase());
        while(timer.getTimeLeft() != 0.0f) {
            assertFalse(timer.nextPhase());
            System.out.println("Time left: " + timer.getTimeLeft());
        }

        assertFalse(timer.nextPhase());
        System.out.println("Final Phase: " + timer.getPhase());
    }

    @Test
    void getTimeLeft() {
        assertEquals(Timer.Phase.NOT_USED, timer.getPhase());

        assertTrue(timer.nextPhase());

        assertEquals(Timer.Phase.START_PHASE, timer.getPhase());


        System.out.println("Time left: " + timer.getTimeLeft());
    }
}