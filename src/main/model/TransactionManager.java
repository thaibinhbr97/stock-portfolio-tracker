package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private List<Transaction> transactions;

    // EFFECTS: constructs a TransactionManager with transactions set to a new List of Transaction
    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: record a record to keep track of transactions' history
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // EFFECTS: returns a new transactions object filltered by action (BUY/SELL)
    public List<Transaction> filterByAction(String action) {
        List<Transaction> filltered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAction().equalsIgnoreCase(action)) {
                filltered.add(t);
            }
        }
        return filltered;
    }

    // REQUIRES: endTime has to be >= starTime in Unix Timestamp
    // EFFECTS: returns a new transactions object filltered by date
    public List<Transaction> filterByDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            LocalDateTime dt = t.getDateTime();
            if ((dt.isEqual(startTime) || dt.isAfter(startTime)) && (dt.isEqual(endTime) || dt.isBefore(endTime))) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    // EFFECTS: returns a new transactions object filltered by symbol
    public List<Transaction> filterBySymbol(String symbol) {
        List<Transaction> filltered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getSymbol().equalsIgnoreCase(symbol)) {
                filltered.add(t);
            }
        }
        return filltered;        
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // EFFECTS: returns a header as a below format
    // |        Date         | Symbol |  Action  |          Shares          |          Price         |          Total         |
    public String getHeader() {
        String headerString = String.format("| %s | %s | %s | %s | %s | %s |",
                "Date", "Symbol", "Action", "Shares", "Price", "Total");
        return headerString;
    }

    // EFFECTS: Overriding toString() method of TransactionManager class as below
    // |        Date         | Symbol |  Action  |          Shares          |          Price         |          Total         |
    // | 2025-10-05T10:44:00 |  AAPL  |   SELL   |          10.00           |        $100.00         |        $1000.00        |
    // | 2025-10-05T10:44:00 |  AAPL  |   SELL   |          50.00           |        $100.00         |        $5000.00        |
    // ...
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader()).append("\n");
        for (Transaction t : transactions) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }        
}
