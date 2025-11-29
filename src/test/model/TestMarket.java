package model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
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
    void testAddOrReplaceAddsNew() {
        Stock aapl = new Stock("AAPL", "Apple Inc.", "Technology", 150.00);
        testMarket.addOrReplace(aapl);

        assertFalse(testMarket.isEmpty());
        assertEquals(1, testMarket.getAllStocks().size());
        assertSame(aapl, testMarket.getStock("AAPL"));
    }

    @Test
    void testAddOrReplaceReplacesExistingBySymbol() {
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 200.00));

        Stock stored = testMarket.getStock("AAPL");
        assertNotNull(stored);
        assertEquals(200.00, stored.getCurrentPrice());
        assertEquals(1, testMarket.getAllStocks().size());
    }

    @Test
    void testAddOrReplaceNullStockNoOp() {
        testMarket.addOrReplace(null);
        assertTrue(testMarket.isEmpty());
    }

    @Test
    void testAddOrReplaceEmptySymbolNoOp() {
        Stock nullSymbol = new Stock("", "No Symbol", "Misc", 1.0);
        testMarket.addOrReplace(nullSymbol);
        assertTrue(testMarket.isEmpty());
    }

    @Test
    void testAddAllNullListNoOp() {
        testMarket.addAll(null);
        assertTrue(testMarket.isEmpty());
    }

    @Test
    void testAddAllIgnoresNullElements() {
        List<Stock> list = new ArrayList<>();
        list.add(new Stock("MSFT", "Microsoft", "Technology", 330.0));
        list.add(null);
        list.add(new Stock("AMZN", "Amazon", "Consumer Discretionary", 130.0));

        testMarket.addAll(list);
        assertEquals(2, testMarket.getAllStocks().size());
        assertNotNull(testMarket.getStock("MSFT"));
        assertNotNull(testMarket.getStock("AMZN"));
    }

    @Test
    void testAddAllAddsMultipleStocks() {
        List<Stock> batch = new ArrayList<>();
        batch.add(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        batch.add(new Stock("GOOGL", "Alphabet Inc.", "Technology", 2800.00));

        testMarket.addAll(batch);
        assertEquals(2, testMarket.getAllStocks().size());
        assertNotNull(testMarket.getStock("AAPL"));
        assertNotNull(testMarket.getStock("GOOGL"));
    }

    @Test
    void testGetStockCaseInsensitiveSymbol() {
        testMarket.addOrReplace(new Stock("msft", "Microsoft Corporation", "Technology", 350.00));
        Stock upper = testMarket.getStock("MSFT");
        Stock lower = testMarket.getStock("msft");
        assertNotNull(upper);
        assertNotNull(lower);
        assertEquals(upper, lower);
    }

    @Test
    void testGetStockNotFound() {
        assertNull(testMarket.getStock("ZZZZ"));
    }

    @Test
    void testGetStockNullSymbolReturnsNull() {
        assertNull(testMarket.getStock(null));
    }

    @Test
    void testGetAllStocksUnmodifiable() {
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        testMarket.addOrReplace(new Stock("GOOGL", "Alphabet Inc.", "Technology", 2800.00));

        List<Stock> list = new ArrayList<>(testMarket.getAllStocks());
        assertThrows(UnsupportedOperationException.class, () -> testMarket.getAllStocks().clear());
        assertEquals(2, list.size());
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

    @Test
    void testToJsonFromJsonRoundTrip() {
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 180.0));
        testMarket.addOrReplace(new Stock("AMZN", "Amazon.com, Inc.", "Consumer Discretionary", 130.0));

        JSONObject json = testMarket.toJson();
        assertTrue(json.has("stocks"));
        JSONArray arr = json.getJSONArray("stocks");
        assertEquals(2, arr.length());

        Market rebuilt = Market.fromJson(json);
        assertNotNull(rebuilt);
        assertEquals(2, rebuilt.getAllStocks().size());
        assertEquals(180.0, rebuilt.getStock("AAPL").getCurrentPrice());
        assertEquals(130.0, rebuilt.getStock("AMZN").getCurrentPrice());
    }

    @Test
    void testFromJsonWithEmptyArrayReturnsEmptyMarket() {
        JSONObject root = new JSONObject();
        root.put("stocks", new JSONArray());
        Market rebuilt = Market.fromJson(root);
        assertTrue(rebuilt.isEmpty());
    }

    @Test
    void testCopyFrom() {
        Market other = new Market();
        other.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        other.addOrReplace(new Stock("GOOGL", "Alphabet Inc.", "Technology", 2800.00));

        testMarket.copyFrom(other);
        assertEquals(2, testMarket.getAllStocks().size());
        assertNotNull(testMarket.getStock("AAPL"));
        assertNotNull(testMarket.getStock("GOOGL"));
    }

    @Test
    void testCopyFromNull() {
        testMarket.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        testMarket.copyFrom(null);
        assertEquals(1, testMarket.getAllStocks().size());
        assertNotNull(testMarket.getStock("AAPL"));
    }
}
