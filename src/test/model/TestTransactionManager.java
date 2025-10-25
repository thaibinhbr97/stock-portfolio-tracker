package model;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTransactionManager {
    private TransactionManager transactionManager;

    // symbol strings
    private static final String ORCL = "ORCL";
    private static final String AAPL = "AAPL";
    private static final String AMZN = "AMZN";
    private static final String GOOGL = "GOOGL";
    private static final String NVDA = "NVDA";
    private static final String COST = "COST";
    private static final String JPM = "JPM";    

    private Transaction buyAmazon;
    private Transaction sellAmazon;
    private Transaction buyGoogle;
    private Transaction sellGoogle;

    private List<Transaction> transactions;



    // Deterministic date-times for testing
    private static LocalDateTime testDateTime(int hour) {
        return LocalDateTime.of(2025, 10, 5, hour, 0, 0);
    }

    private final LocalDateTime dt10 = testDateTime(10);
    private final LocalDateTime dt12 = testDateTime(12);
    private final LocalDateTime dt14 = testDateTime(14);
    
    @BeforeEach
    public void runBefore() {
        transactionManager = new TransactionManager();

        buyAmazon  = new Transaction("AMZN", "BUY",  5.0, 200.00, dt10);
        sellAmazon = new Transaction("AMZN", "SELL", 3.0, 200.00, dt12);
        buyGoogle  = new Transaction("GOOGL", "BUY", 10.0, 300.00, dt14);
        sellGoogle = new Transaction("GOOGL", "SELL", 5.0, 200.00, dt14);

        transactions = transactionManager.getTransactions();
    }

    @Test
    public void testAddOneBuyTransaction() {
        assertFalse(transactions.contains(buyAmazon));
        assertEquals(0, transactions.size());

        transactionManager.addTransaction(buyAmazon);
        assertTrue(transactions.contains(buyAmazon));
        assertEquals(1, transactions.size());
        assertEquals(buyAmazon, transactions.get(0));
    }

    @Test
    public void testAddMoreThanOneBuyTransaction() {
        assertFalse(transactions.contains(buyAmazon));
        assertFalse(transactions.contains(buyGoogle));
        assertEquals(0, transactions.size());

        transactionManager.addTransaction(buyAmazon);
        assertTrue(transactions.contains(buyAmazon));
        assertEquals(1, transactions.size());
        assertEquals(buyAmazon, transactions.get(0));

        transactionManager.addTransaction(buyGoogle);
        assertTrue(transactions.contains(buyGoogle));
        assertEquals(2, transactions.size());
        assertEquals(buyGoogle, transactions.get(1));
    }

    @Test
    public void testAddOneSellTransaction() {
        assertFalse(transactions.contains(sellAmazon));
        transactionManager.addTransaction(buyAmazon);        
        transactionManager.addTransaction(sellAmazon);
        assertTrue(transactions.contains(sellAmazon));
        assertEquals(2, transactions.size());   
        assertEquals(sellAmazon, transactions.get(1));           
    }

    @Test
    public void testAddMoreThanOneSellTransaction() {
        assertFalse(transactions.contains(sellAmazon));
        transactionManager.addTransaction(buyAmazon);        
        transactionManager.addTransaction(buyAmazon);        
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(sellAmazon);
        assertTrue(transactions.contains(sellAmazon));
        assertEquals(4, transactions.size()); 
        assertEquals(sellAmazon, transactions.get(2));
        assertEquals(sellAmazon, transactions.get(3));
    }
    
    @Test
    public void testFilterByBuyAction() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(buyGoogle);  
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(sellGoogle);
        assertEquals(4, transactions.size()); 

        List<Transaction> buys = transactionManager.filterByAction("BUY");
        assertEquals(2, buys.size());
        assertEquals("BUY", buys.get(0).getAction());
        assertEquals("BUY", buys.get(1).getAction());
        assertEquals(buyAmazon, buys.get(0));
        assertEquals(buyGoogle, buys.get(1));
    }           
    
    @Test
    public void testFilterBySellAction() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(buyGoogle);  
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(sellGoogle);
        assertEquals(4, transactions.size()); 

        List<Transaction> sells = transactionManager.filterByAction("SELL");
        assertEquals(2, sells.size());
        assertEquals("SELL", sells.get(0).getAction());
        assertEquals("SELL", sells.get(1).getAction());
        assertEquals(sellAmazon, sells.get(0));
        assertEquals(sellGoogle, sells.get(1));       
    }

    @Test
    public void testFilterByActionCaseInsensitiveQuery() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(buyGoogle);
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(sellGoogle);

        List<Transaction> buys = transactionManager.filterByAction("buy"); // lower-case query
        assertEquals(2, buys.size());
        assertEquals("BUY", buys.get(0).getAction());
        assertEquals("BUY", buys.get(1).getAction());
    }

    @Test
    public void testFilterByActionNoMatches() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(sellAmazon);
        List<Transaction> divs = transactionManager.filterByAction("DIVIDEND");
        assertTrue(divs.isEmpty());
    }

    @Test
    public void testFilterByActionOnEmptyManager() {
        TransactionManager empty = new TransactionManager();
        List<Transaction> res = empty.filterByAction("BUY");
        assertTrue(res.isEmpty());
    }

    @Test
    public void testFilterByDateTimeInclusiveBounds() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(buyGoogle);  

        List<Transaction> filtered = transactionManager.filterByDateTime(dt12, dt14);
        assertEquals(2, filtered.size());
        assertEquals(dt12, filtered.get(0).getDateTime());
        assertEquals(dt14, filtered.get(1).getDateTime());
    }

    @Test
    public void testFilterByDateTimeFullRange() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(buyGoogle);
        transactionManager.addTransaction(sellGoogle);

        List<Transaction> filtered = transactionManager.filterByDateTime(dt10, dt14);
        assertEquals(4, filtered.size());        
    }

    public void testFilterByDateTimeOutOfRange() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(sellAmazon);
        List<Transaction> filtered = transactionManager.filterByDateTime(testDateTime(6), testDateTime(9));
        assertTrue(filtered.isEmpty());
    }

    @Test
    public void testFilterByDateTimeStartAfterEndReturnsEmpty() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(sellAmazon);
        List<Transaction> filtered = transactionManager.filterByDateTime(dt14, dt12);
        assertTrue(filtered.isEmpty());
    }    

    @Test
    public void testFilterBySymbol() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(buyGoogle);  
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(sellGoogle);  
        
        List<Transaction> symbolTransactions = new ArrayList<>(); 
        symbolTransactions = transactionManager.filterBySymbol(AMZN);
        assertEquals(2, symbolTransactions.size());   
        assertEquals(buyAmazon, symbolTransactions.get(0)); 
        assertEquals(sellAmazon, symbolTransactions.get(1));         
    }

    @Test 
    public void testTransactionManagerToString() {
        String expected = transactionManager.getHeader() + "\n";
        transactionManager.addTransaction(buyAmazon);
        expected += buyAmazon.toString() + "\n";
        assertEquals(expected, transactionManager.toString());      
    }

    @Test
    public void testFromJsonArrayRoundTrip() {
        transactionManager.addTransaction(buyAmazon);
        transactionManager.addTransaction(sellAmazon);
        transactionManager.addTransaction(buyGoogle);

        JSONArray arr = transactionManager.toJsonArray();
        TransactionManager rebuilt = TransactionManager.fromJsonArray(arr);

        assertNotNull(rebuilt);
        assertEquals(3, rebuilt.getTransactions().size());
        assertEquals(buyAmazon.getSymbol(), rebuilt.getTransactions().get(0).getSymbol());
        assertEquals(sellAmazon.getAction(), rebuilt.getTransactions().get(1).getAction());
        assertEquals(buyGoogle.getDateTime(), rebuilt.getTransactions().get(2).getDateTime());
    }

    @Test
    public void testFromJsonArrayNullReturnsEmptyManager() {
        TransactionManager rebuilt = TransactionManager.fromJsonArray(null);
        assertNotNull(rebuilt);
        assertTrue(rebuilt.getTransactions().isEmpty());
    }

    @Test
    public void testSetTransactionsReplacesUnderlyingList() {
        List<Transaction> replacement = new ArrayList<>();
        replacement.add(buyAmazon);
        replacement.add(sellAmazon);

        transactionManager.setTransactions(replacement);
        assertSame(replacement, transactionManager.getTransactions());
        assertEquals(2, transactionManager.getTransactions().size());
        assertEquals("AMZN", transactionManager.getTransactions().get(0).getSymbol());
    }    
}
