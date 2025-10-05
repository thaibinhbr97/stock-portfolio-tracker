package service;

import java.time.LocalDateTime;
import java.util.List;

import model.Transaction;

public class TransactionManager {
    private List<Transaction> transactions;

    // EFFECTS: constructs a TransactionManager with transactions is set to a new List of Transaction
    public TransactionManager() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: record a buy transaction to keep track of transactions' history
    public void recordBuy() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: record a sell transaction to keep track of transactions' history
    public void recordSell() {
        // stub
    }

    // EFFECTS: returns a new transactions object filltered by action (BUY/SELL)
    public List<Transaction> filterByAction(String action) {
        return null; // stub
    }

    // REQUIRES: endTime has to be >= starTime in Unix Timestamp
    // EFFECTS: returns a new transactions object filltered by date
    public List<Transaction> filterByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return null; // stub
    }

    // EFFECTS: returns a new transactions object filltered by symbol
    public List<Transaction> filterBySymbol(String symbol) {
        return null; // stub
    }

    // EFFECTS: prints out a histoy of transaction given transactions object parameter similar to example below
    
    // |        Date         | Symbol |  Action  |   Shares   |      Price     |          Total          |
    // | 2025-10-05T10:44:00 |  AAPL  |   SELL   |     10     |      $100      |          1000           |
    // | 2025-10-05T10:44:00 |  AAPL  |   SELL   |     50     |      $100      |          5000           |
    // ...
    public void logTransactions(List<Transaction> transactions) {
        // stub
    }
}
