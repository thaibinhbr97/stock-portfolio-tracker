package model;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Represents the user's overall stock portfolio.
 * The Portfolio maintains a collection of Stock objects and provides
 * methods for adding, removing, and retrieving stocks as well as calculating the total value of all owned stocks.
 */
public class Portfolio { 
    private String ownerName;
    private double cashBalance;
    private List<Stock> stocks;
    private double totalValue;
    private LocalDateTime lastUpdated;
    private List<String> symbols;
    
    // EFFECTS: constructs a Portfolio with onwerName, cashBalance stocks, totalValue, lastUpdated.
    public Portfolio(double ownerName, double cashBalance, List<Stock> stocks, double totalValue, LocalDateTime lastUpdated) {
        // stub
    }

    // EFFECTS: returns Stock with given symbol, or null if not found.
    public Stock getStock(String symbol) {
        return null; // stub
    }

    // MODIFIES: this, Stock
    // EFFECTS: adds the given stock to the portfolio if not already presented;
    // if stock already existed (same symbol), increase its share.
    public void addStock(Stock s) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns true and removes the stock with the given symbol from the portfolio if it exists in portfolio and that stock share needs to
    // be 0.0 to be removed. Otherwise, returns false when that stock cannot be removed from the portfolio.
    public boolean removeStock(Stock s) {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: recalculates and updates totalValue to sum of all stock market values
    // (shares * current price)
    public void updateTotalValue() {
        // stub
    }

    // getters 
    public double getCashBalance() {
        return 0.0; // stub
    }

    public double getTotalValue() {
        return 0.0; // stub
    }

    public List<Stock> getStocks() {
        return null; // stub
    }

    // EFFECTS: display owner information in the console
    // with the format as below:
    // This portfolio owned by ownerName with total value $ and format it to 2 decimal places
    public String getOwnerInformation() {
        // return "This portfolio owned by " + ownerName + " with total value $" 
        //     + String.format("%.2f", totalValue);
        return null; // stub
    }
}
