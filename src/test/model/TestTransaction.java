package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTransaction {
    private Transaction buyAMZN;
    private Transaction sellAMZN;
    private Transaction buyGOOGL;
    private Transaction sellGOOGL;
    
    @BeforeEach
    void runBefore() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 5, 6, 23, 32);
        buyAMZN = new Transaction(dateTime, "AMZN", "BUY", 5, 219.51, 5 * 219.51);
    }

    @Test
    void testConstrutor() {
        LocalDateTime dateTimeTest = LocalDateTime.of(2025, 10, 5, 6, 23, 32);
        assertEquals(dateTimeTest, buyAMZN.getDateTime());
        assertEquals("AMZN", buyAMZN.getSymbol());
        assertEquals("BUY", buyAMZN.getAction());
        assertEquals(5, buyAMZN.getShares());
        assertEquals(219.51, buyAMZN.getPrice());
        assertEquals(5 * 219.51, buyAMZN.getTotal());
    }
    
    @Test
    void testPrinting() {
        String output = "| 2025-10-5T6:23:32 | AMZN | BUY | 5 | 219.51 | 1097.55 |";
        assertTrue(output.equals(buyAMZN.toString()));
    }
}
