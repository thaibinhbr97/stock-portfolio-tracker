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

    // EFFECTS: constructs a Transaction with dateTime 
    // is initialized to LocalDateTime.now(),
    // symbol, action, shares, price,
    // a recent action (BUY/SELL) for a stock.
    public Transaction(String symbol, String action, double shares, double price, LocalDateTime dateTime) {
        this.symbol = symbol.toUpperCase();
        this.action = action.toUpperCase();
        this.shares = shares;
        this.price = price;
        this.dateTime = dateTime;
    }

    // getters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAction() {
        return action;
    }

    public double getShares() {
        return shares;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return shares * price;
    }

    // EFFECTS: Overriding toString() method of Transaction class as example below
    // | Date | Symbol | Action | Shares |  Price | Total |
    // | 2025-10-05T06:23:32 | AMZN | BUY | 5.00 |$200.00 | $1000.00 |
    @Override
    public String toString() {
        String transactionString = String.format("| %s | %s | %s | %.2f | $%.2f | $%.2f |", 
                getDateTime().toString(), getSymbol(), getAction(), getShares(), getPrice(), getTotal());
        return transactionString;
    }
}
