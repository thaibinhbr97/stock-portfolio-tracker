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
    
    private LocalDateTime dateTime;

    @BeforeEach
    void runBefore() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 5, 6, 23, 32);
        buyAMZN = new Transaction("AMZN", "BUY", 5, 200.00, 5 * 200.00, dateTime);
    }

    @Test
    void testConstrutor() {
        assertEquals(LocalDateTime.of(2025, 10, 5, 6, 23, 32), buyAMZN.getDateTime());
        assertEquals("AMZN", buyAMZN.getSymbol());
        assertEquals("BUY", buyAMZN.getAction());
        assertEquals(5, buyAMZN.getShares());
        assertEquals(200.00, buyAMZN.getPrice());
        assertEquals(1000.00, buyAMZN.getTotal());
    }
    
    @Test
    void testTransactionToString() {
        String transactionString = "| 2025-10-5T6:23:32 | AMZN | BUY | 5 | 200.00 | 1000.00 |";
        assertTrue(transactionString.equals(buyAMZN.toString()));
    }
}
