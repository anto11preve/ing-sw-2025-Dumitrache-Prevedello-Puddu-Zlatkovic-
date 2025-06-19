package Model.Board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {

    @Test
    public void testConstructor() {
        Timer timer = new Timer();
        assertEquals(Timer.Phase.NOT_USED, timer.getPhase());
        assertEquals(0.0f, timer.getTimeLeft(), 0.001f);
    }
    
    @Test
    public void testNextPhase() {
        Timer timer = new Timer();
        assertEquals(Timer.Phase.NOT_USED, timer.getPhase());
        
        // Since time left is 0, we should be able to move to the next phase
        assertTrue(timer.nextPhase());
        assertEquals(Timer.Phase.START_PHASE, timer.getPhase());
        
        // Time left should now be greater than 0
        assertTrue(timer.getTimeLeft() > 0);
        
        // We shouldn't be able to move to the next phase until time is up
        assertFalse(timer.nextPhase());
    }
    
    @Test
    public void testPhaseProgression() {
        Timer timer = new Timer();
        
        // Simulate time passing by directly manipulating the targetTime field
        // This is a bit of a hack for testing purposes
        timer.nextPhase(); // Move to START_PHASE
        assertEquals(Timer.Phase.START_PHASE, timer.getPhase());
        
        // We need to wait for the timer to expire or mock it
        // For testing purposes, we'll use reflection to set the targetTime to now
        try {
            java.lang.reflect.Field targetTimeField = Timer.class.getDeclaredField("targetTime");
            targetTimeField.setAccessible(true);
            targetTimeField.set(timer, System.currentTimeMillis());
            Thread.sleep(10); // Ensure time passes
        } catch (Exception e) {
            fail("Failed to set targetTime field: " + e.getMessage());
        }
        
        // Now we should be able to move to the next phase
        assertTrue(timer.nextPhase());
        assertEquals(Timer.Phase.MIDDLE_PHASE, timer.getPhase());
        
        // Repeat for LAST_PHASE
        try {
            java.lang.reflect.Field targetTimeField = Timer.class.getDeclaredField("targetTime");
            targetTimeField.setAccessible(true);
            targetTimeField.set(timer, System.currentTimeMillis());
            Thread.sleep(10); // Ensure time passes
        } catch (Exception e) {
            fail("Failed to set targetTime field: " + e.getMessage());
        }
        
        assertTrue(timer.nextPhase());
        assertEquals(Timer.Phase.LAST_PHASE, timer.getPhase());
        
        // After LAST_PHASE, we shouldn't be able to advance further
        try {
            java.lang.reflect.Field targetTimeField = Timer.class.getDeclaredField("targetTime");
            targetTimeField.setAccessible(true);
            targetTimeField.set(timer, System.currentTimeMillis());
            Thread.sleep(10); // Ensure time passes
        } catch (Exception e) {
            fail("Failed to set targetTime field: " + e.getMessage());
        }
        
        assertFalse(timer.nextPhase());
        assertEquals(Timer.Phase.LAST_PHASE, timer.getPhase());
    }
    
    @Test
    public void testGetTimeLeft() {
        Timer timer = new Timer();
        assertEquals(0.0f, timer.getTimeLeft(), 0.001f);
        
        timer.nextPhase();
        assertTrue(timer.getTimeLeft() > 0);
        
        // Time should decrease as time passes
        float firstReading = timer.getTimeLeft();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail("Sleep interrupted");
        }
        float secondReading = timer.getTimeLeft();
        
        assertTrue(secondReading < firstReading);
    }
}