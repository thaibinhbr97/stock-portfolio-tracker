package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestEvent {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertNotNull(e.getDate());

        // Check that the date is recent (within 1 second)
        long diff = Math.abs(e.getDate().getTime() - d.getTime());
        assertTrue(diff < 1000, "Event date should be within 1s of current time");
    }

    @Test
    public void testToString() {
        assertEquals(e.getDate().toString() + "\n" + "Sensor open at door", e.toString());
    }
}
