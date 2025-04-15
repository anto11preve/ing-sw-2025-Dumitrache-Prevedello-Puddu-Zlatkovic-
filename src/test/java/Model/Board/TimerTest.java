package Model.Board;

import Model.Enums.TimerPhase;
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
		assertEquals(TimerPhase.NOT_USED, timer.getPhase());
	}

	@Test
	void nextPhase() {
		while (timer.getPhase() != TimerPhase.LAST_PHASE){
			System.out.println("Phase: " + timer.getPhase());
			while (timer.getTimeLeft() != 0.0f) {
				assertEquals(false, timer.nextPhase());
				System.out.print("\rTime left: " + timer.getTimeLeft());
			}
			assertEquals(true, timer.nextPhase());
		}
	}

	@Test
	void getTimeLeft() {
		assertEquals(TimerPhase.NOT_USED, timer.getPhase());

		assertEquals(true, timer.nextPhase());

		assertEquals(TimerPhase.START_PHASE, timer.getPhase());


		System.out.println("Time left: " + timer.getTimeLeft());
	}
}