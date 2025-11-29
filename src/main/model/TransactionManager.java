package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

/*
 * Represents a history record of all transactions for BUY OR SELL action. 
 * TransactionManager allows user filter transactions history based on 
 * BUY/SELL action, stock symbol, date and time.
 * This class is immutable once created and used for maintaining a history record of all transactions.
 */
public class TransactionManager {
    private List<Transaction> transactions;

    // EFFECTS: constructs a TransactionManager with transactions set to a new List
    // of Transaction
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
        EventLog.getInstance().logEvent(new Event("Filtered transactions by action: " + action));
        List<Transaction> filltered = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAction().equalsIgnoreCase(action)) {
                filltered.add(transaction);
            }
        }
        return filltered;
    }

    // REQUIRES: endTime has to be >= starTime in Unix Timestamp
    // EFFECTS: returns a new transactions object filltered by date
    public List<Transaction> filterByDateTime(LocalDateTime startTime, LocalDateTime endTime) {
        EventLog.getInstance()
                .logEvent(new Event("Filtered transactions by date range: " + startTime + " to " + endTime));
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDateTime dateTime = transaction.getDateTime();
            if ((dateTime.isEqual(startTime)
                    || dateTime.isAfter(startTime))
                    && (dateTime.isEqual(endTime)
                            || dateTime.isBefore(endTime))) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }

    // EFFECTS: returns a new transactions object filtered by symbol
    public List<Transaction> filterBySymbol(String symbol) {
        EventLog.getInstance().logEvent(new Event("Filtered transactions by symbol: " + symbol));
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getSymbol().equalsIgnoreCase(symbol)) {
                filtered.add(transaction);
            }
        }
        return filtered;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // EFFECTS: returns a header as a below format
    // | Date | Symbol | Action | Shares | Price | Total |
    public String getHeader() {
        String headerString = String.format("| %s | %s | %s | %s | %s | %s |",
                "Date", "Symbol", "Action", "Shares", "Price", "Total");
        return headerString;
    }

    // setters
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // EFFECTS: Overriding toString() method of TransactionManager class as below
    // | Date | Symbol | Action | Shares | Price | Total |
    // | 2025-10-05T10:44:00 | AAPL | SELL | 10.00 | $100.00 | $1000.00 |
    // | 2025-10-05T10:44:00 | AAPL | SELL | 50.00 | $100.00 | $5000.00 |
    // ...
    @Override
    public String toString() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(getHeader()).append("\n");
        for (Transaction transaction : transactions) {
            contentBuilder.append(transaction.toString()).append("\n");
        }
        return contentBuilder.toString();
    }

    public JSONArray toJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns a transaction manager array from a JSON object
    public static TransactionManager fromJsonArray(JSONArray jsonArray) {
        TransactionManager tm = new TransactionManager();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                tm.addTransaction((Transaction.fromJson(jsonArray.getJSONObject(i))));
            }
        }
        return tm;
    }
}
