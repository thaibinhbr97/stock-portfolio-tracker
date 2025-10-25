package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents a history record of a single transaction BUY/SELL.
 * Each Transaction object includes details such as the date, stock symbol,
 * action type (BUY/SELL), number of shares, price per share, and total amount.
 * This class is immutable once created and used for maintaining transaction history.
 */
public class Transaction implements Writable {
    private LocalDateTime dateTime;
    private String symbol;
    private String action; // BUY OR SELL
    private double shares;
    private double price;

    // EFFECTS: constructs a Transaction with dateTime 
    // is initialized to LocalDateTime.now(),
    // symbol, action, shares, price,
    // a recent action (BUY/SELL) for a stock.
    public Transaction(String symbol, String action, Double shares, Double price, LocalDateTime dateTime) {
        this.symbol = symbol;
        this.action = action;
        this.shares = shares;
        this.price = price;
        this.dateTime = dateTime;
    }

    // getters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getSymbol() {
        return symbol.toUpperCase();
    }

    public String getAction() {
        return action.toUpperCase();
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

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dateTime", getDateTime().toString());
        jsonObject.put("symbol", getSymbol());
        jsonObject.put("action", getAction());
        jsonObject.put("shares", getShares());
        jsonObject.put("price", getPrice());
        jsonObject.put("total", getTotal());
        return jsonObject;
    }

    // EFFECTS: returns a transaction object from a JSON object
    public static Transaction fromJson(JSONObject jsonObject) {
        return new Transaction(
            jsonObject.getString("symbol"), 
            jsonObject.getString("action"), 
            jsonObject.getDouble("shares"), 
            jsonObject.getDouble("price"), 
            LocalDateTime.parse(jsonObject.getString("dateTime"))
        );
    }     
    
    
}
