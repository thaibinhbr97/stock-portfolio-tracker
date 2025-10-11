package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Transaction buyAMZN;
    private Transaction sellAMZN;
    private Transaction buyGOOGL;
    private Transaction sellGOOGL;

    private List<Transaction> transactions;

    private LocalDateTime dateTime = LocalDateTime.of(2025, 10, 5, 6, 23, 32);
    
    @BeforeEach
    public void runBefore() {
        transactionManager = new TransactionManager();

        buyAMZN = new Transaction("AMZN", "BUY", 5, 200.00, dateTime);
        sellAMZN = new Transaction("AMZN", "SELL", 3, 200.00, dateTime);
        buyGOOGL = new Transaction("GOOGL", "BUY", 10, 300.00, dateTime);
        sellGOOGL = new Transaction("GOOGL", "SELL", 5, 200.00, dateTime);

        transactions = transactionManager.getTransactions();
    }

    @Test
    public void testAddOneBuyTransaction() {
        assertFalse(transactions.contains(buyAMZN));
        assertEquals(0, transactions.size());

        transactionManager.addTransaction(buyAMZN);
        assertTrue(transactions.contains(buyAMZN));
        assertEquals(1, transactions.size());
        assertEquals(buyAMZN, transactions.get(0));
    }

    @Test
    public void testAddMoreThanOneBuyTransaction() {
        assertFalse(transactions.contains(buyAMZN));
        assertFalse(transactions.contains(buyGOOGL));
        assertEquals(0, transactions.size());

        transactionManager.addTransaction(buyAMZN);
        assertTrue(transactions.contains(buyAMZN));
        assertEquals(1, transactions.size());
        assertEquals(buyAMZN, transactions.get(0));

        transactionManager.addTransaction(buyGOOGL);
        assertTrue(transactions.contains(buyGOOGL));
        assertEquals(2, transactions.size());
        assertEquals(buyGOOGL, transactions.get(1));
    }

    @Test
    public void testAddOneSellTransaction() {
        assertFalse(transactions.contains(sellAMZN));
        transactionManager.addTransaction(buyAMZN);        
        transactionManager.addTransaction(sellAMZN);
        assertTrue(transactions.contains(sellAMZN));
        assertEquals(2, transactions.size());   
        assertEquals(sellAMZN, transactions.get(1));           
    }

    @Test
    public void testAddMoreThanOneSellTransaction() {
        assertFalse(transactions.contains(sellAMZN));
        transactionManager.addTransaction(buyAMZN);        
        transactionManager.addTransaction(buyAMZN);        
        transactionManager.addTransaction(sellAMZN);
        transactionManager.addTransaction(sellAMZN);
        assertTrue(transactions.contains(sellAMZN));
        assertEquals(4, transactions.size()); 
        assertEquals(sellAMZN, transactions.get(2));
        assertEquals(sellAMZN, transactions.get(3));
    }
    
    @Test
    public void testFilterByBuyAction() {
        transactionManager.addTransaction(buyAMZN);
        transactionManager.addTransaction(buyGOOGL);  
        transactionManager.addTransaction(sellAMZN);
        transactionManager.addTransaction(sellGOOGL);
        assertEquals(4, transactions.size()); 

        // List<Transaction> buyTransactions = new ArrayList<>();  
        // buyTransactions.add(buyAMZN);                 
        // buyTransactions.add(buyGOOGL);
        // assertEquals(2, buyTransactions.size()); 
        // assertEquals(buyAMZN, buyTransactions.get(0));                 
        // assertEquals(buyGOOGL, buyTransactions.get(1)); 
        List<Transaction> buys = transactionManager.filterByAction("BUY");
        assertEquals(2, buys.size());
        assertEquals("BUY", buys.get(0).getAction());
        assertEquals("BUY", buys.get(1).getAction());
        assertEquals(buyAMZN, buys.get(0));
        assertEquals(buyGOOGL, buys.get(1));
    }           
    
    @Test
    public void testFilterBySellAction() {
        transactionManager.addTransaction(buyAMZN);
        transactionManager.addTransaction(buyGOOGL);  
        transactionManager.addTransaction(sellAMZN);
        transactionManager.addTransaction(sellGOOGL);
        assertEquals(4, transactions.size()); 

        // List<Transaction> sellTransactions = new ArrayList<>();  
        // sellTransactions.add(sellAMZN);                 
        // sellTransactions.add(sellGOOGL);
        // assertEquals(2, sellTransactions.size()); 
        // assertEquals(sellAMZN, sellTransactions.get(0));                 
        // assertEquals(sellGOOGL, sellTransactions.get(1));   
        List<Transaction> sells = transactionManager.filterByAction("SELL");
        assertEquals(2, sells.size());
        assertEquals("SELL", sells.get(0).getAction());
        assertEquals("SELL", sells.get(1).getAction());
        assertEquals(sellAMZN, sells.get(0));
        assertEquals(sellGOOGL, sells.get(1));       
    }

    @Test
    public void testFilterByActionCaseInsensitiveQuery() {
        transactionManager.addTransaction(buyAMZN);
        transactionManager.addTransaction(buyGOOGL);
        transactionManager.addTransaction(sellAMZN);
        transactionManager.addTransaction(sellGOOGL);

        List<Transaction> buys = transactionManager.filterByAction("buy"); // lower-case query
        assertEquals(2, buys.size());
        assertEquals("BUY", buys.get(0).getAction());
        assertEquals("BUY", buys.get(1).getAction());
    }

    @Test
    public void testFilterByActionNoMatches() {
        transactionManager.addTransaction(buyAMZN);
        transactionManager.addTransaction(sellAMZN);
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
    public void testFilterByDateTimes() {
        transactionManager.addTransaction(buyAMZN);
        transactionManager.addTransaction(buyGOOGL);  
        transactionManager.addTransaction(sellAMZN);
        transactionManager.addTransaction(sellGOOGL);

        List<Transaction> dateTimeFilteredTransactions = new ArrayList<>();          
        dateTimeFilteredTransactions = transactionManager.filterByDateTime(LocalDateTime.of(2025, 10, 5, 6, 23, 32), LocalDateTime.now());
        assertEquals(4, dateTimeFilteredTransactions.size());         
    }

    @Test
    public void testFilterBySymbol() {
        transactionManager.addTransaction(buyAMZN);
        transactionManager.addTransaction(buyGOOGL);  
        transactionManager.addTransaction(sellAMZN);
        transactionManager.addTransaction(sellGOOGL);  
        
        List<Transaction> symbolTransactions = new ArrayList<>(); 
        symbolTransactions = transactionManager.filterBySymbol(AMZN);
        assertEquals(2, symbolTransactions.size());   
        assertEquals(buyAMZN, symbolTransactions.get(0)); 
        assertEquals(sellAMZN, symbolTransactions.get(1));         
    }

    @Test 
    public void testTransactionManagerToString() {
        String transactionManagerString = transactionManager.getHeader();
        transactionManager.addTransaction(buyAMZN);
        transactionManagerString += "\n| 2025-10-05T06:23:32 | AMZN | BUY | 5.00 | $200.00 | $1000.00 |\n";
        System.out.println(transactionManagerString.toString());
        System.out.println(transactionManagerString);
        assertEquals(transactionManagerString, transactionManager.toString());        
    }
    
}
