package model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMarket {
    private Market testMarket;

    @BeforeEach
    void setUp() {
        testMarket = new Market(); // should start empty
    }

    @Test
    void testEmptyOnConstruction() {
        assertTrue(testMarket.isEmpty());
        assertEquals(0, testMarket.getAllStocks().size());
    }

    @Test
    void testAddOrReplace_AddsNew() {
        Stock aapl = new Stock("AAPL", "Apple Inc.", "Technology", 150.00);
        testMarket.addOrReplace(aapl);

        assertFalse(testMarket.isEmpty());
        assertEquals(1, testMarket.getAllStocks().size());
        assertSame(aapl, testMarket.getStock("AAPL"));
    }

    @Test
    void testAddOrReplace_ReplacesExistingBySymbol() {
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 200.00));

        Stock stored = testMarket.getStock("AAPL");
        assertNotNull(stored);
        assertEquals(200.00, stored.getCurrentPrice());
        assertEquals(1, testMarket.getAllStocks().size());
    }

    @Test
    void testGetStock_CaseInsensitiveSymbol() {
        testMarket.addOrReplace(new Stock("msft", "Microsoft Corporation", "Technology", 350.00));
        Stock upper = testMarket.getStock("MSFT");
        Stock lower = testMarket.getStock("msft");
        assertNotNull(upper);
        assertNotNull(lower);
        assertEquals(upper, lower);
    }

    @Test
    void testGetStock_NotFound() {
        assertNull(testMarket.getStock("ZZZZ"));
    }

    @Test
    void testAddAll_AddsMultipleStocks() {
        List<Stock> batch = new ArrayList<>();
        batch.add(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        batch.add(new Stock("GOOGL", "Alphabet Inc.", "Technology", 2800.00));

        testMarket.addAll(batch);
        assertEquals(2, testMarket.getAllStocks().size());
        assertNotNull(testMarket.getStock("AAPL"));
        assertNotNull(testMarket.getStock("GOOGL"));
    }

    @Test
    void testIterationIsSortedBySymbol() {
        // Add intentionally out of order
        testMarket.addOrReplace(new Stock("TSLA", "Tesla, Inc.", "Automotive", 250.00));
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        testMarket.addOrReplace(new Stock("GOOGL", "Alphabet Inc.", "Technology", 2800.00));

        List<Stock> list = new ArrayList<>(testMarket.getAllStocks());
        assertEquals("AAPL", list.get(0).getSymbol());
        assertEquals("GOOGL", list.get(1).getSymbol());
        assertEquals("TSLA", list.get(2).getSymbol());
    }
}
