package model;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionManager {
    private static List<Transaction> transactions;

    // EFFECTS: constructs a TransactionManager with transactions set to a new List of Transaction
    public TransactionManager() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: record a record to keep track of transactions' history
    public void addTransaction(Transaction transaction) {
        // stub
    }

    // EFFECTS: returns a new transactions object filltered by action (BUY/SELL)
    public List<Transaction> filterByAction(String action) {
        return null; // stub
    }

    // REQUIRES: endTime has to be >= starTime in Unix Timestamp
    // EFFECTS: returns a new transactions object filltered by date
    public List<Transaction> filterByDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        return null; // stub
    }

    // EFFECTS: returns a new transactions object filltered by symbol
    public List<Transaction> filterBySymbol(String symbol) {
        return null; // stub
    }

    public List<Transaction> getTransactions() {
        return null;
    }

    // EFFECTS: returns a header as a below format
    // |        Date         | Symbol |  Action  |   Shares   |      Price     |          Total          |
    public String getHeader() {
        return null;
    }

    // EFFECTS: Overriding toString() method of TransactionManager class as below
    // |        Date         | Symbol |  Action  |   Shares   |      Price     |          Total          |
    // | 2025-10-05T10:44:00 |  AAPL  |   SELL   |     10     |      $100      |          1000           |
    // | 2025-10-05T10:44:00 |  AAPL  |   SELL   |     50     |      $100      |          5000           |
    // ...
    @Override
    public String toString() {
        return null;
    }        
}
