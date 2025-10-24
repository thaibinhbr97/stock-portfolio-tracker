package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTransaction {
    private Transaction buyAmazon;
    private Transaction sellAmazon;
    private Transaction buyGoogle;
    private Transaction sellGoogle;
    
    private LocalDateTime dateTime;

    @BeforeEach
    public void runBefore() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 5, 6, 23, 32);
        buyAmazon = new Transaction("AMZN", "BUY", 5.0, 200.00, dateTime);
    }

    @Test
    public void testConstrutor() {
        assertEquals(LocalDateTime.of(2025, 10, 5, 6, 23, 32), buyAmazon.getDateTime());
        assertEquals("AMZN", buyAmazon.getSymbol());
        assertEquals("BUY", buyAmazon.getAction());
        assertEquals(5, buyAmazon.getShares());
        assertEquals(200.00, buyAmazon.getPrice());
        assertEquals(1000.00, buyAmazon.getTotal());
    }
    
    @Test
    public void testTransactionToString() {
        String transactionString = "| 2025-10-05T06:23:32 | AMZN | BUY | 5.00 | $200.00 | $1000.00 |";
        System.out.println(buyAmazon.toString());
        System.out.println(transactionString);
        assertTrue(transactionString.equals(buyAmazon.toString()));
    }
}
