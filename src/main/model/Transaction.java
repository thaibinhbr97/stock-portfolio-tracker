package model;

import java.time.LocalDateTime;

/*
 * Represents a history record of a single transaction BUY/SELL.
 * Each Transaction object includes details such as the date, stock symbol,
 * action type (BUY/SELL), number of shares, price per share, and total amount.
 * This class is immutable once created and used for maintaining transaction history.
 */
public class Transaction {
    private LocalDateTime dateTime;
    private String symbol;
    private String action; // BUY/SELL
    private double shares;
    private double price;
    private double total;

    // EFFECTS: constructs a Transaction with dateTime is set to LocalDateTime.now(), symbol, action, shares, price, total of 
    // a recent action (BUY/SELL) for a stock.
    public Transaction(LocalDateTime dateTime, String symbol, String action, double shares, double price, double total) {
        // stub
    }

    // getters
    public LocalDateTime getDateTime() {
        return null; // stub
    }

    public String getSymbol() {
        return null; // stub
    }

    public String getAction() {
        return null; // stub
    }

    public double getShares() {
        return 0.0; // stub
    }

    public double getPrice() {
        return 0.0; // stub
    }

    public double getTotal() {
        return 0.0; // stub
    }

    // EFFECTS: print out a record of a single transaction BUY/SELL in the console
    // with the format as below:
    // | Date | Symbol | Action | Shares | Price | Total |
    public String logTransaction() {
        return null; // stub
    }
}
