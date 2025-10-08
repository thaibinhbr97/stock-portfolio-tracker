package model;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWatchStock {
    private Stock apple;
    private Stock amazon;
    private Stock google;
    private Stock nvidia;
    private Stock costco;
    private Stock jpmorgan;

    private WatchStock watchStock;

    @BeforeEach
        void runBefore() {
            // constructor with shares parameter
            apple = new Stock("AAPL", "Apple Inc.", "Technology", 258.02);
            amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary", 219.51);
            google = new Stock("GOOGL", "Alphabet Inc.", "Technology", 246.45);
            nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 187.55);
            costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples",915.95);
            jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 310.35);

            watchStock = new WatchStock();
    }

    @Test
    void testConstructor() {
        assertTrue(watchStock.size() == 0);
        assertTrue(watchStock.getSymbols().size() == 0);
    }

    @Test
    void testAddStockNotInWatchStock() {
        assertTrue(watchStock.size() == 0);
        assertTrue(watchStock.getSymbols().size() == 0);
        assertFalse(watchStock.getSymbols().contains("AAPL"));

        watchStock.addStock(apple);
        assertTrue(watchStock.size() == 1);
        assertTrue(watchStock.getSymbols().contains("AAPL"));
    }

    @Test
    void testAddStockInWatchStock() {
        watchStock.addStock(google);
        assertTrue(watchStock.size() == 1);
        assertTrue(watchStock.getSymbols().size() == 1);
        assertTrue(watchStock.getSymbols().contains("GOOGL"));

        watchStock.addStock(google);
        assertTrue(watchStock.size() == 1);
        assertTrue(watchStock.getSymbols().size() == 1);
    }

    @Test
    void testRemoveStockNotInWatchStock() {
        assertFalse(watchStock.getSymbols().contains("AMZN"));
        watchStock.removeStock(amazon);
        assertFalse(watchStock.getSymbols().contains("AMZN"));
    }

    @Test
    void testRemoveStockInWatchStock() {
        watchStock.addStock(costco);
        assertTrue(watchStock.size() == 1);
        assertTrue(watchStock.getSymbols().size() == 1);
        assertTrue(watchStock.getSymbols().contains("COST"));

        watchStock.removeStock(costco);
        assertTrue(watchStock.size() == 0);
        assertTrue(watchStock.getSymbols().size() == 0);
        assertFalse(watchStock.getSymbols().contains("COST"));        
    }

    @Test
    void testWatchStockToString() {
        String watchStockString = "| NVDA | Nvidia Corp | Technology | 187.55 |";
        assertTrue(watchStockString.equals(nvidia.toString()));
    }    
}


